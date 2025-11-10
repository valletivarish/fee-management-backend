package com.organization.service;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.organization.entity.Student;
import com.organization.exception.StudentApiException;
import com.organization.exception.StudentNotFoundException;
import com.organization.repository.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository repository;

	@Override
	public List<Student> findAll() {
		return repository.findAll();
	}

	@Override
	public Student findById(String id) {
		Optional<Student> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new StudentApiException(HttpStatus.NOT_FOUND, "Student with id was not found"+id);
		
	}

	@Transactional
	@Override
	public Student save(Student student) {
		normalizeStudent(student);
		return repository.save(student);
	}

	@Transactional
	@Override
	public void deleteById(String id) {
		repository.deleteById(id);

	}

	@Override
	public List<Student> saveAll(List<Student> studentList) {
		studentList.forEach(this::normalizeStudent);
		return repository.saveAll(studentList);
	}

	private void normalizeStudent(Student student) {
		if (student.getDegreeType() == null || student.getDegreeType().trim().isEmpty()) {
			throw new StudentApiException(HttpStatus.BAD_REQUEST, "Degree type is required");
		}
		student.setDegreeType(student.getDegreeType().trim());

		if (student.getDegreeDurationYears() != null && student.getDegreeDurationYears() <= 0) {
			throw new StudentApiException(HttpStatus.BAD_REQUEST, "Degree duration must be greater than zero");
		}

		if (student.getCourses() == null || student.getCourses().isEmpty()) {
			throw new StudentApiException(HttpStatus.BAD_REQUEST, "At least one course enrollment is required");
		}

		boolean dualDegree = "DUAL".equalsIgnoreCase(student.getDegreeType());

		if (!dualDegree && student.getCourses().size() > 1) {
			throw new StudentApiException(HttpStatus.BAD_REQUEST, "Additional courses are allowed only for Dual Degree students");
		}

		Student.CourseEnrollment primary = null;
		int minStart = Integer.MAX_VALUE;
		int maxEnd = Integer.MIN_VALUE;

		for (Student.CourseEnrollment enrollment : student.getCourses()) {
			if (enrollment.getCourseName() == null || enrollment.getCourseName().trim().isEmpty()) {
				throw new StudentApiException(HttpStatus.BAD_REQUEST, "Course name is required");
			}
			enrollment.setCourseName(enrollment.getCourseName().trim());

			if (enrollment.getStartYear() == null || enrollment.getEndYear() == null) {
				throw new StudentApiException(HttpStatus.BAD_REQUEST, "Course start and end year are required");
			}
			if (enrollment.getEndYear() <= enrollment.getStartYear()) {
				throw new StudentApiException(HttpStatus.BAD_REQUEST, "Course end year must be after start year");
			}
			if (primary == null || enrollment.isPrimary()) {
				primary = enrollment;
			}
			minStart = Math.min(minStart, enrollment.getStartYear());
			maxEnd = Math.max(maxEnd, enrollment.getEndYear());
		}

		if (primary == null) {
			primary = student.getCourses().get(0);
			primary.setPrimary(true);
		} else {
			for (Student.CourseEnrollment enrollment : student.getCourses()) {
				enrollment.setPrimary(enrollment == primary);
			}
		}

		if (student.getDegreeDurationYears() != null) {
			int spanYears = maxEnd - minStart;
			if (spanYears > student.getDegreeDurationYears()) {
				throw new StudentApiException(HttpStatus.BAD_REQUEST, "Course span exceeds degree duration");
			}
		}

		student.setCourse(primary.getCourseName());
		student.setAcademicYear(primary.getStartYear() + "-" + primary.getEndYear());
	}
}
