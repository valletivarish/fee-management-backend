package com.organization.repository;

import com.organization.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByStudentFeeId(String studentFeeId);
    List<Payment> findByStudentId(String studentId);
    List<Payment> findByPaidAtBetween(Instant from, Instant to);
    List<Payment> findByMethod(Payment.Method method);
    List<Payment> findByMethodAndPaidAtBetween(Payment.Method method, Instant from, Instant to);
}
