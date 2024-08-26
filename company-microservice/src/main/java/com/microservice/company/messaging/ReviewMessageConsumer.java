package com.microservice.company.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.company.model.ReviewMessageDTO;
import com.microservice.company.service.CompanyService;

@Service
public class ReviewMessageConsumer {
	
	@Autowired
	private CompanyService companyService;
	
	@RabbitListener(queues = "companyRatingQueue")
	public void consumeMessage(ReviewMessageDTO reviewMessageDTO) {
		companyService.updateCompanyRating(reviewMessageDTO);
	}
	
}
