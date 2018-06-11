package com.fireway.cpms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class CpmsApplication {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}


	public static void main(String[] args) {
		SpringApplication.run(CpmsApplication.class, args);
	}
}
