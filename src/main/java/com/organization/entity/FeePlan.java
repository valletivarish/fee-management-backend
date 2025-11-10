package com.organization.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Document(collection = "fee_plans")
@CompoundIndexes({
        @CompoundIndex(name = "course_year_unique", def = "{'course':1,'academicYear':1}", unique = true)
})
public class FeePlan {

    @Id
    private String id;

    @Indexed
    @NotBlank(message = "Course is required")
    @Size(max = 100, message = "Course must not exceed 100 characters")
    private String course;

    @Indexed
    @NotBlank(message = "Academic year is required")
    @Pattern(regexp = "\\d{4}-\\d{4}", message = "Academic year must be in format YYYY-YYYY")
    private String academicYear;

    @NotNull(message = "Tuition fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tuition fee must be non-negative")
    @Digits(integer = 10, fraction = 2, message = "Tuition fee must have at most 10 integer digits and 2 decimal places")
    private BigDecimal tuition = BigDecimal.ZERO;
    
    @NotNull(message = "Hostel fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Hostel fee must be non-negative")
    @Digits(integer = 10, fraction = 2, message = "Hostel fee must have at most 10 integer digits and 2 decimal places")
    private BigDecimal hostel = BigDecimal.ZERO;
    
    @NotNull(message = "Library fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Library fee must be non-negative")
    @Digits(integer = 10, fraction = 2, message = "Library fee must have at most 10 integer digits and 2 decimal places")
    private BigDecimal library = BigDecimal.ZERO;
    
    @NotNull(message = "Lab fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Lab fee must be non-negative")
    @Digits(integer = 10, fraction = 2, message = "Lab fee must have at most 10 integer digits and 2 decimal places")
    private BigDecimal lab = BigDecimal.ZERO;
    
    @NotNull(message = "Sports fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Sports fee must be non-negative")
    @Digits(integer = 10, fraction = 2, message = "Sports fee must have at most 10 integer digits and 2 decimal places")
    private BigDecimal sports = BigDecimal.ZERO;

    public FeePlan() {}

    public FeePlan(String id, String course, String academicYear, BigDecimal tuition, BigDecimal hostel, BigDecimal library, BigDecimal lab, BigDecimal sports) {
        this.id = id;
        this.course = course;
        this.academicYear = academicYear;
        this.tuition = tuition;
        this.hostel = hostel;
        this.library = library;
        this.lab = lab;
        this.sports = sports;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getTotal() {
        return tuition.add(hostel).add(library).add(lab).add(sports);
    }

    @Override
    public String toString() {
        return "FeePlan{" +
                "id='" + id + '\'' +
                ", course='" + course + '\'' +
                ", academicYear='" + academicYear + '\'' +
                ", tuition=" + tuition +
                ", hostel=" + hostel +
                ", library=" + library +
                ", lab=" + lab +
                ", sports=" + sports +
                '}';
    }
}
