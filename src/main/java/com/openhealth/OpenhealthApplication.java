package com.openhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.openhealth.entity.User;
import com.openhealth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OpenhealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenhealthApplication.class, args);
	}

    @Bean
    CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("adminpass"));
			admin.setRole("ADMIN");
			userRepository.save(admin);

			User receptionist = new User();
			receptionist.setUsername("recep");
			receptionist.setPassword(passwordEncoder.encode("recepciona"));
			receptionist.setRole("RECEPTIONIST");
			userRepository.save(receptionist);
		};
	}
} 