package com.omsoft.empcompany.models.request;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {

    private Long companyId;

    private String companyName;

    private String description;

    private String website;

    private String contact;
}
