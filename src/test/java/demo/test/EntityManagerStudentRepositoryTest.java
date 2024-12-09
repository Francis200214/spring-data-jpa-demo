package demo.test;

import com.demo.DemoApplication;
import com.demo.entity.Student;
import com.demo.repository.MethodNameQueryStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 测试 EntityManager 测试类
 *
 * @author francis
 */
@SpringBootTest(classes = DemoApplication.class)
public class EntityManagerStudentRepositoryTest {

    @Autowired
    private MethodNameQueryStudentRepository methodNameQueryStudentRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSaveAndFindStudent() {
        List<Student> all = methodNameQueryStudentRepository.findAll();
        int batchSize = 50;
        for (int i = 0; i < all.size(); i++) {
            // 插入
            entityManager.persist(all.get(i));
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }


}
