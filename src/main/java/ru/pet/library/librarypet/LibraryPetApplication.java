package ru.pet.library.librarypet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LibraryPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryPetApplication.class, args);
	}

}
