package com.example.interviewPrep;
import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class InterviewPrepApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		System.setProperty("google.redirect.uri", Objects.requireNonNull(dotenv.get("GOOGLE_REDIRECT_URI")));


		SpringApplication.run(InterviewPrepApplication.class, args);
	}

}
