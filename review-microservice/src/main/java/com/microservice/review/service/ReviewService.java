package com.microservice.review.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.review.model.Review;
import com.microservice.review.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	public List<Review> getAllReviews(Long companyId) {
		return reviewRepository.findByCompanyId(companyId);
	}

	public boolean addReview(Long companyId, Review review) {
		if(companyId != null && review != null) {
			review.setCompanyId(companyId);
			reviewRepository.save(review);
			return true;
		} else {
			return false;
		}
	}

	public Review getReview(Long reviewId) {
		return reviewRepository.findById(reviewId).orElse(null);
	}

	public boolean updateReview(Long reviewId, Review updatedReview) {
		Optional<Review> optionalReview = reviewRepository.findById(reviewId);
		if(optionalReview.isPresent()) {
			Review review = optionalReview.get();
			review.setTitle(updatedReview.getTitle());
			review.setDescription(updatedReview.getDescription());
			review.setRating(updatedReview.getRating());
			review.setCompanyId(updatedReview.getCompanyId());
			reviewRepository.save(review);
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteReview(Long reviewId) {
		try {
			reviewRepository.deleteById(reviewId);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
