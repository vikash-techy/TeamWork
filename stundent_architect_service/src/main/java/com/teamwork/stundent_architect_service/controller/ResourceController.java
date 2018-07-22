/**
 * 
 */
package com.teamwork.stundent_architect_service.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.teamwork.stundent_architect_service.service.VideoService;

/**
 * @author Surya
 *
 */
@RestController
@RequestMapping("/api/resources")
public class ResourceController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VideoService videoService;
	
	@RequestMapping(path = "/videos", method = RequestMethod.GET)
	public List<String> handleVideoRequest() {
		return videoService.listVideos();
	}

	@RequestMapping(path = "/videos/{id}", method = RequestMethod.GET)
	public StreamingResponseBody handleVideoRequest(@PathVariable("id") Integer id) {
		byte[] video = videoService.getVideo(id, 0).getVideoContent();
		return buildResponse(video);
	}

	@RequestMapping(path = "/videos/{id}/{chunk}", method = RequestMethod.GET)
	public StreamingResponseBody handleVideoRequest(@PathVariable("id") Integer id,
			@PathVariable(name = "chunk", required = false) Integer chunk) {
		int c = 0;
		if (null != chunk && chunk > 0)
			c = chunk;

		byte[] video = videoService.getVideo(id, c).getVideoContent();
		return buildResponse(video);
	}

	private StreamingResponseBody buildResponse(byte[] video) {
		return new StreamingResponseBody() {

			@Override
			public void writeTo(OutputStream out) throws IOException {
				out.write(video);
				out.flush();
			}
		};
	}

	public String getVideo() {
		return "Hello! World";
	}
}
