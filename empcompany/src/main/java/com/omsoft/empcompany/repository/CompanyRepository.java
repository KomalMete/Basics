package com.omsoft.empcompany.repository;

import com.omsoft.empcompany.models.Company;
import com.omsoft.empcompany.models.request.CompanyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByCompanyName(String name);


}
