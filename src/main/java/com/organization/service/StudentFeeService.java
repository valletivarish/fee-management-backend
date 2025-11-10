package com.organization.service;

import com.organization.entity.StudentFee;

import java.time.Instant;
import java.util.List;

public interface StudentFeeService {
    StudentFee assign(String studentId, String feePlanId, Instant dueDate);
    List<StudentFee> listAll();
    List<StudentFee> listMine(String studentId);
    StudentFee updateDueDate(String id, Instant dueDate);
    void deleteIfNoPayments(String id);
}
