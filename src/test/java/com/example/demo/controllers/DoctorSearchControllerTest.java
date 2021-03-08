package com.example.demo.controllers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistence.entity.Doctor;
import com.example.demo.persistence.entity.ExecutionStatus;
import com.example.demo.services.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@Tag("Controller")
public class DoctorSearchControllerTest {

	// Usado para testead controladores
	private MockMvc mockMvc;
	// Usado para mockear las dependencias que está dentro del ApplicationContext
	@Mock
	DoctorService docService;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(
				new DoctorSearchController(this.docService)).build();
	}
	
	@Test
	@DisplayName("Should return error message for when Doctor not existing in the data"
				+ " base tries to login.")
	public void Should_ReturnErrorMessage_ForUnmatchingDoctor() throws Exception {
		Doctor doctor = new Doctor();
		doctor.setEmail("foo@bar.com");
		doctor.setPassword("foobar");
		
		// Create a Json representation for the Doctor object
		Gson gson = new Gson();
		String jsonDoctor = gson.toJson(doctor);
		
		// Pre-program the behaviour of mock. When the findByEmailAndPassword method in 
		// service is invocker, return empty list
		
		when(this.docService.findByEmailAndPassword("foo@bar.com", "foobar"))
		.thenReturn(null);
		
		// Invoking the controller method
		MvcResult result = this.mockMvc.perform(
											post("/doctors/login/process")
											.contentType(MediaType.APPLICATION_JSON)
											.content(jsonDoctor))
										.andExpect(status().isOk())
										.andReturn();
		
		// Verify the program behavior. Assert the response object
		MockHttpServletResponse response = result.getResponse();
		ObjectMapper mapper = new ObjectMapper();
		ExecutionStatus responseObj = mapper.readValue(response.getContentAsString(), ExecutionStatus.class);
		
		assertTrue(responseObj.getCode().equals("DOCTOR_LOGIN_UNSUCCESSFUL"));
		assertTrue(responseObj.getDescription().equals("Doctor's email or password is incorrect. Please try again!"));
										
	}
}
