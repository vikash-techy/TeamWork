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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamwork.stundent_architect_service.exception.ResourceNotFoundException;
import com.teamwork.stundent_architect_service.model.Board;
import com.teamwork.stundent_architect_service.model.Standard;
import com.teamwork.stundent_architect_service.repository.BoardRespository;
import com.teamwork.stundent_architect_service.repository.StandardRespository;

/**
 * @author suryateja.kasulanati
 *
 */
@RestController
public class StandardController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StandardRespository standardRespository;

	@Autowired
	private BoardRespository boardRepository;

	@GetMapping("/standards")
	public Collection<Standard> getAll() {
		final List<Standard> standards = standardRespository.findAll();
		if (standards.isEmpty())
			throw new ResourceNotFoundException("Standard", "", null);

		return standards;
	}

	@RequestMapping("/standards/id/{id}")
	public Standard getStandard(@PathVariable Long id) {
		Optional<Standard> standard = standardRespository.findById(id);
		if (!standard.isPresent())
			throw new ResourceNotFoundException("Standard", "Id", id);

		return standard.get();
	}

	@GetMapping("/boards/{boardId}/standards")
	public Collection<Standard> getAllStandardsByBoard(@PathVariable Long boardId) {
		final List<Standard> standards = standardRespository.findByBoard(boardId);
		if (standards.isEmpty())
			throw new ResourceNotFoundException("Standards for Board", "Id", boardId);
		
		return standards;
	}

	@PostMapping("/boards/{boardId}/standards")
	public Standard addStandard(@PathVariable Long boardId, @Valid @RequestBody Standard standard) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new ResourceNotFoundException("Board", "Id", boardId));

		standard.setBoard(board);
		return standardRespository.save(standard);
	}

	@PutMapping("standards/{standardId}")
	public Standard updateStandard(@PathVariable Long standardId, @Valid @RequestBody Standard newStandard) {
		Standard standard = standardRespository.findById(standardId)
				.orElseThrow(() -> new ResourceNotFoundException("Standard", "Id", standardId));
		standard.setName(newStandard.getName());
		standard.setDescription(newStandard.getDescription());
		return standardRespository.save(standard);
	}

	@DeleteMapping("standards/{standardId}")
	public ResponseEntity<?> deleteStandard(@PathVariable Long standardId) {
		return standardRespository.findById(standardId).map(standard -> {
			standardRespository.delete(standard);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Standard", "Id", standardId));
	}
}
