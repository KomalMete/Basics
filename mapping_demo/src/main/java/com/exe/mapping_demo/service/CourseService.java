package com.exe.mapping_demo.service;

import com.exe.mapping_demo.models.request.CourseRequest;

public interface CourseService {
    Object saveCourse(CourseRequest courseRequest);

    Object DeleteCourseById(Long id);
}
