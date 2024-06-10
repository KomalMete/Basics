package com.example.dataFormatter.service;

import com.example.dataFormatter.models.request.EmployeeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeService {
    Object saveOrUpdate(EmployeeRequest employeeRequest);

    Object uploadExcelForEmployee(MultipartFile file) throws IOException;
}
