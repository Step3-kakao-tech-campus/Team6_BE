package com.example.tripKo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TripKoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripKoApplication.class, args);
	}

}
