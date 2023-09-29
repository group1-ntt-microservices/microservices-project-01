package com.ntt.microservice.credits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CreditsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditsApplication.class, args);
	}

}
