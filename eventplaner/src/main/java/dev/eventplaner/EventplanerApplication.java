package dev.eventplaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main entry point for the Eventplaner application.
 * It is annotated with @SpringBootApplication, indicating it is a Spring Boot
 * application.
 */
@SpringBootApplication
public class EventplanerApplication {

	/**
	 * The main method that serves as an entry point for the application.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(EventplanerApplication.class, args);
	}

}
