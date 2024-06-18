package com.omsoftware.demo_2.repository;

import com.omsoftware.demo_2.model.Employee;
import com.omsoftware.demo_2.projection.EmployeeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee , Long> {
    //without sort
    @Query(value = "select * from employees where name like %:name%", nativeQuery = true)
    Page<Employee> findByName(String name, Pageable pageable);

    //with sort
    @Query(value = "select * from employees where address like %:address%",nativeQuery = true)
    Page findByAddress(String address, Pageable pageable);

    Page findByNameAndLastName(String name, String lastName, Pageable pageable);

    //last_name as its sql related query so give column name as present in db
    @Query(value = "select * from employees where CONCAT(name,'',last_name) like %:userName% ",nativeQuery = true)
    Page searchByName1AndLastName(String userName, Pageable pageable);

    //db query
    @Query(value = "select name as name, last_name as lastName, email as email from employees", nativeQuery = true)
    Page<EmployeeProjection> findByProjection(Pageable pageable);

    Employee findByEmail(String email);

    boolean existsByEmail(String email);

}
