package com.omsoftware.demo_2.controller;

import com.omsoftware.demo_2.model.request.EmployeeRequest;
import com.omsoftware.demo_2.model.response.CustomEntityResponse;
import com.omsoftware.demo_2.model.response.EntityResponse;
import com.omsoftware.demo_2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdate(@RequestBody EmployeeRequest employeeRequest)
    {
        try
        {
            return new ResponseEntity(new EntityResponse(employeeService.saveOrUpdate(employeeRequest), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity( new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees()
    {
        try
        {
            return new ResponseEntity(new EntityResponse(employeeService.getAllEmployees(), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1),HttpStatus.OK);
        }
    }

    //java8
    @DeleteMapping("/deleteById/{employeeId}")
    public ResponseEntity<?> deleteById(@PathVariable Long employeeId)
    {
        try
        {
            return new ResponseEntity<>(new EntityResponse(employeeService.deleteById(employeeId), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    //java8
    @GetMapping("/findById/{employeeId}")
    public ResponseEntity<?> findById(@PathVariable Long employeeId)
    {
        try
        {
            return new ResponseEntity<>(new EntityResponse(employeeService.findById(employeeId), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    //java8
    @GetMapping("/searchByName")
    public ResponseEntity<?> searchByName(@RequestParam (required = false, defaultValue = "0") Integer pageNo,
                                          @RequestParam (required = false, defaultValue = "5") Integer pageSize,
                                          @RequestParam (required = false, name = "name") String name
                                        )


    {
        try
        {
            Pageable pageable = PageRequest.of(pageNo,pageSize);
            return new ResponseEntity<>(new EntityResponse(employeeService.searchByName(name, pageable), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
    //java8
    @GetMapping("/searchByAddress")
    public ResponseEntity<?> searchByAddress(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                             @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                                             @RequestParam(required = false, name = "address") String address,
                                             @RequestParam(required = false, name = "sortBy") String sortBy
                                             )
    {

        try
        {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by( Sort.Direction.DESC,sortBy));//giving sorting type
            return new ResponseEntity<>(new EntityResponse(employeeService.searchByAddress(address, pageable), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    //by firstname and lastname
    @GetMapping("/searchByFirstNameAndLastName")
    public ResponseEntity<?> searchByNameAndLastName(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                        @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                        @RequestParam(required = false, name = "name") String name,
                                                        @RequestParam(required = false, name = "lastName") String lastName,
                                                        @RequestParam(required = false, name = "sortBy") String sortBy
                                                        )
    {
        try
        {
            Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
            return new ResponseEntity<>(new EntityResponse(employeeService.searchByNameAndLastName(name, lastName ,pageable), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    //using like so that if u give firstname only then also result will show.
    @GetMapping("/searchName1AndLastName")
    public ResponseEntity<?> searchName1AndLastName(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                    @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                    @RequestParam(required = false, name = "userName") String userName,
                                                    @RequestParam(required = false, name = "sortBy") String sortBy
                                                   )
    {
        try
        {
            Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
            return new ResponseEntity<>(new EntityResponse(employeeService.searchByName1AndLastName(userName,pageable), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/projection")
    public ResponseEntity<?> projection(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                        @RequestParam(required = false, defaultValue = "5") Integer pageSize)
    {
        try
        {
            Pageable pageable = PageRequest.of(pageNo,pageSize);
            return new ResponseEntity<>(new EntityResponse(employeeService.projection(pageable), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    //java8
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam(name = "employeeId") Long employeeId,
                                            @RequestParam(name = "oldPassword") String oldPassword,
                                            @RequestParam(name = "newPassword") String newPassword
                                            )
    {
        try
        {
            return  new ResponseEntity<>(new EntityResponse(employeeService.changePassword(employeeId,oldPassword,newPassword), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    //java8
    @PostMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestParam (name = "employeeId") Long employeeId)
    {
        try
        {
            return new ResponseEntity<>( new EntityResponse(employeeService.changeStatus(employeeId), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>( new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<?> fileUpload(@RequestParam(name = "file")MultipartFile file)
    {
        try
        {
            return new ResponseEntity<>( new EntityResponse(employeeService.fileUpload(file), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>( new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestParam(name = "email") String email,
                                            @RequestParam(name = "password") String password)
    {
        try
        {
            return new ResponseEntity<>( new EntityResponse(employeeService.forgetPassword(email,password), 0), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>( new CustomEntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
}
