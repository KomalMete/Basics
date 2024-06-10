package com.omsoft.empcompany.service;

import com.omsoft.empcompany.models.request.EmployeeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface EmployeeService {

    Object saveOrUpdate(EmployeeRequest employeeRequest);

    ByteArrayInputStream downloadExcelForEmployee();

    Object uploadExcelForEmployee(MultipartFile file) throws IOException;
}
