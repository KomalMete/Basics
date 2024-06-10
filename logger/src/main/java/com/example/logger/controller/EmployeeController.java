package com.example.logger.controller;

import com.example.logger.model.request.EmployeeRequest;
import com.example.logger.model.response.CustomEntityResponse;
import com.example.logger.model.response.EntityResponse;
import com.example.logger.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
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
}
