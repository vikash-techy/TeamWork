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
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author suryateja.kasulanati
 *
 */
@Entity
@Table(name = "content")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "contentId")
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_id")
	private Long contentId;

	@Column(name = "content_type")
	@NotBlank
	private String contentType;

	@Column(name = "content_location", unique = true)
	private String contentLocation;

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentLocation() {
		return contentLocation;
	}

	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}

	@Override
	public String toString() {
		return "Content [contentId=" + contentId + ", contentType=" + contentType + ", contentLocation=" + contentLocation
				+ "]";
	}

}
