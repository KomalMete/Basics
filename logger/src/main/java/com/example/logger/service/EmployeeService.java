package com.example.logger.service;

import com.example.logger.model.request.EmployeeRequest;

public interface EmployeeService {
    Object saveOrUpdate(EmployeeRequest employeeRequest);
}
