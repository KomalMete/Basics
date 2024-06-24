package com.omsoftware.demo_2.serviceImpl;

import com.omsoftware.demo_2.model.Employee;
import com.omsoftware.demo_2.repository.EmployeeRepository;
import com.omsoftware.demo_2.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    AutoCloseable autoCloseable;

    Employee employee;

    @BeforeEach
    void setUp()
    {
        autoCloseable = MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = new Employee((long) 1, "Komal", "Mete", "Pune", "9881464237", "komalmete8@gmail.com", "komalmete");
    }

    @AfterEach
    void tearDown() throws Exception
    {
        autoCloseable.close();
    }

    @Test
    void TestsaveOrUpdate()
    {
        mock(employee.getClass());
        mock(employeeRepository.getClass());

        when(employeeRepository.save(employee)).thenReturn(employee);
    }

    @Test
    void getAllEmployees() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findById() {
    }

    @Test
    void searchByName() {
    }

    @Test
    void searchByLocation() {
    }

    @Test
    void searchByNameAndLastName() {
    }

    @Test
    void searchByName1AndLastName() {
    }

    @Test
    void projection() {
    }
}