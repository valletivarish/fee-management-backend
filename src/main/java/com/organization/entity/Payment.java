package com.organization.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "payments")
public class Payment {

    @Id
    private String id;

    @Indexed
    @NotBlank(message = "Student fee ID is required")
    private String studentFeeId;

    @Indexed
    @NotBlank(message = "Student ID is required")
    private String studentId;

    @Indexed
    @NotBlank(message = "Payer user ID is required")
    private String payerUserId;

    @Indexed
    @NotNull(message = "Payment method is required")
    private Method method;

    @Indexed
    @NotNull(message = "Payment amount is required")
    @DecimalMin(value = "0.01", message = "Payment amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Payment amount must have at most 10 integer digits and 2 decimal places")
    private BigDecimal amount;

    @Indexed
    @NotNull(message = "Payment date is required")
    private Instant paidAt = Instant.now();

    @Size(max = 50, message = "Reference number must not exceed 50 characters")
    private String referenceNo;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    public enum Method { CASH, CARD, UPI, NET_BANKING, OTHER }

    public Payment() {}

    public Payment(String id, String studentFeeId, String studentId, String payerUserId, Method method, BigDecimal amount, Instant paidAt, String referenceNo, String notes) {
        this.id = id;
        this.studentFeeId = studentFeeId;
        this.studentId = studentId;
        this.payerUserId = payerUserId;
        this.method = method;
        this.amount = amount;
        this.paidAt = paidAt;
        this.referenceNo = referenceNo;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentFeeId() {
        return studentFeeId;
    }

    public void setStudentFeeId(String studentFeeId) {
        this.studentFeeId = studentFeeId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPayerUserId() {
        return payerUserId;
    }

    public void setPayerUserId(String payerUserId) {
        this.payerUserId = payerUserId;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", studentFeeId='" + studentFeeId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", payerUserId='" + payerUserId + '\'' +
                ", method=" + method +
                ", amount=" + amount +
                ", paidAt=" + paidAt +
                ", referenceNo='" + referenceNo + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
