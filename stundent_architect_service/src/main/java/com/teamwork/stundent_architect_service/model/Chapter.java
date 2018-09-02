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
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author suryateja.kasulanati
 *
 */
@Entity
@Table(name = "chapter")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Chapter extends ResourceSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chapter_id")
	private Long chapterId;

	@Column(name = "name")
	@NotBlank
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subject_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "instructor_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Instructor instructor;

	@Column(name = "is_mandatory")
	private boolean isMandatory;

	@Column(name = "is_free")
	private boolean isFree;

	@Column(name = "time_required_in_sec")
	private Long timeRequiredInSecs;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public Long getTimeRequiredInSecs() {
		return timeRequiredInSecs;
	}

	public void setTimeRequiredInSecs(Long timeRequiredInSecs) {
		this.timeRequiredInSecs = timeRequiredInSecs;
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
		return "Chapter [chapterId=" + chapterId + ", name=" + name + ", description=" + description + ", subject="
				+ subject + ", instructor=" + instructor + ", isMandatory=" + isMandatory + ", isFree=" + isFree
				+ ", timeRequiredInSecs=" + timeRequiredInSecs + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}

}
