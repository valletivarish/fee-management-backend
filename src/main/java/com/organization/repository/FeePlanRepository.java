package com.organization.repository;

import com.organization.entity.FeePlan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FeePlanRepository extends MongoRepository<FeePlan, String> {
    Optional<FeePlan> findByCourseAndAcademicYear(String course, String academicYear);
    boolean existsByCourseAndAcademicYear(String course, String academicYear);
    List<FeePlan> findByCourse(String course);
    List<FeePlan> findByAcademicYear(String academicYear);
}
