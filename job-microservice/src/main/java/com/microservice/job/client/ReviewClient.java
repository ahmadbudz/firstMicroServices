package com.microservice.job.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservice.job.model.Review;

@FeignClient(name = "REVIEW-MICROSERVICE")
public interface ReviewClient {
	
	@GetMapping("/api/reviews")
	List<Review> getReviews(@RequestParam("companyId") Long companyId);
	
}
