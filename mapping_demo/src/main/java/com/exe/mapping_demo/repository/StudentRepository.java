package com.exe.mapping_demo.repository;

import com.exe.mapping_demo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //@Query(value = "select * from student where courseId like %:name%")
    List<Student> findByCourseListId(Long id);

}
