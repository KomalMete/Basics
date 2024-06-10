package com.omsoft.demo_3.service;

import com.omsoft.demo_3.model.request.EmployeeRequest;

public interface EmployeeService {
    Object saveOrUpdate(EmployeeRequest employeeRequest);

    Object verifyOTP(String email, String otp);
}
