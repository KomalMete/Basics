package com.omsoft.callingexternalapi.controller;

import com.omsoft.callingexternalapi.models.response.CustomEntityResponse;
import com.omsoft.callingexternalapi.models.response.EntityResponse;
import com.omsoft.callingexternalapi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/save")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/getAllCountriesData")
    public ResponseEntity<?> getAllCountriesData()
    {
        //Root root = countryService.getAllCountriesData();

        try
        {
            return new ResponseEntity<>(new EntityResponse(countryService.getAllCountriesData(), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
}
