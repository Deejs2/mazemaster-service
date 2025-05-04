package com.game.mazemaster_service;

import com.game.mazemaster_service.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
@RestController
public class MazemasterServiceApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MazemasterServiceApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(MazemasterServiceApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		return "Welcome to the MazeMaster Service! CI/CD Implemented!";
	}

}
