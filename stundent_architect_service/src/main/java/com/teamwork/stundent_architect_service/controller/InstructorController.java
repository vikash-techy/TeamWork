/**
 * 
 */
package com.teamwork.stundent_architect_service.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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
@ExposesResourceFor(Instructor.class)
public class InstructorController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InstructorRepository instructorRepository;

	@GetMapping("/instructors")
	public Resources<Instructor> getAll() {
		final List<Instructor> instructors = instructorRepository.findAll();

		for (final Instructor instructor : instructors) {
			Link selfLink = linkTo(methodOn(InstructorController.class).getInstructor(instructor.getInstructorId()))
					.withSelfRel();
			instructor.add(selfLink);
		}

		Link standardLink = linkTo(methodOn(InstructorController.class).getAll()).withSelfRel();
		Resources<Instructor> resources = new Resources<Instructor>(instructors, standardLink);

		return resources;
	}

	@GetMapping("/instructors/id/{id}")
	public Resource<Instructor> getInstructor(@PathVariable Long id) {
		Optional<Instructor> instructor = instructorRepository.findById(id);
		if (!instructor.isPresent())
			throw new ResourceNotFoundException("Instructor", "Id", id);

		Resource<Instructor> resource = new Resource<Instructor>(instructor.get());
		resource.add(linkTo(methodOn(InstructorController.class).getInstructor(id)).withSelfRel());

		return resource;
	}

	@PostMapping("/instructors")
	public Resource<Instructor> addInstructor(@RequestBody Instructor instructor) {
		
		if (null != instructorRepository.findByEmailIgnoreCase(instructor.getEmail())) {
			throw new RuntimeException("Email Address already registered " + instructor.getEmail());
		}

		String registrationDate = instructor.getRegistrationDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(registrationDate, formatter);

		instructor.setRegistrationDate(dateTime.toString());

		Instructor newInstructor = instructorRepository.save(instructor);

		Link selfLink = linkTo(methodOn(InstructorController.class).getInstructor(newInstructor.getInstructorId()))
				.withSelfRel();

		Resource<Instructor> resource = new Resource<Instructor>(newInstructor, selfLink);
		resource.add(linkTo(methodOn(this.getClass()).getAll()).withRel("all-instructors"));

		return resource;
	}
	
	@GetMapping("/instructors/{email}")
	public Resource<Instructor> getInstructorByEmail(@PathVariable String email) {
		Instructor instructor = instructorRepository.findByEmailIgnoreCase(email);

		if (null == instructor) {
			throw new ResourceNotFoundException("Instructor", "Email", email);
		}

		Link selfLink = linkTo(methodOn(InstructorController.class).getInstructor(instructor.getInstructorId()))
				.withSelfRel();

		Resource<Instructor> resource = new Resource<Instructor>(instructor, selfLink);
		resource.add(linkTo(methodOn(this.getClass()).getAll()).withRel("all-instructors"));

		return resource;
	}
}
