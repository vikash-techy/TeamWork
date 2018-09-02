/**
 * 
 */
package com.teamwork.stundent_architect_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamwork.stundent_architect_service.model.Section;

/**
 * @author suryateja.kasulanati
 *
 */
public interface SectionRepository extends JpaRepository<Section, Long> {

	@Query(value = "select * from section where chapter_id = ?1 ", nativeQuery = true)
	public List<Section> findByChapter(Long chapterId);}
