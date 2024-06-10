package com.omsoft.empcompany.service;


import com.omsoft.empcompany.models.request.CompanyRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface CompanyService {

    Object saveOrUpdate(CompanyRequest companyRequest);

    Object deleteById(Long companyId);

    Object getById(Long companyId);

    Object getAll();

    ByteArrayInputStream downloadExcelForCompany();

    Object uploadExcelForCompany(MultipartFile file) throws IOException;
}
