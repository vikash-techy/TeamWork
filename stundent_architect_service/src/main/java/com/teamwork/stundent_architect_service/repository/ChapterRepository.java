/**
 * 
 */
package com.teamwork.stundent_architect_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamwork.stundent_architect_service.model.Chapter;

/**
 * @author suryateja.kasulanati
 *
 */
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

	@Query(value = "select * from chapter where standard_id = ?1 ", nativeQuery = true)
	public List<Chapter> findBySubject(Long subjectId);
}
