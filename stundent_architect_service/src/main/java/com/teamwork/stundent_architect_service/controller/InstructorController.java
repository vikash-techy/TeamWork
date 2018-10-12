/**
 * 
 */
package com.teamwork.stundent_architect_service.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamwork.stundent_architect_service.exception.ResourceNotFoundException;
import com.teamwork.stundent_architect_service.model.Instructor;
import com.teamwork.stundent_architect_service.repository.InstructorRepository;

/**
 * @author suryateja.kasulanati
 *
 */
@RestController
public class InstructorController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InstructorRepository instructorRepository;

	@GetMapping("/instructors")
	public Collection<Instructor> getAll() {
		final List<Instructor> instructors = instructorRepository.findAll();
		if (instructors.isEmpty())
			throw new ResourceNotFoundException("Boards", "", null);

		return instructors;
	}

	@GetMapping("/instructors/id/{id}")
	public Instructor getInstructor(@PathVariable Long id) {
		Optional<Instructor> instructor = instructorRepository.findById(id);
		if (!instructor.isPresent())
			throw new ResourceNotFoundException("Instructor", "Id", id);

		return instructor.get();
	}

	@PostMapping("/instructors")
	public Instructor addInstructor(@RequestBody Instructor instructor) {
		if (null != instructorRepository.findByEmailIgnoreCase(instructor.getEmail())) {
			throw new RuntimeException("Email Address already registered " + instructor.getEmail());
		}

		String registrationDate = instructor.getRegistrationDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(registrationDate, formatter);

		instructor.setRegistrationDate(dateTime.toString());

		Instructor newInstructor = instructorRepository.save(instructor);
		return newInstructor;
	}
	
	@GetMapping("/instructors/{email}")
	public Instructor getInstructorByEmail(@PathVariable String email) {
		Instructor instructor = instructorRepository.findByEmailIgnoreCase(email);
		if (null == instructor) {
			throw new ResourceNotFoundException("Instructor", "Email", email);
		}

		return instructor;
	}
}
