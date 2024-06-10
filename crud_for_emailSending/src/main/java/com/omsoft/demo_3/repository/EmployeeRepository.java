package com.omsoft.demo_3.repository;

import com.omsoft.demo_3.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmail(String mail);

    boolean existsByEmail(String mail);
}
