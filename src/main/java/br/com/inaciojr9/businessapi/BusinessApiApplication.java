package br.com.inaciojr9.businessapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BusinessApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessApiApplication.class, args);
	}

}
