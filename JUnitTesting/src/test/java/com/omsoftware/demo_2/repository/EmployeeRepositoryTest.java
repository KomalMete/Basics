package com.omsoftware.demo_2.repository;

import com.omsoftware.demo_2.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    Employee employee;

    @BeforeEach
    void setUp() {

        employee = new Employee((long) 2, "Dipti", "Mete", "Pune", "7743939942", "diptimete8@gmail.com", "diptimete");
        employeeRepository.save(employee);
    }

    @AfterEach
    void tearDown() {
        employee = null;
        employeeRepository.deleteAll();
    }

    //test cases
    //1. findByName
    @Test
    void TestFindByName_Found()
    {
        Page<Employee> emp =    employeeRepository.findByName("Dipti", Pageable.unpaged());
        List<Employee> empList = emp.getContent();

        assertThat(empList.get(0).getName()).isEqualTo(employee.getName());
    }
}
