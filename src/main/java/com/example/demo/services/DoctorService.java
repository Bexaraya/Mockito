package com.example.demo.services;

import java.util.List;

import com.example.demo.exception.DoctorNotFoundException;
import com.example.demo.exception.UnmatchingDoctorCredentialsException;
import com.example.demo.persistence.entity.Doctor;

public interface DoctorService {

	List<Doctor> findByLocationAndSpeciality(String location,
			String speciality);
	
	Doctor doesUserExists(String firstName) throws DoctorNotFoundException;
	
	Doctor findByEmailAndPassword(String email, String password) throws UnmatchingDoctorCredentialsException;
}
