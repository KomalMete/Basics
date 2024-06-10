package com.example.logger.serviceImpl;

import com.example.logger.model.Employee;
import com.example.logger.model.request.EmployeeRequest;
import com.example.logger.repository.EmployeeRepository;
import com.example.logger.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Object saveOrUpdate(EmployeeRequest employeeRequest) {

        logger.info("Starting saveOrUpdate method.");
        Optional<Employee> existingEmployee = employeeRepository.findById(employeeRequest.getEmployeeId());

        Employee emp = existingEmployee.orElseGet(() -> new Employee());

        emp.setEmployeeId(employeeRequest.getEmployeeId());
        emp.setName(employeeRequest.getName());
        emp.setLastName(employeeRequest.getLastName());
        emp.setEmail(employeeRequest.getEmail());
        emp.setPassword(employeeRequest.getPassword());
        Employee savedEmployee =  employeeRepository.save(emp);

        logger.info("Employee details saved successfully for ID: {}", savedEmployee.getEmployeeId());

        return "Employee details saved successfully..";
    }
}
