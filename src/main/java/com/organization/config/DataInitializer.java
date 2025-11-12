package com.organization.config;

import com.organization.entity.User;
import com.organization.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.organization.entity.Student;
import com.organization.entity.Student.CourseEnrollment;
import com.organization.repository.StudentRepository;

import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository,
                               StudentRepository studentRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@example.com";
            String studentEmail = "john.doe@example.com";

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

            if (!studentRepository.existsByEmail(studentEmail)) {
                Student student = new Student();
                student.setFirstName("John");
                student.setLastName("Doe");
                student.setEmail(studentEmail);
                student.setDegreeType("Bachelor of Science");
                student.setDegreeDurationYears(4);
                student.setCourse("Computer Science");
                student.setAcademicYear("2024-2028");
                student.setCourses(List.of(
                        new CourseEnrollment("Computer Science", 2024, 2028, true)
                ));

                studentRepository.save(student);

                System.out.println("Sample student created: " + studentEmail);
            } else {
                System.out.println("Sample student already exists: " + studentEmail);
            }
        };
    }
}
