package com.ada.moneymorpher;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController()
public class MoneymorpherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneymorpherApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	@GetMapping("/status")
	public String ApiStatus (){
		return "API Running";
	}
}
