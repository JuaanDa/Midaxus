package com.example.Midaxus;

import com.example.Midaxus.model.entities.Admin;
import com.example.Midaxus.model.entities.Student;
import com.example.Midaxus.model.entities.Teacher;
import com.example.Midaxus.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Set;

@SpringBootApplication
public class MidaxusApplication {
	public static void main(String[] args) {
		SpringApplication.run(MidaxusApplication.class, args);
	}
}
