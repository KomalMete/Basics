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
import org.springframework.data.domain.Sort;

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
    //found
    @Test
    void TestFoundByName_Found()
    {
        Pageable pageable = PageRequest.of(0,5);
       Page<Employee> emp =  employeeRepository.findByName("Komal", pageable);

       assertThat(emp.getContent().get(0).getName()).isEqualTo(employee.getName());
    }

    //not found
    @Test
    void TestFoundByName_NotFound()
    {
        Pageable pageable = PageRequest.of(0,5);
        Page<Employee> emp =  employeeRepository.findByName("Dipti", pageable);

        assertThat(emp.isEmpty()).toString();
    }

    //2.search by location
    @Test
    void TestFoundByLocation_Found()
    {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("location"));
        Page<Employee> emp =  employeeRepository.findByLocation("Pune", pageable);

        assertThat(emp.getContent().get(0).getLocation()).isEqualTo(employee.getLocation());
    }

    //not found
    @Test
    void TestFoundByLocation_NotFound()
    {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("location"));
        Page<Employee> emp =  employeeRepository.findByLocation("Mumbai", pageable);

        assertThat(emp.getContent()).isEmpty();
    }

    //6.find by email
    //found
    @Test
    void TestFoundByEmail_Found()
    {
        Employee emp = employeeRepository.findByEmail("komalmete8@gmail.com");

        assertThat(emp.getEmail()).isEqualTo(employee.getEmail());
    }

    //not found
    @Test
    void TestFoundByEmail_NotFound()
    {
        Employee emp = employeeRepository.findByEmail("komalmete@gmail.com");

        assertThat(emp).isNull();
    }

    //7.exists by email
    //found
    @Test
    void TestExistsByEmail_Found()
    {
        boolean value = employeeRepository.existsByEmail("komalmete8@gmail.com");

        assertThat(value).isTrue();
    }

    //not found
    @Test
    void TestExistsByEmail_NotFound()
    {
        boolean value = employeeRepository.existsByEmail("komalmete@gmail.com");

        assertThat(value).isFalse();
    }
}
