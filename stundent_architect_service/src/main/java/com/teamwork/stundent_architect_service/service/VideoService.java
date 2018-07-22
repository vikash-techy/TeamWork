/**
 * 
 */
package com.teamwork.stundent_architect_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teamwork.stundent_architect_service.model.Video;
import com.teamwork.stundent_architect_service.model.Video.VideoBuilder;
import com.teamwork.stundent_architect_service.utility.VideoHelper;

/**
 * @author Surya
 *
 */
@Service
public class VideoService {

	public Video getVideo(int id, int chunk) {
		return VideoBuilder.getVideo(id, chunk);
	}

	public List<String> listVideos() {
		return VideoHelper.searchVideos("");
	}
}
