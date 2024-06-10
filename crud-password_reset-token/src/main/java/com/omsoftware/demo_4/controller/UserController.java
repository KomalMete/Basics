package com.omsoftware.demo_4.controller;

import com.omsoftware.demo_4.models.request.UserRequest;
import com.omsoftware.demo_4.models.response.CustomEntityResponse;
import com.omsoftware.demo_4.models.response.EntityResponse;
import com.omsoftware.demo_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest)
    {
        try
        {
            return new ResponseEntity(new EntityResponse(userService.addUser(userRequest), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity( new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
}
