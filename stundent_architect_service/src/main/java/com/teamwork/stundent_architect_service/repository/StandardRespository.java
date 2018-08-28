/**
 * 
 */
package com.teamwork.stundent_architect_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamwork.stundent_architect_service.model.Board;
import com.teamwork.stundent_architect_service.model.Standard;

/**
 * @author suryateja.kasulanati
 *
 */
public interface StandardRespository extends JpaRepository<Standard, Long> {

	public List<Standard> findByBoard(Board board);
}
