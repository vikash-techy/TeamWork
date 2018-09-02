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

/**
 * @author suryateja.kasulanati
 *
 */
@Entity
@Table(name = "content")
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_id")
	private Long contentId;

	@Column(name = "content_type")
	@NotBlank
	private String contentType;

	@Column(name = "content_location")
	private String cotentLocation;

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

	public String getCotentLocation() {
		return cotentLocation;
	}

	public void setCotentLocation(String cotentLocation) {
		this.cotentLocation = cotentLocation;
	}

	@Override
	public String toString() {
		return "Content [contentId=" + contentId + ", contentType=" + contentType + ", cotentLocation=" + cotentLocation
				+ "]";
	}

}
