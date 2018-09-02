/**
 * 
 */
package com.teamwork.stundent_architect_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamwork.stundent_architect_service.model.Instructor;

/**
 * @author suryateja.kasulanati
 *
 */
public interface InstructorRepository extends JpaRepository<Instructor, Long>{
	
	public Instructor findByEmailIgnoreCase(String email);
}
