package com.organization.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.organization.entity.Student;
import com.organization.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<List<Student>> findAllStudents() {
		List<Student> studentList = studentService.findAll();
		return new ResponseEntity<>(studentList, HttpStatus.OK);
	}

	@GetMapping("/{sid}")
	public ResponseEntity<Student> findStudent(@PathVariable(name = "sid") String id) {
		Student student = studentService.findById(id);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	@PostMapping
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student stud) {
		Student student = studentService.save(stud);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}

	@PostMapping("/save-all")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Student>> saveAllStudent(@Valid @RequestBody List<Student> studentList) {
		List<Student> student = studentService.saveAll(studentList);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}

	@PutMapping
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student stud) {
		Student student = studentService.save(stud);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	@DeleteMapping("/{sid}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable(name = "sid") String id) {
		studentService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}