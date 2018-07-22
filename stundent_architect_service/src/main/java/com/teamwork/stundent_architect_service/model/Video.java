/**
 * 
 */
package com.teamwork.stundent_architect_service.model;

import org.springframework.stereotype.Component;

import com.teamwork.stundent_architect_service.utility.VideoHelper;

/**
 * @author Surya
 *
 */
@Component
public class Video {

	private int id;
	private VideoMetadata videoMetadata;
	private byte[] videoContent;

	public Video() {}

	private Video(int id, VideoMetadata videoMetadata, byte[] videoContent) {
		super();
		this.id = id;
		this.videoMetadata = videoMetadata;
		this.videoContent = videoContent;
	}

	public int getId() {
		return id;
	}

	public VideoMetadata getVideoMetadata() {
		return videoMetadata;
	}

	public byte[] getVideoContent() {
		return videoContent;
	}

	public static class VideoBuilder {
		public static Video getVideo(int id, int chunk) {
			byte[] videoBytes = VideoHelper.loadVideoContent(id, chunk);
			VideoMetadata metadata = VideoHelper.loadVideoMetadata(id);
			Video video = new Video(id, metadata, videoBytes);
			return video;
		}
	}
}
