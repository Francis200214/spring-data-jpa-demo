package com.demo.repository;

import com.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * 方法名查询
 */
public interface MethodNameQueryStudentRepository extends JpaRepository<Student, Long> {

    // 根据学生邮箱查询学生信息
    Student findByEmail(String email);

    // 根据学生姓名模糊查询学生信息
    List<Student> findAllByNameLike(String name);

    // 根据学生年龄大于某个值查询学生信息
    List<Student> findAllByAgeGreaterThan(Integer ageIsGreaterThan);

    // 根据学生年龄大于某个值且姓名模糊查询学生信息
    List<Student> findAllByAgeGreaterThanAndNameLike(Integer ageIsGreaterThan, String name);

    // 根据Id批量删除多个学生
    void removeByIdIsIn(Collection<Long> ids);


    // 分页排序查询：根据学生年龄大于某个值且姓名模糊查询学生信息
    Page<Student> findAllByAgeGreaterThanAndNameLike(Integer ageIsGreaterThan, String name, Pageable pageable);

    // 排序查询：根据学生年龄大于某个值且姓名模糊查询学生信息
    List<Student> findAllByAgeGreaterThanAndNameLike(Integer ageIsGreaterThan, String name, Sort sort);


}
