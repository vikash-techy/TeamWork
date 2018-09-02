/**
 * 
 */
package com.teamwork.stundent_architect_service.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamwork.stundent_architect_service.exception.ResourceNotFoundException;
import com.teamwork.stundent_architect_service.model.Instructor;
import com.teamwork.stundent_architect_service.model.Standard;
import com.teamwork.stundent_architect_service.model.Subject;
import com.teamwork.stundent_architect_service.repository.InstructorRepository;
import com.teamwork.stundent_architect_service.repository.StandardRespository;
import com.teamwork.stundent_architect_service.repository.SubjectRepository;

/**
 * @author suryateja.kasulanati
 *
 */
@RestController
@ExposesResourceFor(Standard.class)
public class SubjectController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SubjectRepository subjectRespository;

	@Autowired
	private StandardRespository standardRespository;

	@Autowired
	private InstructorRepository instructorRepository;

	@GetMapping("/subjects")
	public Resources<Subject> getAll() {
		final List<Subject> subjects = subjectRespository.findAll();

		for (final Subject subject : subjects) {
			Link selfLink = linkTo(methodOn(SubjectController.class).getSubject(subject.getSubjectId())).withSelfRel();
			subject.add(selfLink);
		}

		Link standardLink = linkTo(methodOn(SubjectController.class).getAll()).withSelfRel();
		Resources<Subject> resources = new Resources<Subject>(subjects, standardLink);

		return resources;
	}

	@RequestMapping("/subjects/id/{id}")
	public Resource<Subject> getSubject(@PathVariable Long id) {
		Optional<Subject> subject = subjectRespository.findById(id);
		if (!subject.isPresent())
			throw new ResourceNotFoundException("Subject", "Id", id);

		Resource<Subject> resource = new Resource<Subject>(subject.get());
		resource.add(linkTo(methodOn(SubjectController.class).getSubject(id)).withSelfRel());
		resource.add(linkTo(methodOn(StandardController.class).getStandard(subject.get().getStandard().getStandardId()))
				.withRel("standard"));

		return resource;
	}

	@GetMapping("/standards/{standardId}/subjects")
	public Resources<Subject> getAllSubjectsByStandard(@PathVariable Long standardId) {

		final List<Subject> subjects = subjectRespository.findByStandard(standardId);

		for (final Subject subject : subjects) {
			Link selfLink = linkTo(methodOn(StandardController.class).getStandard(subject.getSubjectId()))
					.withSelfRel();
			subject.add(selfLink);
		}

		Link boardLink = linkTo(methodOn(StandardController.class).getStandard(standardId)).withRel("standard");
		Resources<Subject> resources = new Resources<Subject>(subjects, boardLink);

		return resources;
	}

	@PostMapping(value = { "/standards/{standardId}/subjects", "/standards/{standardId}/subjects/{instructorId}" })
	public Resource<Subject> addSubject(@PathVariable Long standardId, @PathVariable Optional<Long> instructorId,
			@Valid @RequestBody Subject subject) {

		standardRespository.findById(standardId).map(standard -> {
			subject.setStandard(standard);
			if (instructorId.isPresent()) {
				Instructor instructor = instructorRepository.findById(instructorId.get())
						.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));
				subject.setInstructor(instructor);
			}
			return subjectRespository.save(subject);
		});

		Link selfLink = linkTo(methodOn(SubjectController.class).getSubject(subject.getSubjectId())).withSelfRel();

		Resource<Subject> resource = new Resource<Subject>(subject, selfLink);
		resource.add(linkTo(methodOn(StandardController.class).getStandard(subject.getStandard().getStandardId()))
				.withRel("standard"));

		return resource;
	}

	@PutMapping(value = { "/subjects/id/{subjectId}", "/subjects/id/{subjectId}/{instructorId}" })
	public Resource<Subject> updateSubject(@PathVariable Long subjectId, @PathVariable Optional<Long> instructorId,
			@Valid @RequestBody Subject newSubject) {

		Subject subject = subjectRespository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject", "Id", subjectId));

		subject.setName(newSubject.getName());
		subject.setDescription(newSubject.getDescription());
		if (instructorId.isPresent()) {
			Instructor instructor = instructorRepository.findById(instructorId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));
			subject.setInstructor(instructor);
		}

		subject = subjectRespository.save(subject);

		Link selfLink = linkTo(methodOn(SubjectController.class).getSubject(subjectId)).withSelfRel();

		Resource<Subject> resource = new Resource<Subject>(subject, selfLink);
		resource.add(linkTo(methodOn(StandardController.class).getStandard(subject.getStandard().getStandardId()))
				.withRel("standard"));

		return resource;
	}

	@DeleteMapping("/subjects/id/{subjectId}")
	public ResponseEntity<?> deleteSubject(@PathVariable Long subjectId) {
		return subjectRespository.findById(subjectId).map(subject -> {
			subjectRespository.delete(subject);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Subject", "Id", subjectId));
	}

	@PatchMapping("/subjects/id/{subjectId}/{instructorId}")
	public Resource<Subject> updateInstructor(@PathVariable Long subjectId, @PathVariable Long instructorId) {
		Subject subject = subjectRespository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject", "Id", subjectId));

		Instructor instructor = instructorRepository.findById(instructorId)
				.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));
		subject.setInstructor(instructor);
		
		subject = subjectRespository.save(subject);

		Link selfLink = linkTo(methodOn(SubjectController.class).getSubject(subjectId)).withSelfRel();
		Resource<Subject> resource = new Resource<Subject>(subject, selfLink);
		resource.add(linkTo(methodOn(StandardController.class).getStandard(subject.getStandard().getStandardId()))
				.withRel("standard"));

		return resource;
	}
}
