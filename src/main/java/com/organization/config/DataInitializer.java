package com.organization.config;

import com.organization.entity.User;
import com.organization.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@example.com";

            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = new User();
                admin.setName("System Admin");
                admin.setUsername("admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("Admin@123")); // encode password!
                admin.setRoles(Set.of("ROLE_ADMIN"));

                userRepository.save(admin);

                System.out.println("Admin user created: " + adminEmail + " / Admin@123");
            } else {
                System.out.println("Admin user already exists: " + adminEmail);
            }
        };
    }
}
