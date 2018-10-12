/**
 * 
 */
package com.teamwork.stundent_architect_service.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SubjectController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SubjectRepository subjectRespository;

	@Autowired
	private StandardRespository standardRespository;

	@Autowired
	private InstructorRepository instructorRepository;

	@GetMapping("/subjects")
	public Collection<Subject> getAll() {
		final List<Subject> subjects = subjectRespository.findAll();
		if(subjects.isEmpty())
			throw new ResourceNotFoundException("Subjects", "", null);
		
		return subjects;
	}

	@RequestMapping("/subjects/id/{id}")
	public Subject getSubject(@PathVariable Long id) {
		Optional<Subject> subject = subjectRespository.findById(id);
		if (!subject.isPresent())
			throw new ResourceNotFoundException("Subject", "Id", id);

		return subject.get();
	}

	@GetMapping("/standards/{standardId}/subjects")
	public Collection<Subject> getAllSubjectsByStandard(@PathVariable Long standardId) {
		final List<Subject> subjects = subjectRespository.findByStandard(standardId);
		if (subjects.isEmpty())
			throw new ResourceNotFoundException("Subjects for Standard", "Id", standardId);
		
		return subjects;
	}

	@PostMapping(value = { "/standards/{standardId}/subjects", "/standards/{standardId}/subjects/{instructorId}" })
	public Subject addSubject(@PathVariable Long standardId, @PathVariable Optional<Long> instructorId,
			@Valid @RequestBody Subject subject) {
		Standard standard = standardRespository.findById(standardId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject for Standard", "Id", standardId));
		
		subject.setStandard(standard);
		if (instructorId.isPresent()) {
			Instructor instructor = instructorRepository.findById(instructorId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));
			subject.setInstructor(instructor);
		}
		Subject newSubject = subjectRespository.save(subject);

		return newSubject;
	}

	@PutMapping(value = { "/subjects/id/{subjectId}", "/subjects/id/{subjectId}/instructors/{instructorId}" })
	public Subject updateSubject(@PathVariable Long subjectId, @PathVariable Optional<Long> instructorId,
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
		return subject;
	}

	@DeleteMapping("/subjects/id/{subjectId}")
	public ResponseEntity<?> deleteSubject(@PathVariable Long subjectId) {
		return subjectRespository.findById(subjectId).map(subject -> {
			subjectRespository.delete(subject);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Subject", "Id", subjectId));
	}

	@PatchMapping("/subjects/id/{subjectId}/instructors/{instructorId}")
	public Subject updateInstructor(@PathVariable Long subjectId, @PathVariable Long instructorId) {
		Subject subject = subjectRespository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject", "Id", subjectId));

		Instructor instructor = instructorRepository.findById(instructorId)
				.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));
		
		subject.setInstructor(instructor);
		subject = subjectRespository.save(subject);
		return subject;
	}
}
