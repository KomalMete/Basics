package com.omsoft.empcompany.controller;

import com.omsoft.empcompany.models.request.EmployeeRequest;
import com.omsoft.empcompany.models.response.CustomEntityResponse;
import com.omsoft.empcompany.models.response.EntityResponse;
import com.omsoft.empcompany.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/saveOrUpdateEmployee")
    public ResponseEntity<?> saveOrUpdate(@RequestBody EmployeeRequest employeeRequest)
    {
        try
        {
            return new ResponseEntity<>(new EntityResponse(employeeService.saveOrUpdate(employeeRequest), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/downloadExcelForEmployee")
    public ResponseEntity<InputStreamResource> downloadExcelForEmployee()
    {
        //List<Company> list = companyRepository.findAll();
        try
        {
            ByteArrayInputStream byteArrayInputStream = employeeService.downloadExcelForEmployee();

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

    @PostMapping("/uploadExcelForEmployee")
    public ResponseEntity<?> uploadExcelForEmployee(@RequestParam MultipartFile file)
    {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid Excel file");
        }

        try
        {
            return new ResponseEntity<>(new EntityResponse(employeeService.uploadExcelForEmployee(file), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    //download data from database to excel for which company id provided matches
    @GetMapping("/downloadListOfEmployeesFromSameCompany")
    public ResponseEntity<InputStreamResource> downloadListOfEmployeesFromSameCompany(@RequestParam (name = "companyId") Long companyId)
    {
        try
        {
            ByteArrayInputStream byteArrayInputStream = employeeService.downloadListOfEmployeesFromSameCompany(companyId);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=employee.xlsx");

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

    //download data from database to excel for which company id provided matches where data overwrite is not happening
    @GetMapping("/downloadListForEmployeeAvoidOverwriteData")
    public ResponseEntity<InputStreamResource> downloadListForEmployeeAvoidOverwriteData(@RequestParam (name = "companyId") Long companyId)
    {
        try
        {
            ByteArrayInputStream byteArrayInputStream = employeeService.downloadListForEmployeeAvoidOverwriteData(companyId);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=employee.xlsx");

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

}
