package com.microservice.job.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.job.model.Company;

@FeignClient(name = "COMPANY-MICROSERVICE")
public interface CompanyClient {
	@GetMapping("/api/companies/id/{id}")
	Company getCompany(@PathVariable("id") Long id);
}
