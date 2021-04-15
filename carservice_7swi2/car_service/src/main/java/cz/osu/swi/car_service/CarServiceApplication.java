package cz.osu.swi.car_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
public class CarServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}
}
