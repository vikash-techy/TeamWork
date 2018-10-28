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
import org.springframework.web.bind.annotation.RestController;

import com.teamwork.stundent_architect_service.exception.ResourceNotFoundException;
import com.teamwork.stundent_architect_service.model.Chapter;
import com.teamwork.stundent_architect_service.model.Instructor;
import com.teamwork.stundent_architect_service.model.Subject;
import com.teamwork.stundent_architect_service.repository.ChapterRepository;
import com.teamwork.stundent_architect_service.repository.InstructorRepository;
import com.teamwork.stundent_architect_service.repository.SubjectRepository;

/**
 * @author suryateja.kasulanati
 *
 */
@RestController
public class ChapterController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ChapterRepository chapterRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private InstructorRepository instructorRepository;

	@GetMapping("/chapters")
	public Collection<Chapter> getAll() {
		final List<Chapter> chapters = chapterRepository.findAll();
		if (chapters.isEmpty())
			throw new ResourceNotFoundException("Chapters", "", null);

		return chapters;
	}

	@GetMapping("/chapters/id/{id}")
	public Chapter getChapter(@PathVariable Long id) {
		Optional<Chapter> chapter = chapterRepository.findById(id);
		if (!chapter.isPresent())
			throw new ResourceNotFoundException("Chapter", "Id", id);

		return chapter.get();
	}

	@GetMapping("/subjects/{subjectId}/chapters")
	public Collection<Chapter> getAllChaptersBySubjetc(@PathVariable Long subjectId) {
		final List<Chapter> chapters = chapterRepository.findBySubject(subjectId);
		if (chapters.isEmpty())
			throw new ResourceNotFoundException("Chapters for Subject", "Id", subjectId);

		return chapters;
	}

	@PostMapping(value = { "/subjects/{subjectId}/chapters", "/subjects/{subjectId}/chapters/{instructorId}" })
	public Chapter addChapter(@PathVariable Long subjectId, @PathVariable Optional<Long> instructorId,
			@Valid @RequestBody Chapter chapter) {
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter for Subject", "Id", subjectId));

		chapter.setSubject(subject);
		if (instructorId.isPresent()) {
			Instructor instructor = instructorRepository.findById(instructorId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));
			chapter.setInstructor(instructor);
		}
		Chapter newChapter = chapterRepository.save(chapter);

		return newChapter;
	}

	@PutMapping(value = { "/chapters/id/{chapterId}", "/chapters/id/{chapterId}/instructors/{instructorId}" })
	public Chapter updateChapter(@PathVariable Long chapterId, @PathVariable Optional<Long> instructorId,
			@Valid @RequestBody Chapter newChapter) {
		Chapter chapter = chapterRepository.findById(chapterId)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter", "Id", chapterId));

		chapter.setName(newChapter.getName());
		chapter.setDescription(newChapter.getDescription());
		chapter.setIsFree(newChapter.isFree());
		chapter.setIsMandatory(newChapter.isMandatory());
		chapter.setTimeRequiredInSecs(newChapter.getTimeRequiredInSecs());
		
		if (instructorId.isPresent()) {
			Instructor instructor = instructorRepository.findById(instructorId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));
			chapter.setInstructor(instructor);
		}

		chapter = chapterRepository.save(chapter);
		return chapter;
	}

	@DeleteMapping("/chapters/id/{chapterId}")
	public ResponseEntity<?> deleteChapter(@PathVariable Long chapterId) {
		return chapterRepository.findById(chapterId).map(chapter -> {
			chapterRepository.delete(chapter);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Chapter", "Id", chapterId));
	}

	@PatchMapping("/chapters/id/{chapterId}/instructors/{instructorId}")
	public Chapter updateInstructor(@PathVariable Long chapterId, @PathVariable Long instructorId) {
		Chapter chapter = chapterRepository.findById(chapterId)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter", "Id", chapterId));

		Instructor instructor = instructorRepository.findById(instructorId)
				.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));

		chapter.setInstructor(instructor);
		chapter = chapterRepository.save(chapter);
		return chapter;
	}
}