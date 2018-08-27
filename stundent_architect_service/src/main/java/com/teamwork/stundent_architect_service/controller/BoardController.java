/**
 * 
 */
package com.teamwork.stundent_architect_service.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.teamwork.stundent_architect_service.exception.ResourceNotFoundException;
import com.teamwork.stundent_architect_service.model.Board;
import com.teamwork.stundent_architect_service.repository.BoardRespository;

/**
 * @author suryateja.kasulanati
 *
 */
@RestController
public class BoardController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardRespository boardRespository;

	@GetMapping("/boards")
	public Resources<Board> getAll() {
		final List<Board> boards = boardRespository.findAll();

		for (final Board board : boards) {
			Link selfLink = linkTo(methodOn(BoardController.class).getBoard(board.getBoardId())).withSelfRel();
			board.add(selfLink);
		}

		Link allBoardsLink = linkTo(methodOn(BoardController.class).getAll()).withSelfRel();
		Resources<Board> resources = new Resources<Board>(boards, allBoardsLink);

		return resources;
	}

	@RequestMapping("/boards/id/{id}")
	public Resource<Board> getBoard(@PathVariable Long id) {
		Optional<Board> board = boardRespository.findById(id);
		if (!board.isPresent())
			throw new ResourceNotFoundException("Board", "Id", id);

		Resource<Board> resource = new Resource<Board>(board.get());
		resource.add(linkTo(methodOn(BoardController.class).getBoard(board.get().getBoardId())).withSelfRel());
		resource.add(linkTo(methodOn(this.getClass()).getAll()).withRel("all-boards"));

		return resource;
	}

	@RequestMapping("/boards/name/{name}")
	public Resources<Board> getBoardByName(@PathVariable String name) {
		final List<Board> boards = boardRespository.findByNameIgnoreCaseContaining(name);
		for (final Board board : boards) {
			Link selfLink = linkTo(methodOn(BoardController.class).getBoard(board.getBoardId())).withSelfRel();
			board.add(selfLink);
		}

		Link allBoardsLink = linkTo(methodOn(BoardController.class).getAll()).withRel("all-boards");
		Resources<Board> resources = new Resources<Board>(boards, allBoardsLink);

		return resources;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/boards")
	public Resource<Board> addBoard(@RequestBody Board board) {
		Board newBoard = boardRespository.save(board);

		Link selfLink = linkTo(methodOn(BoardController.class).getBoard(newBoard.getBoardId())).withSelfRel();

		Resource<Board> resource = new Resource<Board>(newBoard, selfLink);
		resource.add(linkTo(methodOn(this.getClass()).getAll()).withRel("all-boards"));

		return resource;
	}

	@PutMapping("/boards/{id}")
	public Resource<Board> updateBoard(@PathVariable Long id, @RequestBody Board boardDetails) {
		Optional<Board> boardOptional = boardRespository.findById(id);
		if (!boardOptional.isPresent())
			throw new ResourceNotFoundException("Board", "Id", id);

		boardDetails.setBoardId(id);
		boardRespository.save(boardDetails);

		Link selfLink = linkTo(methodOn(BoardController.class).getBoard(boardDetails.getBoardId())).withSelfRel();

		Resource<Board> resource = new Resource<Board>(boardDetails, selfLink);
		resource.add(linkTo(methodOn(this.getClass()).getAll()).withRel("all-boards"));

		return resource;
	}

	@DeleteMapping("/boards/{id}")
	public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
		try {
			boardRespository.deleteById(id);
		} catch (DataAccessException e) {
			throw new ResourceNotFoundException("Board", "Id", id);
		}
		return ResponseEntity.ok().build();
	}
}
