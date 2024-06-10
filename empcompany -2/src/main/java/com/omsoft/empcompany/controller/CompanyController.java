package com.omsoft.empcompany.controller;

import com.omsoft.empcompany.models.Company;
import com.omsoft.empcompany.models.request.CompanyRequest;
import com.omsoft.empcompany.models.response.CustomEntityResponse;
import com.omsoft.empcompany.models.response.EntityResponse;
import com.omsoft.empcompany.repository.CompanyRepository;
import com.omsoft.empcompany.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdate(@RequestBody CompanyRequest companyRequest)
    {
        try
        {
            return new ResponseEntity<>(new EntityResponse(companyService.saveOrUpdate(companyRequest), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestParam (name = "companyId") Long companyId)
    {
        try
        {
            return new ResponseEntity<>(new EntityResponse(companyService.deleteById(companyId), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam (name = "companyId") Long companyId)
    {
        try
        {
            return new ResponseEntity<>(new EntityResponse(companyService.getById(companyId), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll()
    {
        try
        {
            return new ResponseEntity<>(new EntityResponse(companyService.getAll(), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/downloadExcelForCompany")
    public ResponseEntity<InputStreamResource> downloadExcelForCompany()
    {
        //List<Company> list = companyRepository.findAll();
        try
        {
            ByteArrayInputStream byteArrayInputStream = companyService.downloadExcelForCompany();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=company_list.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(byteArrayInputStream));
            //return new ResponseEntity<>(new EntityResponse(companyService.downloadExcelForCompany(), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }

    }

    @PostMapping("/uploadExcelForCompany")
    public ResponseEntity<?> uploadExcelForCompany(@RequestParam MultipartFile file)
    {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid Excel file");
        }

        try
        {

            return new ResponseEntity<>(new EntityResponse(companyService.uploadExcelForCompany(file), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
}
