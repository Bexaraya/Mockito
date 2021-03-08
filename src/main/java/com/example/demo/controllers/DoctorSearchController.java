package com.example.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UnmatchingDoctorCredentialsException;
import com.example.demo.persistence.entity.Doctor;
import com.example.demo.persistence.entity.ExecutionStatus;
import com.example.demo.services.DoctorService;

@RestController
@RequestMapping("/doctors/*")
public class DoctorSearchController {

	final static Logger logger = LoggerFactory.getLogger(DoctorSearchController.class);
	
	DoctorService docService;
	
	@Autowired
	public DoctorSearchController(DoctorService docService) {
		this.docService = docService;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET,
			produces = "application/json")
	public List<Doctor> searchDoctor(
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "speciality", required = false) String speciality) {
		return docService.findByLocationAndSpeciality(location, speciality);
	}
	
	@PostMapping(value = "/login/process", produces = "application/json")
	public @ResponseBody ExecutionStatus processLogin(ModelMap model,
			@RequestBody Doctor reqDoctor) {
		Doctor doctor = null;
		try {
			doctor = docService.findByEmailAndPassword(reqDoctor.getEmail(), reqDoctor.getPassword());
		}
		catch(UnmatchingDoctorCredentialsException e)
		{
			logger.debug(e.getMessage(), e);
		}
		
		if (doctor == null)
			return new ExecutionStatus("DOCTOR_LOGIN_UNSUCCESSFUL", "Doctor's email or password is incorrect. Please try again!");
		return new ExecutionStatus("DOCTOR_LOGIN_SUCCESSFUL", "Login successful!");
		
	}
	
}
