package edu.dwes.pi_manuelRetamosa_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PiManuelRetamosaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiManuelRetamosaBackendApplication.class, args);
	}

}
