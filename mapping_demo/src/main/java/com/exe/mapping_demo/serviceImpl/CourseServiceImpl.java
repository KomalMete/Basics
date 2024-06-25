package com.exe.mapping_demo.serviceImpl;

import com.exe.mapping_demo.models.Course;
import com.exe.mapping_demo.models.Student;
import com.exe.mapping_demo.models.request.CourseRequest;
import com.exe.mapping_demo.repository.CourseRepository;
import com.exe.mapping_demo.repository.StudentRepository;
import com.exe.mapping_demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Object saveCourse(CourseRequest courseRequest) {
        Course course = new Course();
        course.setId(courseRequest.getId());
        course.setName(courseRequest.getName());

        courseRepository.save(course);
        return "Course details saved successfully..";
    }

    @Override
    public Object DeleteCourseById(Long id)
    {
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course doesnt exists"));

        List<Student> students = studentRepository.findByCourseListId(id);
        for(Student student : students)
        {
            course.removeStudent(student);

        }
        courseRepository.deleteById(id);
        return "course deleted successfully..";
    }
}
