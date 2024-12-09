package com.demo.repository;

import com.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Query 注解查询
 */
public interface QueryAnnotationQueryStudentRepository extends JpaRepository<Student, Long> {

    // 根据学生邮箱查询学生信息
    @Query("SELECT stu FROM Student stu WHERE stu.email = ?1")
    Student findByEmail(String email);

    // 根据班级名称查询班级下的所有学生
    @Query("SELECT stu FROM Student stu JOIN FETCH stu.classroom classroom WHERE classroom.name = :classroomName")
    List<Student> findUsersByClassroomName(@Param("classroomName") String classroomName);

    // 删除多个学生
    @Modifying
    @Query("DELETE FROM Student stu WHERE stu.id in (?1)")
    int removeAllByIdIn(Collection<Long> ids);

    @Query(value = "UPDATE Student set age = :age where id = :id ")
    @Modifying
    void updateAge(@Param("id") Long id, @Param("age") Integer age);

    // 分页排序查询：根据班级名称查询班级下的所有学生
    @Query("SELECT stu FROM Student stu JOIN stu.classroom classroom WHERE classroom.name = :classroomName")
    Page<Student> findUsersByClassroomName(@Param("classroomName") String classroomName, Pageable pageable);

    // 排序查询：根据班级名称查询班级下的所有学生
    List<Student> findUsersByClassroomName(@Param("classroomName") String classroomName, Sort sort);

}
