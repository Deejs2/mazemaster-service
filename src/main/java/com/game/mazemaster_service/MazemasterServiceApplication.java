package com.game.mazemaster_service;

import com.game.mazemaster_service.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class MazemasterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MazemasterServiceApplication.class, args);
	}

}
