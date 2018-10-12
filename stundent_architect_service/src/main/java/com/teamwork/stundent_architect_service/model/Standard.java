/**
 * 
 */
package com.teamwork.stundent_architect_service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author suryateja.kasulanati
 *
 */
@Entity
@Table(name = "standard")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Standard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "standard_id")
	private Long standardId;

	@Column(name = "name")
	@NotBlank
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "board_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Board board;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public Long getStandardId() {
		return standardId;
	}

	public void setStandardId(Long standardId) {
		this.standardId = standardId;
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

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Standard [standardId=" + standardId + ", name=" + name + ", description=" + description + ", board="
				+ board + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
