package com.exe.mapping_demo.models.request;

import com.exe.mapping_demo.models.Student;
import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseRequest {

    private Long id;

    private String name;

    private List<Student> students;
}
