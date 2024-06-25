package com.exe.mapping_demo.controller;

import com.exe.mapping_demo.models.request.CourseRequest;
import com.exe.mapping_demo.models.response.EntityResponse;
import com.exe.mapping_demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/saveCourse")
    public ResponseEntity<?> saveCourse(@RequestBody CourseRequest courseRequest)
    {
        try
        {
            return new ResponseEntity( new EntityResponse(courseService.saveCourse(courseRequest), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity( new EntityResponse(e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/DeleteCourseById")
    public ResponseEntity<?> DeleteCourseById(@RequestParam (name = "id") Long id)
    {
        try
        {
            return new ResponseEntity( new EntityResponse(courseService.DeleteCourseById(id), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity( new EntityResponse(e.getMessage(), 0), HttpStatus.BAD_REQUEST);
        }
    }
}
