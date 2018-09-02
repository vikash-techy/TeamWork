/**
 * 
 */
package com.teamwork.stundent_architect_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamwork.stundent_architect_service.model.Subject;

/**
 * @author suryateja.kasulanati
 *
 */
public interface SubjectRepository extends JpaRepository<Subject, Long> {

	@Query(value = "select * from subject where standard_id = ?1 ", nativeQuery = true)
	public List<Subject> findByStandard(Long standardId);
}
