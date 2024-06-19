package com.omsoftware.demo_2.service;

import com.omsoftware.demo_2.model.request.EmployeeRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

public interface EmployeeService {
    Object saveOrUpdate(EmployeeRequest employeeRequest) throws MessagingException;

    Object getAllEmployees();

    Object deleteById(Long employeeId);

    Object findById(Long employeeId);

    Object searchByName(String name, Pageable pageable);

    Object searchByLocation(String location, Pageable pageable);

    Object searchByNameAndLastName(String name, String lastName , Pageable pageable);

    Object searchByName1AndLastName(String userName, Pageable pageable);

    Object projection(Pageable pageable);

    Object changePassword(Long employeeId, String oldPassword, String newPassword);

    //Object changeStatus(Long employeeId);

    Object fileUpload(MultipartFile file);

    Object forgetPassword(String email, String password);
}
