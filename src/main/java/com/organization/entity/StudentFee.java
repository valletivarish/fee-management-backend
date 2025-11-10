package com.organization.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "student_fees")
@CompoundIndex(name = "student_fee_unique", def = "{'studentId':1,'feePlanId':1}", unique = true)
public class StudentFee {

    @Id
    private String id;

    @Indexed
    private String studentId;

    @Indexed
    private String feePlanId;

    @Indexed
    private String course;

    @Indexed
    private String academicYear;

    private BigDecimal tuition = BigDecimal.ZERO;
    private BigDecimal hostel = BigDecimal.ZERO;
    private BigDecimal library = BigDecimal.ZERO;
    private BigDecimal lab = BigDecimal.ZERO;
    private BigDecimal sports = BigDecimal.ZERO;

    @Indexed
    private BigDecimal amountAssigned = BigDecimal.ZERO;

    private BigDecimal amountPaid = BigDecimal.ZERO;

    @Indexed
    private Status status = Status.PENDING;

    @Indexed
    private Instant assignedAt = Instant.now();

    private Instant dueDate;

    public enum Status { PENDING, PARTIAL, PAID }

    public StudentFee() {}

    public StudentFee(String id, String studentId, String feePlanId, String course, String academicYear, BigDecimal tuition, BigDecimal hostel, BigDecimal library, BigDecimal lab, BigDecimal sports, BigDecimal amountAssigned, BigDecimal amountPaid, Status status, Instant assignedAt, Instant dueDate) {
        this.id = id;
        this.studentId = studentId;
        this.feePlanId = feePlanId;
        this.course = course;
        this.academicYear = academicYear;
        this.tuition = tuition;
        this.hostel = hostel;
        this.library = library;
        this.lab = lab;
        this.sports = sports;
        this.amountAssigned = amountAssigned;
        this.amountPaid = amountPaid;
        this.status = status;
        this.assignedAt = assignedAt;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFeePlanId() {
        return feePlanId;
    }

    public void setFeePlanId(String feePlanId) {
        this.feePlanId = feePlanId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public BigDecimal getTuition() {
        return tuition;
    }

    public void setTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    public BigDecimal getHostel() {
        return hostel;
    }

    public void setHostel(BigDecimal hostel) {
        this.hostel = hostel;
    }

    public BigDecimal getLibrary() {
        return library;
    }

    public void setLibrary(BigDecimal library) {
        this.library = library;
    }

    public BigDecimal getLab() {
        return lab;
    }

    public void setLab(BigDecimal lab) {
        this.lab = lab;
    }

    public BigDecimal getSports() {
        return sports;
    }

    public void setSports(BigDecimal sports) {
        this.sports = sports;
    }

    public BigDecimal getAmountAssigned() {
        return amountAssigned;
    }

    public void setAmountAssigned(BigDecimal amountAssigned) {
        this.amountAssigned = amountAssigned;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getBalance() {
        return amountAssigned.subtract(amountPaid);
    }

    @Override
    public String toString() {
        return "StudentFee{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", feePlanId='" + feePlanId + '\'' +
                ", course='" + course + '\'' +
                ", academicYear='" + academicYear + '\'' +
                ", tuition=" + tuition +
                ", hostel=" + hostel +
                ", library=" + library +
                ", lab=" + lab +
                ", sports=" + sports +
                ", amountAssigned=" + amountAssigned +
                ", amountPaid=" + amountPaid +
                ", status=" + status +
                ", assignedAt=" + assignedAt +
                ", dueDate=" + dueDate +
                '}';
    }
}
