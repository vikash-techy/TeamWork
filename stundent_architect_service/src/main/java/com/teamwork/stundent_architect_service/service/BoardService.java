/**
 * 
 */
package com.teamwork.stundent_architect_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamwork.stundent_architect_service.model.Board;
import com.teamwork.stundent_architect_service.repository.BoardRespository;

/**
 * @author suryateja.kasulanati
 *
 */
@Service
public class BoardService {

	@Autowired
	private BoardRespository boardRespository;

	public List<Board> getAllBoards() {
		return boardRespository.findAll();
	}

	public Board addBoard(Board board) {
		return boardRespository.save(board);
	}

	public Optional<Board> getBoard(Long id) {
		return boardRespository.findById(id);
	}
}
