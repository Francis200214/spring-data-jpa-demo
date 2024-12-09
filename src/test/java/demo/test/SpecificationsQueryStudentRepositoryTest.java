package demo.test;

import com.demo.DemoApplication;
import com.demo.entity.Student;
import com.demo.repository.SpecificationsQueryStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Specifications Repository 测试类
 *
 * @author francis
 */
@SpringBootTest(classes = DemoApplication.class)
public class SpecificationsQueryStudentRepositoryTest {

    @Autowired
    private SpecificationsQueryStudentRepository repository;


    @Test
    public void testFindAll() {
        Specification<Student> specification = this.buildConditions();
        List<Student> list = repository.findAll(specification);

        Specification<Student> spec = Specification
                .where(hasName("张三"))
                .and(hasClassroomName("二"));
        List<Student> all = repository.findAll(spec);
    }

    @Test
    public void testFindAllPage() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name").ascending());
        Specification<Student> specification = this.buildConditions();
        Page<Student> page = repository.findAll(specification, pageRequest);
    }


    private Specification<Student> buildConditions() {
        Specification<Student> specifications = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 条件集合
                List<Predicate> predicates = new ArrayList<>();
                // 查询所有姓名等于张三的通宵
                predicates.add(criteriaBuilder.equal(root.get("name"), "张三"));

                // 班级左关联学生，查询班级名称包含“二”【注意：classroom 是与 Student 实体中的属性名称一致】
                Join<Object, Object> join = root.join("classroom", JoinType.LEFT);
                predicates.add(criteriaBuilder.like(join.get("name"), "%二%"));

                return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getGroupRestriction();
            }
        };
        return specifications;
    }


    public static Specification<Student> hasName(String name) {
        return (Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (name == null || name.trim().isEmpty()) {
                // 没有条件
                return cb.conjunction();
            }
            return cb.equal(root.get("name"), name);
        };
    }

    public static Specification<Student> hasClassroomName(String classroomName) {
        return (Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (classroomName == null || classroomName.trim().isEmpty()) {
                return cb.conjunction();
            }
            Join<Object, Object> join = root.join("classroom", JoinType.LEFT);
            return cb.like(join.get("name"), "%" + classroomName + "%");
        };
    }


}
