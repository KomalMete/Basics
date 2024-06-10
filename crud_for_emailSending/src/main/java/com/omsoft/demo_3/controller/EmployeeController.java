package com.omsoft.demo_3.controller;

import com.omsoft.demo_3.model.request.EmployeeRequest;
import com.omsoft.demo_3.model.response.CustomEntityResponse;
import com.omsoft.demo_3.model.response.EntityResponse;
import com.omsoft.demo_3.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdate(@RequestBody EmployeeRequest employeeRequest)
    {
        try
        {
            return new ResponseEntity<>( new EntityResponse(employeeService.saveOrUpdate(employeeRequest) , 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>( new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<?> verifyOTP(@RequestParam(name = "email") String email,
                                       @RequestParam(name = "otp") String OTP )
    {
        try
        {
            return new ResponseEntity<>( new EntityResponse(employeeService.verifyOTP(email,OTP) , 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>( new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
}
