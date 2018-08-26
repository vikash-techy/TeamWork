/**
 * 
 */
package com.teamwork.stundent_architect_service.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.teamwork.stundent_architect_service.model.Board;
import com.teamwork.stundent_architect_service.service.BoardService;

/**
 * @author suryateja.kasulanati
 *
 */
@RestController
public class BoardController implements ApiController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService boardService;

	@GetMapping("/boards")
	public List<Board> getAll() {
		return boardService.getAllBoards();
	}

	@RequestMapping("/boards/{id}")
	public Optional<Board> getBoard(@PathVariable Long id) {
		return boardService.getBoard(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/boards")
	public Board addBoard(@RequestBody Board board) {
		System.out.println("Creating the Board: " + board);
		return boardService.addBoard(board);
	}
}
