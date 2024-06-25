package com.exe.mapping_demo.serviceImpl;

import com.exe.mapping_demo.models.Course;
import com.exe.mapping_demo.models.Student;
import com.exe.mapping_demo.models.request.CourseRequest;
import com.exe.mapping_demo.models.request.StudentRequest;
import com.exe.mapping_demo.repository.CourseRepository;
import com.exe.mapping_demo.repository.StudentRepository;
import com.exe.mapping_demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Object saveStudent(StudentRequest studentRequest) {

        // Validate course IDs
        if (studentRequest.getCourseList() == null || studentRequest.getCourseList().isEmpty()) {
            throw new IllegalArgumentException("Course list must not be empty.");
        }

        // Find or create courses from the provided course IDs
        List<Course> courses = studentRequest.getCourseList().stream()
                .map(course -> courseRepository.findById(course.getId())
                        .orElseThrow(() -> new RuntimeException("Course not found with id " + course.getId())))
                .collect(Collectors.toList());

        Student student = new Student();
        //student.setId(studentRequest.getId());
        student.setName(studentRequest.getName());
        student.setCourseList(courses);

        studentRepository.save(student);


        for (Course course : courses) {
            course.addStudent(student);
            courseRepository.save(course);  // Ensure the course is updated in the database

//            if(courseRepository.existsById(course.getId()))
//            {
//                course.addStudent(student);
//            }
//            else
//            {
//                course.addStudent(student);
//                courseRepository.save(course);
//            }
        }
        return "Student details saved successfully..";
    }
}
