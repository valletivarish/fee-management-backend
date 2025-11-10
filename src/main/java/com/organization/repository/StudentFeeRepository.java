package com.organization.repository;

import com.organization.entity.StudentFee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentFeeRepository extends MongoRepository<StudentFee, String> {
    List<StudentFee> findByStudentId(String studentId);
    boolean existsByStudentIdAndFeePlanIdAndAcademicYear(String studentId, String feePlanId, String academicYear);
}
