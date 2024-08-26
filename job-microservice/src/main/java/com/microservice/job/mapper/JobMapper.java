package com.microservice.job.mapper;

import java.util.List;

import com.microservice.job.model.Company;
import com.microservice.job.model.Job;
import com.microservice.job.model.JobDTO;
import com.microservice.job.model.Review;

public class JobMapper {
	
	public static JobDTO mapToJobWithCompantDTO(Job job, Company company, List<Review> reviews) {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setId(job.getId());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setMaxSalary(job.getMaxSalary());
		jobDTO.setMinSalary(job.getMinSalary());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setLocation(job.getLocation());
		jobDTO.setCompany(company);
		jobDTO.setReviews(reviews);
		
		return jobDTO;
		
	}
	
}
