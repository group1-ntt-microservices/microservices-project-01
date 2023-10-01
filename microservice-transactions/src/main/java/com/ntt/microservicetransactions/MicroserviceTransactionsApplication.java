package com.ntt.microservicetransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableMongoRepositories
@EnableSwagger2
public class MicroserviceTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceTransactionsApplication.class, args);
	}

}
