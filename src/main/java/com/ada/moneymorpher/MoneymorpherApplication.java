package com.ada.moneymorpher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController()
public class MoneymorpherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneymorpherApplication.class, args);
	}

	@GetMapping("/status")
	public String ApiStatus (){
		return "API Running";
	}
}
