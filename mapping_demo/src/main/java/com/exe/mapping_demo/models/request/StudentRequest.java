package com.exe.mapping_demo.models.request;

import com.exe.mapping_demo.models.Course;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentRequest {



    private String name;

    private List<Course> courseList;
}
