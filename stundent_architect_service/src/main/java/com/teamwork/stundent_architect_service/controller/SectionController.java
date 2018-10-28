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
import com.teamwork.stundent_architect_service.model.Content;
import com.teamwork.stundent_architect_service.model.Instructor;
import com.teamwork.stundent_architect_service.model.Section;
import com.teamwork.stundent_architect_service.repository.ChapterRepository;
import com.teamwork.stundent_architect_service.repository.ContentRepository;
import com.teamwork.stundent_architect_service.repository.InstructorRepository;
import com.teamwork.stundent_architect_service.repository.SectionRepository;

/**
 * @author suryateja.kasulanati
 *
 */
@RestController
public class SectionController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SectionRepository sectionRepository;

	@Autowired
	private ChapterRepository chapterRepository;

	@Autowired
	private InstructorRepository instructorRepository;

	@Autowired
	private ContentRepository contentRepository;

	@GetMapping("/sections")
	public Collection<Section> getAll() {
		final List<Section> sections = sectionRepository.findAll();
		if (sections.isEmpty())
			throw new ResourceNotFoundException("Sections", "", null);

		return sections;
	}

	@GetMapping("/sections/id/{id}")
	public Section getSection(@PathVariable Long id) {
		Optional<Section> section = sectionRepository.findById(id);
		if (!section.isPresent())
			throw new ResourceNotFoundException("Section", "Id", id);

		return section.get();
	}

	@GetMapping("/chapters/{chapterId}/sections")
	public Collection<Section> getAllSectionsByChapter(@PathVariable Long chapterId) {
		final List<Section> sections = sectionRepository.findByChapter(chapterId);
		if (sections.isEmpty())
			throw new ResourceNotFoundException("Sections for Chapters", "Id", chapterId);

		return sections;
	}

	@PostMapping(value = { "/chapters/{chapterId}/sections",
			"/chapters/{chapterId}/sections/instructors/{instructorId}",
			"/chapters/{chapterId}/sections/content/{contentId}" })
	public Section addSection(@PathVariable Long chapterId, @PathVariable Optional<Long> instructorId,
			@PathVariable Optional<Long> contentId, @Valid @RequestBody Section section) {
		Chapter chapter = chapterRepository.findById(chapterId)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter for Subject", "Id", chapterId));

		section.setChapter(chapter);
		
		if (instructorId.isPresent()) {
			Instructor instructor = instructorRepository.findById(instructorId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));
			section.setInstructor(instructor);
		}
		
		if (contentId.isPresent()) {
			Content content = contentRepository.findById(contentId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Content", "Id", contentId));
			section.setContent(content);
		} else {
			String contentLocation = section.getContentLocation();
			if (!contentLocation.isEmpty()) {
				Optional<Content> content = contentRepository.findByContentLocationIgnoreCase(contentLocation);
				if (content.isPresent())
					section.setContent(content.get());
				else {
					Content addContent = new Content();
					addContent.setContentType("DEFAULT");
					addContent.setContentLocation(contentLocation);
					addContent = contentRepository.save(addContent);
					section.setContent(addContent);
				}
			}
		}
		
		Section newSection = sectionRepository.save(section);
		return newSection;
	}

	@PutMapping(value = { "/sections/id/{sectionId}", "/sections/id/{sectionId}/instructors/{instructorId}",
			"/sections/id/{sectionId}/content/{contentId}" })
	public Section updateSection(@PathVariable Long sectionId, @PathVariable Optional<Long> instructorId,
			@PathVariable Optional<Long> contentId, @Valid @RequestBody Section newSection) {
		Section section = sectionRepository.findById(sectionId)
				.orElseThrow(() -> new ResourceNotFoundException("Section", "Id", sectionId));

		section.setName(newSection.getName());
		section.setDescription(newSection.getDescription());
		section.setIsFree(newSection.isFree());
		section.setIsMandatory(newSection.isMandatory());
		section.setTimeRequiredInSecs(newSection.getTimeRequiredInSecs());

		if (instructorId.isPresent()) {
			Instructor instructor = instructorRepository.findById(instructorId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));
			section.setInstructor(instructor);
		}
		
		if (contentId.isPresent()) {
			Content content = contentRepository.findById(contentId.get())
					.orElseThrow(() -> new ResourceNotFoundException("COntent", "Id", contentId));
			section.setContent(content);
		} else {
			String contentLocation = newSection.getContentLocation();
			if (!contentLocation.isEmpty()) {
				Optional<Content> content = contentRepository.findByContentLocationIgnoreCase(contentLocation);
				if (content.isPresent())
					section.setContent(content.get());
				else {
					Content addContent = new Content();
					addContent.setContentType("DEFAULT");
					addContent.setContentLocation(contentLocation);
					addContent = contentRepository.save(addContent);
					section.setContent(addContent);
				}
			}
		}

		section = sectionRepository.save(section);
		return section;
	}

	@DeleteMapping("/sections/id/{sectionId}")
	public ResponseEntity<?> deleteSection(@PathVariable Long sectionId) {
		return sectionRepository.findById(sectionId).map(section -> {
			sectionRepository.delete(section);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Section", "Id", sectionId));
	}

	@PatchMapping("/sections/id/{sectionId}/instructors/{instructorId}")
	public Section updateInstructor(@PathVariable Long sectionId, @PathVariable Long instructorId) {
		Section section = sectionRepository.findById(sectionId)
				.orElseThrow(() -> new ResourceNotFoundException("Section", "Id", sectionId));

		Instructor instructor = instructorRepository.findById(instructorId)
				.orElseThrow(() -> new ResourceNotFoundException("Instructor", "Id", instructorId));

		section.setInstructor(instructor);
		section = sectionRepository.save(section);
		return section;
	}

	@PatchMapping("/sections/id/{sectionId}/content/{contentId}")
	public Section updateContent(@PathVariable Long sectionId, @PathVariable Long contentId) {
		Section section = sectionRepository.findById(sectionId)
				.orElseThrow(() -> new ResourceNotFoundException("Section", "Id", sectionId));

		Content content = contentRepository.findById(contentId)
				.orElseThrow(() -> new ResourceNotFoundException("Content", "Id", contentId));

		section.setContent(content);
		section = sectionRepository.save(section);
		return section;
	}
}
