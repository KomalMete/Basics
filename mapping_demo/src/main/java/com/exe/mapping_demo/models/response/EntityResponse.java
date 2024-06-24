package com.exe.mapping_demo.models.response;

import lombok.*;

import javax.persistence.Entity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntityResponse {

    private Object response;

    private int status;
}
