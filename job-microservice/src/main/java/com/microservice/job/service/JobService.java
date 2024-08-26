package com.microservice.job.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.job.client.CompanyClient;
import com.microservice.job.client.ReviewClient;
import com.microservice.job.mapper.JobMapper;
import com.microservice.job.model.Company;
import com.microservice.job.model.Job;
import com.microservice.job.model.JobDTO;
import com.microservice.job.model.Review;
import com.microservice.job.repository.JobRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobService {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private CompanyClient companyClient;
	@Autowired
	private ReviewClient reviewClient;
	
	//@CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	@Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	public List<JobDTO> findAll() {
		List<Job> jobs = jobRepository.findAll();
		return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	public List<String> companyBreakerFallback(Exception e){
		List<String> list = new ArrayList<>();
		list.add("womp womp");
		return list;
	}
	
	private JobDTO convertToDTO(Job job) {
		Company company = companyClient.getCompany(job.getCompanyId());
		
		List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
		
		JobDTO jobWithCompantDTO = JobMapper
										.mapToJobWithCompantDTO(job, company, reviews);
		return jobWithCompantDTO;
	}

	public void createJob(Job job) {
		jobRepository.save(job);
	}
	
	public JobDTO getJobById(Long id) {
		return convertToDTO(jobRepository.findById(id).orElse(null));
	}

	public boolean deleteJobById(Long id) {
		try {
			jobRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updateJob(Long id, Job updatedJob) {
		Optional<Job> jobOptional = jobRepository.findById(id);
		if (jobOptional.isPresent()) {
			Job job = jobOptional.get();
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setLocation(updatedJob.getLocation());
			jobRepository.save(job);
			return true;
		} else {
			return false;
		}
	}

}
