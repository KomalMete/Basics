package com.example.dataFormatter.models.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeRequest {

    private Long employeeId;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private String birthdate;

    private String salary;
}
