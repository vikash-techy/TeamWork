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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamwork.stundent_architect_service.exception.ResourceNotFoundException;
import com.teamwork.stundent_architect_service.model.Board;
import com.teamwork.stundent_architect_service.model.Standard;
import com.teamwork.stundent_architect_service.repository.BoardRespository;
import com.teamwork.stundent_architect_service.repository.StandardRepository;

/**
 * @author suryateja.kasulanati
 *
 */
@RestController
public class StandardController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StandardRepository standardRepository;

	@Autowired
	private BoardRespository boardRepository;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/standards")
	public Collection<Standard> getAll() {
		final List<Standard> standards = standardRepository.findAll();
		if (standards.isEmpty())
			throw new ResourceNotFoundException("Standard", "", null);

		return standards;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/standards/id/{id}")
	public Standard getStandard(@PathVariable Long id) {
		Optional<Standard> standard = standardRepository.findById(id);
		if (!standard.isPresent())
			throw new ResourceNotFoundException("Standard", "Id", id);

		return standard.get();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/boards/{boardId}/standards")
	public Collection<Standard> getAllStandardsByBoard(@PathVariable Long boardId) {
		final List<Standard> standards = standardRepository.findByBoard(boardId);
		if (standards.isEmpty())
			throw new ResourceNotFoundException("Standards for Board", "Id", boardId);
		
		return standards;
	}

	@PostMapping("/boards/{boardId}/standards")
	public Standard addStandard(@PathVariable Long boardId, @Valid @RequestBody Standard standard) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new ResourceNotFoundException("Board", "Id", boardId));

		standard.setBoard(board);
		return standardRepository.save(standard);
	}

	@PutMapping("standards/{standardId}")
	public Standard updateStandard(@PathVariable Long standardId, @Valid @RequestBody Standard newStandard) {
		Standard standard = standardRepository.findById(standardId)
				.orElseThrow(() -> new ResourceNotFoundException("Standard", "Id", standardId));
		standard.setName(newStandard.getName());
		standard.setDescription(newStandard.getDescription());
		return standardRepository.save(standard);
	}

	@DeleteMapping("standards/{standardId}")
	public ResponseEntity<?> deleteStandard(@PathVariable Long standardId) {
		return standardRepository.findById(standardId).map(standard -> {
			standardRepository.delete(standard);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Standard", "Id", standardId));
	}
}
