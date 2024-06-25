package com.exe.mapping_demo.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {
    //owner entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "course_student",
                joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
            )
    private List<Student> students;

    //to add student
    public void addStudent(Student student)
    {
        students.add(student);
        student.getCourseList().add(this);
    }

    //to remove student
    public void removeStudent(Student student)
    {
        students.remove(student);
        student.getCourseList().remove(this);
    }


}
