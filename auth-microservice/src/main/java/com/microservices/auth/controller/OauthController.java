package com.microservices.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"","/"})
public class OauthController {
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public String validate() {
		return "succesfull";
	}
	
}
