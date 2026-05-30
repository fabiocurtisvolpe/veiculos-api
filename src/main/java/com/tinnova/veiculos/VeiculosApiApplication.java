package com.tinnova.veiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class VeiculosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeiculosApiApplication.class, args);
	}

}
