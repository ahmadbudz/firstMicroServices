package com.microservice.review.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.review.model.Review;
import com.microservice.review.model.ReviewMessageDTO;

@Service
public class ReviewMessageProducer {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMessage(Review review) {
		ReviewMessageDTO reviewMessageDTO = new ReviewMessageDTO();
		reviewMessageDTO.setId(review.getId());
		reviewMessageDTO.setTitle(review.getTitle());
		reviewMessageDTO.setDescription(review.getDescription());
		reviewMessageDTO.setRating(review.getRating());
		reviewMessageDTO.setComanyId(review.getCompanyId());
		rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessageDTO);
	}
	
}
