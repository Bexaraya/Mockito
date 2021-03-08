package com.example.demo.exception;

public class DoctorNotFoundException extends RuntimeException{

	public DoctorNotFoundException(String msg) {
		super(msg);
	}

}
