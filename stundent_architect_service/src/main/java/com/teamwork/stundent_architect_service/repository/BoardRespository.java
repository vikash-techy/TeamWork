/**
 * 
 */
package com.teamwork.stundent_architect_service.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamwork.stundent_architect_service.model.Board;

/**
 * @author suryateja.kasulanati
 *
 */
@Transactional
public interface BoardRespository extends JpaRepository<Board, Long> {
	
	public List<Board> findByNameIgnoreCaseContaining(String name);
}
