package com.organization.controller;

import com.organization.entity.Payment;
import com.organization.service.PaymentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.time.Instant;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment create(@RequestParam @NotBlank String studentFeeId,
                          @RequestParam @NotNull Payment.Method method,
                          @RequestParam @NotNull @DecimalMin(value = "0.01") BigDecimal amount) {
        String payerUserId = "anonymous"; // For demo purposes
        
        // Log the received parameters for debugging
        System.out.println("PaymentController.create() - studentFeeId: " + studentFeeId + 
                          ", method: " + method + ", amount: " + amount);
        
        return paymentService.create(payerUserId, studentFeeId, method, amount);
    }

    @GetMapping
    public List<Payment> listAll(@RequestParam(required = false) Payment.Method method,
                                   @RequestParam(required = false) Instant from,
                                   @RequestParam(required = false) Instant to) {
        System.out.println("PaymentController.listAll() - method: " + method + ", from: " + from + ", to: " + to);
        List<Payment> result = paymentService.list(method, from, to);
        System.out.println("PaymentController.listAll() - returning " + result.size() + " payments");
        return result;
    }

    @GetMapping("/student-fee/{studentFeeId}")
    public List<Payment> byStudentFee(@PathVariable String studentFeeId) {
        return paymentService.listByStudentFee(studentFeeId);
    }

    @GetMapping("/student/{studentId}")
    public List<Payment> byStudent(@PathVariable String studentId) {
        return paymentService.listByStudent(studentId);
    }

    @GetMapping("/{id}")
    public Payment get(@PathVariable String id) {
        return paymentService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        paymentService.deleteByIdAndRollbackStudentFee(id);
    }
}
