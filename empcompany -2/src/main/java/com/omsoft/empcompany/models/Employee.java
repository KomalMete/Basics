package com.omsoft.empcompany.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "name")
    private String name;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "location")
    private String location;



    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "mobile_no", unique = true)
    private String contact;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "employee_createdAt", updatable = false)
    private LocalDateTime employeeCreatedAt;

    @Column(name = "is_Active")
    private boolean isActive;

    @Column(name = "is_Deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(nullable = false, name = "company_id")
    private Company company;
}
