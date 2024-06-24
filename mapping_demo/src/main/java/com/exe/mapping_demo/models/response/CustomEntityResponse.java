package com.exe.mapping_demo.models.response;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomEntityResponse {

    private String response;

    private int status;
}
