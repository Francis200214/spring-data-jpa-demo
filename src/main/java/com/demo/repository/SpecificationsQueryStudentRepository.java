package com.demo.repository;

import com.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Specifications 查询
 */
public interface SpecificationsQueryStudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAll(Specification<Student> specification);

    Page<Student> findAll(Specification<Student> specification, Pageable pageable);

}
