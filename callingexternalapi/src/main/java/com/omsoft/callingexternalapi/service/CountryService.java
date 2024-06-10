package com.omsoft.callingexternalapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CountryService {

    Object getAllCountriesData() throws JsonProcessingException;
}
