package com.nisum.challenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "NISUM challenge user api rest", description = "Api for register users", contact = @Contact (name = "Parmenia Maldonado", email = "parmmg@gmail.com")))
@SpringBootApplication()
public class NisumChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NisumChallengeApplication.class, args);
	}

}
