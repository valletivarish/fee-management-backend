package com.organization.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "students")
public class Student {

    @Id
    private String id;

    @Indexed
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Indexed
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Indexed(unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    /**
     * Legacy primary course field; populated from the primary course enrollment for backward compatibility.
     */
    @Indexed
    private String course;

    /**
     * Legacy academic year derived from the primary course enrollment.
     */
    private String academicYear;

    @NotBlank(message = "Degree type is required")
    private String degreeType;

    @Min(value = 1, message = "Degree duration must be at least 1 year")
    @Max(value = 10, message = "Degree duration must not exceed 10 years")
    private Integer degreeDurationYears;

    @Valid
    private List<CourseEnrollment> courses = new ArrayList<>();

    public Student() {}

    public Student(String id,
                   String firstName,
                   String lastName,
                   String email,
                   String degreeType,
                   Integer degreeDurationYears,
                   List<CourseEnrollment> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.degreeType = degreeType;
        this.degreeDurationYears = degreeDurationYears;
        if (courses != null) {
            this.courses = courses;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public Integer getDegreeDurationYears() {
        return degreeDurationYears;
    }

    public void setDegreeDurationYears(Integer degreeDurationYears) {
        this.degreeDurationYears = degreeDurationYears;
    }

    public List<CourseEnrollment> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseEnrollment> courses) {
        this.courses = courses;
    }

    public List<CourseEnrollment> getEffectiveCourses() {
        if (courses != null && !courses.isEmpty()) {
            return courses;
        }
        List<CourseEnrollment> legacyCourses = new ArrayList<>();
        if (course != null && academicYear != null) {
            String[] parts = academicYear.split("-");
            if (parts.length == 2) {
                try {
                    int start = Integer.parseInt(parts[0]);
                    int end = Integer.parseInt(parts[1]);
                    legacyCourses.add(new CourseEnrollment(course, start, end, true));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return legacyCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", degreeType='" + degreeType + '\'' +
                ", degreeDurationYears=" + degreeDurationYears +
                ", courses=" + courses +
                '}';
    }

    public static class CourseEnrollment {
        @NotBlank(message = "Course name is required")
        @Size(max = 100, message = "Course name must not exceed 100 characters")
        private String courseName;

        @Min(value = 1900, message = "Start year must be after 1900")
        @Max(value = 3000, message = "Start year must be before 3000")
        @NotNull(message = "Start year is required")
        private Integer startYear;

        @Min(value = 1900, message = "End year must be after 1900")
        @Max(value = 3000, message = "End year must be before 3000")
        @NotNull(message = "End year is required")
        private Integer endYear;

        private boolean primary;

        public CourseEnrollment() {}

        public CourseEnrollment(String courseName, Integer startYear, Integer endYear, boolean primary) {
            this.courseName = courseName;
            this.startYear = startYear;
            this.endYear = endYear;
            this.primary = primary;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public Integer getStartYear() {
            return startYear;
        }

        public void setStartYear(Integer startYear) {
            this.startYear = startYear;
        }

        public Integer getEndYear() {
            return endYear;
        }

        public void setEndYear(Integer endYear) {
            this.endYear = endYear;
        }

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }

        @Override
        public String toString() {
            return courseName + " (" + startYear + "-" + endYear + ")";
        }
    }
}
