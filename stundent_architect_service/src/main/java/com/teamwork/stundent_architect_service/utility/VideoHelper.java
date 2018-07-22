/**
 * 
 */
package com.teamwork.stundent_architect_service.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

import com.teamwork.stundent_architect_service.model.VideoMetadata;

/**
 * @author Surya
 *
 */
public class VideoHelper {

	public static final String VIDEO_SRC = "C:\\\\Workspace\\\\Videos\\\\";
	public static final int DEFAULT_BUFFER_SIZE = 20480;
	public static final String DEFAULT_FILE_TYPE = ".mp4";

	@SuppressWarnings("resource")
	public static byte[] loadVideoContent(int id, int chunk) {

		byte[] videoBytes = null;

		// create absolute filename
		String fileName = VIDEO_SRC + id + DEFAULT_FILE_TYPE;

		File videoFile = new File(fileName);
		if (videoFile.exists()) {
			// calculate position from chunk
			long position = DEFAULT_BUFFER_SIZE * chunk;

			// if position is greater than file length then throw error
			if (position > videoFile.length()) {
				throw new RuntimeException("Invalid chunk access request!");
			}

			// create a file channel
			FileChannel fileChannel = null;
			try {
				fileChannel = new RandomAccessFile(videoFile, "r").getChannel();
				videoBytes = new byte[DEFAULT_BUFFER_SIZE];
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// create a buffer of default size
			ByteBuffer buffer = MappedByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
			try {
				if (fileChannel.read(buffer, position) > 0) {
					buffer.flip();
					videoBytes = Arrays.copyOf(buffer.array(), buffer.limit());
					buffer.clear(); // do something with the data and clear/compact it.
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return videoBytes;
		} else {
			throw new RuntimeException("Oops! No such video found");
		}
	}

	public static List<String> searchVideos(String videoName) {
		File dir = new File(VIDEO_SRC);
		if (null == videoName) {
			FilenameFilter typeFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(DEFAULT_FILE_TYPE);
				}
			};
			String[] children = dir.list(typeFilter);
			if (children == null) {
				throw new RuntimeException("Either dir does not exist or is not a directory");
			} else {
				return Arrays.asList(children);
			}
		} else {
			FilenameFilter nameFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(videoName);
				}
			};
			String[] children = dir.list(nameFilter);
			if (children == null) {
				throw new RuntimeException("Either dir does not exist or is not a directory");
			} else {
				return Arrays.asList(children);
			}
		}

	}

	public static VideoMetadata loadVideoMetadata(int id) {
		return new VideoMetadata();
	}

}
