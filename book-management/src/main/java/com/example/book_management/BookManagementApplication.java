package com.example.book_management;

import com.example.book_management.Entities.Role;
import com.example.book_management.Entities.App_user;
import com.example.book_management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BookManagementApplication implements CommandLineRunner {


	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookManagementApplication.class, args);
	}


	public void run(String... args){
		App_user adminAccount =userRepository.findByRole(Role.ADMIM);

		if (null==adminAccount){
			App_user appuser = new App_user();

			appuser.setEmail("admine@gmail.com");
			appuser.setFirstname("admine");
			appuser.setLastname("Admine");
			appuser.setRole(Role.ADMIM);
			appuser.setPassword(new BCryptPasswordEncoder().encode("admine"));
			userRepository.save(appuser);
		}

	}
}
