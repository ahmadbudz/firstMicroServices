package com.microservice.company.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.company.clients.ReviewClient;
import com.microservice.company.model.Company;
import com.microservice.company.model.ReviewMessageDTO;
import com.microservice.company.repository.CompanyRepository;

import jakarta.ws.rs.NotFoundException;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private ReviewClient reviewClient;
	
	public List<Company> getAllCompanies(){
		return companyRepository.findAll();
	}
	
	public boolean updateCompany(Company updatedCompany, Long id) {
		Optional<Company> optionalCompany = companyRepository.findById(id);
		if(optionalCompany.isPresent()) {
			Company company = optionalCompany.get();
			company.setDescription(updatedCompany.getDescription());
			company.setName(updatedCompany.getName());
			companyRepository.save(company);
			return true;
		} else {
			return false;
		}
	}
	
	public void createCompany(Company company) {
		companyRepository.save(company);
	}
	
	public boolean deleteCompanyById(Long id) {
		try {
			companyRepository.deleteById(id);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public Company getCompanyById(Long id) {
		return companyRepository.findById(id).orElse(null);
	}
	
	public void updateCompanyRating(ReviewMessageDTO reviewMessageDTO) {
		Company company = companyRepository.findById(reviewMessageDTO.getCompanyId())
				.orElseThrow(() -> new NotFoundException("Company not found: " + reviewMessageDTO));
		Double averageRating = reviewClient.getAverageRating(reviewMessageDTO.getCompanyId());
		company.setRating(averageRating);
		companyRepository.save(company);
	}
	
}
