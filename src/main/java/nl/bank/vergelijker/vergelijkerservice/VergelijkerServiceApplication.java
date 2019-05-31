package nl.bank.vergelijker.vergelijkerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VergelijkerServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(VergelijkerServiceApplication.class, args);
	}

}
