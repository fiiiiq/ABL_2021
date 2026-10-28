package com.taufiq.produser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProduserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProduserApplication.class, args);
	}

}
