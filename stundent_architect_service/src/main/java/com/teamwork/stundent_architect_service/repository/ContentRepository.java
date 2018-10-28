/**
 * 
 */
package com.teamwork.stundent_architect_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamwork.stundent_architect_service.model.Content;

/**
 * @author suryateja.kasulanati
 *
 */
public interface ContentRepository extends JpaRepository<Content, Long> {
	
	public Optional<Content> findByContentLocationIgnoreCase(String cotentLocation);

}
