/**
 * 
 */
package com.teamwork.stundent_architect_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamwork.stundent_architect_service.model.Standard;

/**
 * @author suryateja.kasulanati
 *
 */
public interface StandardRepository extends JpaRepository<Standard, Long> {

	@Query(value = "select * from standard where board_id = ?1 ", nativeQuery = true)
	public List<Standard> findByBoard(Long boardId);
}
