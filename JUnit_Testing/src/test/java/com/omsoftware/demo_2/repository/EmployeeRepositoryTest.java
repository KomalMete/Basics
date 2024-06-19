package com.omsoftware.demo_2.repository;

import com.omsoftware.demo_2.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    Employee employee;

    @BeforeEach
    void setUp()
    {
        employee = new Employee((long) 1, "Komal", "Mete", "Pune", "9881464237", "komalmete8@gmail.com", "komalmete");
        employeeRepository.save(employee);
    }

    @AfterEach
    void tearDown()
    {
        employee = null;
        employeeRepository.deleteAll();
    }

    //test case Success
    // 1.  search by name

    @Test
    void TestFoundByName_Found()
    {
        Pageable pageable = PageRequest.of(0,5);
       Page<Employee> emp =  employeeRepository.findByName("Komal", pageable);

       assertThat(emp.getContent().get(0).getName()).isEqualTo(employee.getName());
    }

    @Test
    void TestFoundByName_NotFound()
    {
        Pageable pageable = PageRequest.of(0,5);
        Page<Employee> emp =  employeeRepository.findByName("Dipti", pageable);

        assertThat(emp.isEmpty()).toString();
    }
}
