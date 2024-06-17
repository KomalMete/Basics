package com.omsoftware.demo_2.repository;

import com.omsoftware.demo_2.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    Employee employee;

    @BeforeEach
    void setUp() {

        employee = new Employee(1, "Komal", "Mete", "Pune", "7743939941", "komalmete8@gmail.com", "komal123");
    }

    @AfterEach
    void tearDown() {

    }
}
