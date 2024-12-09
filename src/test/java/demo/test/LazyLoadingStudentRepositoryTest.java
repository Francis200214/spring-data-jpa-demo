package demo.test;

import com.demo.DemoApplication;
import com.demo.entity.Classroom;
import com.demo.entity.Student;
import com.demo.repository.MethodNameQueryStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * 懒加载测试类
 *
 * @author francis
 */
@SpringBootTest(classes = DemoApplication.class)
public class LazyLoadingStudentRepositoryTest {

    @Autowired
    private MethodNameQueryStudentRepository repository;

    @PostConstruct
    public void init() {
        Student student = new Student();
        student.setId(1L);
        student.setName("张三");
        student.setEmail("zhangsan@xxx.com");
        student.setAge(18);
        repository.save(student);
    }

    @Test
    public void queryAll() {
        Optional<Student> studentOptional = repository.findById(1L);
        Student student = studentOptional.get();
        Long id = student.getId();
        String name = student.getName();
        Classroom classroom = student.getClassroom();
    }

    @Test
    @Transactional(readOnly = true)
    public void queryByTransaction() {
        Optional<Student> studentOptional = repository.findById(1L);
        Student student = studentOptional.get();
        Long id = student.getId();
        String name = student.getName();
        Classroom classroom = student.getClassroom();
    }


}
