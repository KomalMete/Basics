package com.exe.mapping_demo.controller;

import com.exe.mapping_demo.models.request.CourseRequest;
import com.exe.mapping_demo.models.request.StudentRequest;
import com.exe.mapping_demo.models.response.EntityResponse;
import com.exe.mapping_demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveStudent")
    public ResponseEntity<?> saveStudent(@RequestBody StudentRequest studentRequest)
    {
        try
        {
            return new ResponseEntity( new EntityResponse(studentService.saveStudent(studentRequest), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity( new EntityResponse(e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteStudent")
    public ResponseEntity<?> deleteStudent(@RequestParam (name = "id") Long id)
    {
        try
        {
            return new ResponseEntity( new EntityResponse(studentService.deleteStudent(id), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity( new EntityResponse(e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }
}
