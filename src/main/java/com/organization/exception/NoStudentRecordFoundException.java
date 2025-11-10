package com.organization.exception;

public class NoStudentRecordFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoStudentRecordFoundException(String message) {
		super(message);
	}
}