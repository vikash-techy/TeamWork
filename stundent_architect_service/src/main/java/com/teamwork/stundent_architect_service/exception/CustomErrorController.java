package com.teamwork.stundent_architect_service.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author Surya
 *
 */
@RestController
public class CustomErrorController implements ErrorController {
	@Autowired
	private ErrorAttributes errorAttributes;

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping("/error")
	public String error(HttpServletRequest servletRequest, Map<String, Object> model) {
		Map<String, Object> attrs = errorAttributes.getErrorAttributes(new ServletWebRequest(servletRequest), false);
		model.putAll(attrs);
		return "error";
	}

}