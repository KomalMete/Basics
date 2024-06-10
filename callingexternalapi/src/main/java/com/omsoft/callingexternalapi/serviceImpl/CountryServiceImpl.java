package com.omsoft.callingexternalapi.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omsoft.callingexternalapi.models.Root;
import com.omsoft.callingexternalapi.repository.DatumRepository;
import com.omsoft.callingexternalapi.repository.RootRepository;
import com.omsoft.callingexternalapi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private RootRepository rootRepository;

    @Autowired
    private DatumRepository datumRepository;

    @Override
    public Object getAllCountriesData() throws JsonProcessingException {

        String url = "https://countriesnow.space/api/v0.1/countries/population/cities";
        RestTemplate restTemplate = new RestTemplate();

      String response = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        Root root = objectMapper.readValue(response, Root.class);
        root.getDatumList().forEach(datumRepository::save);

        return rootRepository.save(root);
    }
}
