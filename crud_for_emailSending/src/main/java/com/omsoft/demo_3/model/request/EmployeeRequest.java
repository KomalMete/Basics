package com.omsoft.demo_3.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private Long employeeId;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private String otpGenerated;
}
