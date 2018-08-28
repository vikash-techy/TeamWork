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
@ExposesResourceFor(Standard.class)
public class StandardController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StandardRespository standardRespository;

	@Autowired
	private BoardRespository boardRepository;

	@GetMapping("/standards")
	public Resources<Standard> getAll() {
		final List<Standard> standards = standardRespository.findAll();

		for (final Standard standard : standards) {
			Link selfLink = linkTo(methodOn(StandardController.class).getStandard(standard.getStandardId()))
					.withSelfRel();
			standard.add(selfLink);
		}

		Link boardLink = linkTo(methodOn(StandardController.class).getAll()).withSelfRel();
		Resources<Standard> resources = new Resources<Standard>(standards, boardLink);

		return resources;
	}

	@RequestMapping("/standards/id/{id}")
	public Resource<Standard> getStandard(@PathVariable Long id) {
		Optional<Standard> standard = standardRespository.findById(id);
		if (!standard.isPresent())
			throw new ResourceNotFoundException("Standard", "Id", id);

		Resource<Standard> resource = new Resource<Standard>(standard.get());
		resource.add(
				linkTo(methodOn(StandardController.class).getStandard(standard.get().getStandardId())).withSelfRel());
		resource.add(linkTo(methodOn(BoardController.class).getBoard(standard.get().getBoard().getBoardId()))
				.withRel("board"));

		return resource;
	}

	@GetMapping("/boards/{boardId}/standards")
	public Resources<Standard> getAllStandardsByBoard(@PathVariable Long boardId) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new ResourceNotFoundException("Board", "Id", boardId));

		final List<Standard> standards = standardRespository.findByBoard(board);

		for (final Standard standard : standards) {
			Link selfLink = linkTo(methodOn(StandardController.class).getStandard(standard.getStandardId()))
					.withSelfRel();
			standard.add(selfLink);
		}

		Link boardLink = linkTo(methodOn(BoardController.class).getBoard(boardId)).withRel("board");
		Resources<Standard> resources = new Resources<Standard>(standards, boardLink);

		return resources;
	}

	@PostMapping("/boards/{boardId}/standards")
	public Resource<Standard> addStandard(@PathVariable Long boardId, @Valid @RequestBody Standard standard) {
		boardRepository.findById(boardId).map(board -> {
			standard.setBoard(board);
			return standardRespository.save(standard);
		});

		Link selfLink = linkTo(methodOn(StandardController.class).getStandard(standard.getStandardId())).withSelfRel();

		Resource<Standard> resource = new Resource<Standard>(standard, selfLink);
		resource.add(linkTo(methodOn(BoardController.class).getBoard(boardId)).withRel("board"));

		return resource;
	}

	@PutMapping("/boards/{boardId}/standards/{standardId}")
	public Resource<Standard> updateComment(@PathVariable Long boardId, @PathVariable Long standardId,
			@Valid @RequestBody Standard newStandard) {
		if (!boardRepository.existsById(boardId)) {
			throw new ResourceNotFoundException("Board ", "Id", boardId);
		}

		Standard standard = standardRespository.findById(standardId)
				.orElseThrow(() -> new ResourceNotFoundException("Standard", "Id", standardId));
		standard.setName(newStandard.getName());
		standard.setDescription(newStandard.getDescription());
		standard = standardRespository.save(standard);

		Link selfLink = linkTo(methodOn(StandardController.class).getStandard(standardId)).withSelfRel();

		Resource<Standard> resource = new Resource<Standard>(standard, selfLink);
		resource.add(linkTo(methodOn(BoardController.class).getBoard(boardId)).withRel("board"));

		return resource;
	}

	@DeleteMapping("/boards/{boardId}/standards/{standardId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long boardId, @PathVariable Long standardId) {
		if (!boardRepository.existsById(boardId)) {
			throw new ResourceNotFoundException("Board ", "Id", boardId);
		}

		return standardRespository.findById(standardId).map(standard -> {
			standardRespository.delete(standard);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Standard", "Id", standardId));
	}
}
