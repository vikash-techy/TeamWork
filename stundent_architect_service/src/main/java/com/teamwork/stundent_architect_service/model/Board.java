/**
 * 
 */
package com.teamwork.stundent_architect_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author suryateja.kasulanati
 *
 */
@Entity
@Table(name = "Board")
public class Board extends ResourceSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "board_id")
	private Long boardId;
	
	@Column(name = "name")
	@NotNull
	private String name;
	
	@Column(name = "description")
	private String description;

	public Board() {
		super();
	}

	public Board(Long boardId, String name, String description) {
		super();
		this.boardId = boardId;
		this.name = name;
		this.description = description;
	}

	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", name=" + name + ", description=" + description + "]";
	}
}
