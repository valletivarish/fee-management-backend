package com.organization.service;

import com.organization.entity.FeePlan;
import com.organization.entity.Payment;
import com.organization.entity.Student;
import com.organization.entity.StudentFee;
import com.organization.repository.FeePlanRepository;
import com.organization.repository.PaymentRepository;
import com.organization.repository.StudentFeeRepository;
import com.organization.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentFeeServiceImpl implements StudentFeeService {

    private final StudentFeeRepository studentFeeRepository;
    private final FeePlanRepository feePlanRepository;
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    public StudentFeeServiceImpl(StudentFeeRepository studentFeeRepository,
                                 FeePlanRepository feePlanRepository,
                                 PaymentRepository paymentRepository,
                                 StudentRepository studentRepository) {
        this.studentFeeRepository = studentFeeRepository;
        this.feePlanRepository = feePlanRepository;
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentFee assign(String studentId, String feePlanId, Instant dueDate) {
        FeePlan plan = feePlanRepository.findById(feePlanId).orElseThrow();
        String[] planYears = plan.getAcademicYear().split("-");
        if (planYears.length != 2) {
            throw new IllegalStateException("Fee plan academic year is invalid");
        }

        int planStartYear;
        int planEndYear;
        try {
            planStartYear = Integer.parseInt(planYears[0].trim());
            planEndYear = Integer.parseInt(planYears[1].trim());
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Fee plan academic year is invalid");
        }

        boolean alreadyAssigned = studentFeeRepository
                .existsByStudentIdAndFeePlanIdAndAcademicYear(studentId, plan.getId(), plan.getAcademicYear());

        if (alreadyAssigned) {
            throw new IllegalStateException("Fee plan already assigned to this student for the same academic year");
        }

        Student student = studentRepository.findById(studentId).orElseThrow();
        List<Student.CourseEnrollment> courses = student.getEffectiveCourses();

        boolean courseMatches = courses.stream()
                .map(Student.CourseEnrollment::getCourseName)
                .filter(Objects::nonNull)
                .anyMatch(name -> name.equalsIgnoreCase(plan.getCourse()));

        if (!courseMatches) {
            String studentCourses = courses.stream()
                    .map(Student.CourseEnrollment::getCourseName)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.joining(", "));
            throw new IllegalStateException(
                    "Fee plan course must match one of the student's enrolled courses. Student courses: "
                            + (studentCourses.isEmpty() ? "None" : studentCourses));
        }

        Student.CourseEnrollment matchingCourse = courses.stream()
                .filter(courseEnrollment ->
                        courseEnrollment.getStartYear() != null &&
                                courseEnrollment.getEndYear() != null &&
                                planStartYear >= courseEnrollment.getStartYear() &&
                                planEndYear <= courseEnrollment.getEndYear())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Fee plan academic year must fall within the student's course duration"));

        StudentFee sf = new StudentFee();
        sf.setStudentId(studentId);
        sf.setFeePlanId(plan.getId());
        sf.setCourse(matchingCourse.getCourseName());
        sf.setAcademicYear(plan.getAcademicYear());
        sf.setTuition(plan.getTuition());
        sf.setHostel(plan.getHostel());
        sf.setLibrary(plan.getLibrary());
        sf.setLab(plan.getLab());
        sf.setSports(plan.getSports());
        sf.setAmountAssigned(plan.getTotal());
        sf.setAmountPaid(BigDecimal.ZERO);
        sf.setStatus(StudentFee.Status.PENDING);
        sf.setAssignedAt(Instant.now());
        sf.setDueDate(dueDate);
        return studentFeeRepository.save(sf);
    }

    @Override
    public List<StudentFee> listAll() {
        return studentFeeRepository.findAll();
    }

    @Override
    public List<StudentFee> listMine(String studentId) {
        return studentFeeRepository.findByStudentId(studentId);
    }

    @Override
    public StudentFee updateDueDate(String id, Instant dueDate) {
        StudentFee sf = studentFeeRepository.findById(id).orElseThrow();
        sf.setDueDate(dueDate);
        return studentFeeRepository.save(sf);
    }

    @Override
    public void deleteIfNoPayments(String id) {
        List<Payment> payments = paymentRepository.findByStudentFeeId(id);
        if (!payments.isEmpty()) {
            paymentRepository.deleteAll(payments);
        }
        studentFeeRepository.deleteById(id);
    }
}
