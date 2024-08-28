package com.microservice.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.review.messaging.ReviewMessageProducer;
import com.microservice.review.model.Review;
import com.microservice.review.model.ReviewMessageDTO;
import com.microservice.review.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ReviewMessageProducer reviewMessageProducer;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Review> getAllReviews(@RequestParam Long companyId){
		return reviewService.getAllReviews(companyId);
	}
	
	@PostMapping
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	public ReviewMessageDTO addReview(@RequestParam Long companyId, 
							@RequestBody Review review) {
		reviewService.addReview(companyId, review);
		return reviewMessageProducer.sendMessage(review);
	}
	
	@GetMapping("id/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Review getReview(@PathVariable Long id) {
		return reviewService.getReview(id);
	}
	
	@PutMapping("id/{id}")
	public ResponseEntity<String> updateReview(@PathVariable Long id, 
											   @RequestBody Review review) {
		boolean updated = reviewService.updateReview(id, review);
		if(updated) {
			return new ResponseEntity<>("Review updated successfullt", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("id/{id}")
	public ResponseEntity<String> deleteReview(@PathVariable Long id) {
		boolean deleted = reviewService.deleteReview(id);
		if(deleted) {
			return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/averageRating")
	public Double getAverageReviewRating(@RequestParam Long companyId) {
		List<Review> reviews = reviewService.getAllReviews(companyId);
		return reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
	}
	
}
