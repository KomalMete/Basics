package com.exe.mapping_demo.service;

import com.exe.mapping_demo.models.request.CourseRequest;
import com.exe.mapping_demo.models.request.StudentRequest;

public interface StudentService {
    Object saveStudent(StudentRequest studentRequest);
}
