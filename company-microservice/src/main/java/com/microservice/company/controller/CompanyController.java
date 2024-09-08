package com.microservice.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.company.model.Company;
import com.microservice.company.service.CompanyService;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping({"", "/"})
	@ResponseStatus(HttpStatus.OK)
	public List<Company> getAllCompanies(){
		return companyService.getAllCompanies();
	}
	
	@PutMapping({"/id/{id}", "/id/{id}/"})
	public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany) {
		boolean updated = companyService.updateCompany(updatedCompany, id);
		if(updated) {
			return new ResponseEntity<>("Company Updated successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping({"","/"})
	@ResponseStatus(HttpStatus.CREATED)
	public String addCompany(@RequestBody Company company) {
		companyService.createCompany(company);
		return "Company Added successfully";
	}
	
	@DeleteMapping({"/id/{id}", "/id/{id}/"})
	public ResponseEntity<String> deleteCompany(@PathVariable Long id){
		boolean deleted = companyService.deleteCompanyById(id);
		if(deleted) {
			return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping({"/id/{id}", "/id/{id}/"})
	public ResponseEntity<Company> getCompany(@PathVariable Long id) {
		Company job = companyService.getCompanyById(id);
		if(job != null) {
			return new ResponseEntity<>(job, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
