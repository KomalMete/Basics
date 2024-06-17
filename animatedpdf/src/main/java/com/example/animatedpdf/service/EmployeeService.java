package com.example.animatedpdf.service;

import com.example.animatedpdf.models.request.EmployeeRequest;

public interface EmployeeService {
    Object saveOrUpdate(EmployeeRequest employeeRequest);
}
