package com.organization.service;

import java.util.List;


import com.organization.entity.Student;

public interface StudentService {

	List<Student> findAll();

	Student findById(String id);

	Student save(Student student);

	List<Student> saveAll(List<Student> studentList);

//	Student updateStudent(Student student);

	void deleteById(String id);

}