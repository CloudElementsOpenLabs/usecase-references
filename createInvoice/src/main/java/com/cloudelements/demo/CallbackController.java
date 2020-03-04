package com.cloudelements.demo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {
	private static final Logger logger = LoggerFactory.getLogger(CallbackController.class);
	
	@RequestMapping ("/authCallBack")
	public String authCallback (HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException, ParseException {
		System.out.println("Callback method!");
		String code = (String) request.getParameter("code");
		String state = (String) request.getParameter("state");
		
		System.out.println("code " + code);
		System.out.println("state " + state);
		//https://auth.cloudelements.io/oauth?state=sonos&code=9f30fd4a-d1e9-4cd8-bac0-a8145b887c53
		
		response.sendRedirect("be.pixala.app://tokenreceived.com?code=" + code);
		return "Call back hit";
	}
	
	

}
