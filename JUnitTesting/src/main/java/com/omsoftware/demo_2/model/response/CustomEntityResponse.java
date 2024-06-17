package com.omsoftware.demo_2.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomEntityResponse {

    private String response;

    private int status;


//    public CustomEntityResponse(String response, int status) {
//        this.response = response;
//        this.status = status;
//    }
}
