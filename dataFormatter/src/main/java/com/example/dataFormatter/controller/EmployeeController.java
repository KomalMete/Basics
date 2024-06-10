package com.example.dataFormatter.controller;

import com.example.dataFormatter.models.request.EmployeeRequest;
import com.example.dataFormatter.models.response.CustomEntityResponse;
import com.example.dataFormatter.models.response.EntityResponse;
import com.example.dataFormatter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/saveOrUpdateEmployee")
    public ResponseEntity<?> saveOrUpdate(@RequestBody EmployeeRequest employeeRequest)
    {
        try
        {
            return new ResponseEntity<>(new EntityResponse(employeeService.saveOrUpdate(employeeRequest), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PostMapping("/uploadExcelForEmployee")
    public ResponseEntity<?> uploadExcelForEmployee(@RequestParam MultipartFile file)
    {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid Excel file");
        }

        try
        {
            return new ResponseEntity<>(new EntityResponse(employeeService.uploadExcelForEmployee(file), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
}
