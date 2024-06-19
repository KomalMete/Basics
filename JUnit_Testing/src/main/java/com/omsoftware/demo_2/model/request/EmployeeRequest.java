package com.omsoftware.demo_2.model.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private Long employeeId;

    private String name;

    private String lastName;

    private String location;

    private String contact;

    private String email;

    private String password;

}
