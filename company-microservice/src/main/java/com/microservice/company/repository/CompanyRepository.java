package com.microservice.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.company.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{

}
