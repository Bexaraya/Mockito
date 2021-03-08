package com.example.demo.exception;

public class UnmatchingDoctorCredentialsException extends RuntimeException {
	
	public UnmatchingDoctorCredentialsException(String msg) {
		super(msg);
	}
	
}
