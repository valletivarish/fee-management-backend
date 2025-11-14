package com.organization.config;

import com.organization.entity.Student;
import com.organization.entity.Student.CourseEnrollment;
import com.organization.entity.User;
import com.organization.repository.StudentRepository;
import com.organization.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Year;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    private record StudentSeed(
            String firstName,
            String lastName,
            String email,
            String password,
            String course,
            String academicYear,
            int startYear,
            int endYear) { }

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository,
                               StudentRepository studentRepository,
                               PasswordEncoder passwordEncoder) {
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

            List<StudentSeed> studentsToSeed = List.of(
                    new StudentSeed(
                            "Aditi",
                            "Sharma",
                            "aditi.sharma@example.com",
                            "Student1@123",
                            "Computer Science Engineering",
                            "2021-2025",
                            2021,
                            2025
                    ),
                    new StudentSeed(
                            "Rahul",
                            "Desai",
                            "rahul.desai@example.com",
                            "Student2@123",
                            "Business Administration",
                            "2023-2027",
                            2023,
                            2027
                    ),
                    new StudentSeed(
                            "Sofia",
                            "Fernandes",
                            "sofia.fernandes@example.com",
                            "Student3@123",
                            "Mechanical Engineering",
                            "2022-2026",
                            2022,
                            2026
                    )
            );

            studentsToSeed.forEach(seed -> {
                if (!studentRepository.existsByEmail(seed.email())) {
                    Student student = new Student();
                    student.setFirstName(seed.firstName());
                    student.setLastName(seed.lastName());
                    student.setEmail(seed.email());
                    student.setDegreeType("Bachelor's Degree");
                    student.setDegreeDurationYears(seed.endYear() - seed.startYear());
                    student.setCourse(seed.course());
                    student.setAcademicYear(seed.academicYear());
                    student.setCourses(List.of(
                            new CourseEnrollment(
                                    seed.course(),
                                    seed.startYear(),
                                    seed.endYear(),
                                    Year.now().getValue() <= seed.endYear())
                    ));

                    studentRepository.save(student);

                    System.out.println("Sample student created: " + seed.email());
                } else {
                    System.out.println("Sample student already exists: " + seed.email());
                }

                if (!userRepository.existsByEmail(seed.email())) {
                    User studentUser = new User();
                    studentUser.setName(seed.firstName() + " " + seed.lastName());
                    studentUser.setUsername(seed.email());
                    studentUser.setEmail(seed.email());
                    studentUser.setPassword(passwordEncoder.encode(seed.password()));
                    studentUser.setRoles(Set.of("ROLE_STUDENT"));

                    userRepository.save(studentUser);

                    System.out.printf("Student user created: %s / %s%n", seed.email(), seed.password());
                } else {
                    System.out.println("Student user already exists: " + seed.email());
                }
            });
        };
    }
}
