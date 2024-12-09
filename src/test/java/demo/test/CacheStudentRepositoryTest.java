package demo.test;

import com.demo.DemoApplication;
import com.demo.entity.Student;
import com.demo.repository.CacheStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;

/**
 * 测试缓存测试类
 *
 * @author francis
 */
@SpringBootTest(classes = DemoApplication.class)
public class CacheStudentRepositoryTest {

    @Autowired
    private CacheStudentRepository repository;


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
    public void test1() throws InterruptedException {
        Student student = repository.findByName("张三");
        System.out.println("111 " + student);

        Thread.sleep(5000L);

        Student student1 = repository.findByName("张三");
        System.out.println("222 " + student1);
    }


}
