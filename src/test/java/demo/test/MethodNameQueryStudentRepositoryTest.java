package demo.test;

import com.demo.DemoApplication;
import com.demo.entity.Classroom;
import com.demo.entity.Student;
import com.demo.entity.StudentCard;
import com.demo.repository.ClassroomRepository;
import com.demo.repository.MethodNameQueryStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * 方法名 Repository 测试类
 *
 * @author francis
 */
@SpringBootTest(classes = DemoApplication.class)
public class MethodNameQueryStudentRepositoryTest {

    @Autowired
    private MethodNameQueryStudentRepository methodNameQueryStudentRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Test
    public void testSaveAndFindStudent() {
        classroomRepository.deleteAll();
        // 创建班级
        Classroom classroom = new Classroom();
        classroom.setName("一班");
        classroom.setGrade("一年级");
        classroomRepository.save(classroom);

        // 创建学生证
        StudentCard studentCard = new StudentCard();
        studentCard.setCardNumber("SC-1001");
        studentCard.setIssueDate(new Date());

        // 创建学生
        Student student = new Student();
        student.setName("张三");
        student.setEmail("zhangsan@example.com");
        student.setAge(7);
        student.setClassroom(classroom);
        student.setStudentCard(studentCard);

        // 保存学生
        methodNameQueryStudentRepository.save(student);

        // 从数据库中查询学生
        Optional<Student> optionalStudent = methodNameQueryStudentRepository.findById(student.getId());
        assertTrue(optionalStudent.isPresent());
        Student savedStudent = optionalStudent.get();

        // 验证数据
        assertEquals("张三", savedStudent.getName());
        assertEquals("一班", savedStudent.getClassroom().getName());
        assertEquals("SC-1001", savedStudent.getStudentCard().getCardNumber());
    }


    @Test
    public void testPageStudent() {
        // 第 1 页（索引从 0 开始），每页 10 条数据，按学生姓名升序排序，年龄降序排序
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("name").ascending().and(Sort.by("age").descending()));
        // 年龄大于 18，姓名包含 "张"
        Page<Student> page = methodNameQueryStudentRepository.findAllByAgeGreaterThanAndNameLike(18, "张", pageable);

        // 总页数
        int totalPages = page.getTotalPages();
        // 本页多少条
        long totalElements = page.getTotalElements();
        // 本页数据
        List<Student> content = page.getContent();
    }

}
