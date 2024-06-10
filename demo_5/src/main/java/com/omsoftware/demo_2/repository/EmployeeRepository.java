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
    @Query(value = "select * from employee where name like %:name%", nativeQuery = true)
    Page findByName(String name, Pageable pageable);

    //with sort
    //here if anything else with mumbai is there in db then also those results will be displayed on screen
    //eg. if i type mumbai in address then navi-mumbai, west-mumbai, east-mumbai will be on screen too
    //if want only particular results then go for address like :address
    //here if i type mumbai then only mumbai location results will be on screen
    @Query(value = "select * from employee where address like %:address%",nativeQuery = true)
    Page findByAddress(String address, Pageable pageable);

    Page findByNameAndLastName(String name, String lastName, Pageable pageable);

    //last_name as its sql related query so give column name as present in db
    @Query(value = "select * from employee where CONCAT(name,'',last_name) like %:userName% ",nativeQuery = true)
    Page searchByName1AndLastName(String userName, Pageable pageable);

    //db query
    @Query(value = "select name as name, last_name as lastName, email as email from employee", nativeQuery = true)
    Page<EmployeeProjection> findByProjection(Pageable pageable);

    Employee findByEmail(String email);

    boolean existsByEmail(String email);

}
