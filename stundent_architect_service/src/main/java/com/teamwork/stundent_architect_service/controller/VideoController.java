/**
 * 
 */
package com.teamwork.stundent_architect_service.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author suryateja.kasulanati
 *
 */
@Controller
public class VideoController {

	@MessageMapping("/student-architect-ws")
	@SendTo("/topic/greetings")
	public String greeting(String message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return "Hello, " + message + "!";
	}
}
