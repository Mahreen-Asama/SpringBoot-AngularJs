package com.redmath.studentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentApplication {

	//NOTE: This project will not run in development, will run in production by setting env. variables
	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

}
