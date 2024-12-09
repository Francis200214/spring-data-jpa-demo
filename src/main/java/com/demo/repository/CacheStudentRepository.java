package com.demo.repository;

import com.demo.entity.Student;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 缓存
 */
public interface CacheStudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT u FROM Student u WHERE u.name = :name")
    @Cacheable(cacheNames = "studentCache", key = "'findByName:' + #name")
    Student findByName(@Param("name") String name);

    @CacheEvict(cacheNames = "studentCache", allEntries = true)
    void removeById(Long id);

    @CacheEvict(cacheNames = "studentCache", allEntries = true)
    @CachePut(cacheNames = "studentCache", key = "'allUsers'")
    Student save(Student student);

}
