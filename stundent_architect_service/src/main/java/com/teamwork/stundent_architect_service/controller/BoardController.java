/**
 * 
 */
package com.teamwork.stundent_architect_service.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Collection<Board> getAll() {
		final List<Board> boards = boardRespository.findAll();
		if (boards.isEmpty())
			throw new ResourceNotFoundException("Boards", "", null);
		
		return boards;
	}

	@GetMapping("/boards/id/{id}")
	public Board getBoard(@PathVariable Long id) {
		Optional<Board> board = boardRespository.findById(id);
		if (!board.isPresent())
			throw new ResourceNotFoundException("Board", "Id", id);

		return board.get();
	}

	@GetMapping("/boards/name/{name}")
	public Collection<Board> getBoardByName(@PathVariable String name) {
		final List<Board> boards = boardRespository.findByNameIgnoreCaseContaining(name);
		if (boards.isEmpty())
			throw new ResourceNotFoundException("Boards", "name", name);
		
		return boards;
	}

	@PostMapping(value = "/boards")
	public Board addBoard(@RequestBody Board board) {
		Board newBoard = boardRespository.save(board);
		return newBoard;
	}

	@PutMapping("/boards/{id}")
	public Board updateBoard(@PathVariable Long id, @RequestBody Board boardDetails) {
		Optional<Board> boardOptional = boardRespository.findById(id);
		if (!boardOptional.isPresent())
			throw new ResourceNotFoundException("Board", "Id", id);

		boardDetails.setBoardId(id);
		Board updatedBoard = boardRespository.save(boardDetails);

		return updatedBoard;
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
