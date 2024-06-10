package com.omsoft.empcompany.models.request;

import com.omsoft.empcompany.models.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private Long employeeId;

    private String name;

    private String userName;

    private String lastName;

    private String location;

    private String contact;

    private String email;

    private String password;

    private Company company;

}
