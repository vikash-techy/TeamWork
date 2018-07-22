/**
 * 
 */
package com.teamwork.stundent_architect_service.model;

import java.util.Date;

/**
 * @author Surya
 *
 */
public class VideoMetadata {

	private String name;
	private long length;
	private String fileType;
	private Date lastModified;

	public String getName() {
		return name;
	}

	public long getLength() {
		return length;
	}

	public String getFileType() {
		return fileType;
	}

	public Date getLastModified() {
		return lastModified;
	}

}
