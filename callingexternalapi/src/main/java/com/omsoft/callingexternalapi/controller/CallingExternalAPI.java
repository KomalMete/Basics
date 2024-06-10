package com.omsoft.callingexternalapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omsoft.callingexternalapi.models.response.CustomEntityResponse;
import com.omsoft.callingexternalapi.models.response.EntityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CallingExternalAPI {

    @GetMapping("/hello")
    public String printHello()
    {
        return "Hello World";
    }

    @GetMapping("/calling")
    public String getAllCompanies()
    {
        String url = "http://localhost:8080/api/hello";

        RestTemplate restTemplate = new RestTemplate();

        String str =  restTemplate.getForObject(url, String.class);

       return str;
    }

    @GetMapping("/getlist")
    public ResponseEntity<?> getCompanies()
    {
        String url = "http://localhost:8081/api/getAll";
        RestTemplate restTemplate = new RestTemplate();
       //Object[] list = restTemplate.getForObject(url,Object[] .class);

        try
        {
            EntityResponse response = restTemplate.getForObject(url, EntityResponse.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    //here im just seeing the output on postman and not saving that in my database
    @GetMapping("/countries")
    public ResponseEntity<?> getCountries()
    {
        String url = "https://countriesnow.space/api/v0.1/countries/population/cities";
        RestTemplate restTemplate = new RestTemplate();

        try {

//            EntityResponse response = restTemplate.getForObject(url, EntityResponse.class);
//            return new ResponseEntity<>(response, HttpStatus.OK);


            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(response, Map.class);

            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }

    }

    //i want to save data in database
//    @GetMapping("/getContriesData")
//    public ResponseEntity<?> getContriesData()
//    {
//
//    }
}
