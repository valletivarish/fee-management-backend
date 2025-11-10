package com.organization.service;

import com.organization.entity.Payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface PaymentService {
    Payment create(String payerUserId, String studentFeeId, Payment.Method method, BigDecimal amount);
    List<Payment> listAll();
    List<Payment> listByStudentFee(String studentFeeId);
    List<Payment> listByStudent(String studentId);

    void deleteById(String id);

    void deleteByIdAndRollbackStudentFee(String id);

    Payment getById(String id);

    List<Payment> list(Payment.Method method, Instant from, Instant to);
}
