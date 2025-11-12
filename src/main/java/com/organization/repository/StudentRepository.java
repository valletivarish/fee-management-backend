package com.organization.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.organization.entity.Student;
@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    boolean existsByEmail(String email);
}