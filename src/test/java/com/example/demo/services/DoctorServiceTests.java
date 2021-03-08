package com.example.demo.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.exception.DoctorNotFoundException;
import com.example.demo.persistence.entity.Doctor;
import com.example.demo.persistence.repository.DoctorRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@Tag("Service")
public class DoctorServiceTests {
	
	@Mock
	private DoctorRepository doctorRepository;
	private DoctorService doctorService;
	
	@BeforeEach
	public void setUp(TestInfo testInfo) throws Exception {
		// TestInfo tiene informacion del Test actual, como displayName, la clase de Test, el
		// metodo test, etc.
		this.doctorService = new DoctorServiceImpl(this.doctorRepository);
		String displayName = testInfo.getDisplayName();
		assertTrue(displayName.equals("Throws exception if doctor with given firstName does not exist :("));
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		
	}
	
	@Test
	@DisplayName("Throws exception if doctor with given"
			   + " firstName does not exist :(")
	public void Should_throwException_When_DoctorsDoesNotExist() {
		String name = "pepito";
		Mockito.when(this.doctorRepository.findByFirstName(name)).thenReturn(new ArrayList<Doctor>());
		assertThatThrownBy(() -> this.doctorService.doesUserExists(name))
		.isInstanceOf(DoctorNotFoundException.class)
		.hasMessage("Doctor does not exists in the database.");
		assertAll("doctors",
				() -> assertNotEquals(2, 1),
				() -> assertEquals(1, 1));
	}
}
