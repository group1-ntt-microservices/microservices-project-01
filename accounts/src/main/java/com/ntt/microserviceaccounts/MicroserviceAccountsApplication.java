package com.ntt.microserviceaccounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceAccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceAccountsApplication.class, args);
	}

}
