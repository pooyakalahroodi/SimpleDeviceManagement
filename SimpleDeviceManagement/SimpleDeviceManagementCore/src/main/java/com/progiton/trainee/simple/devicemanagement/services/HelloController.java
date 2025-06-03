package com.progiton.trainee.simple.devicemanagement.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sdm/api/greeting")
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
		return "Hello, World!";
	}
}

