package com.cloudelements.demo.usecase.welcome;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudelements.demo.usecase.environment.EnvironmentService;

@Controller
public class WelcomeViewController {

	@Autowired
	private EnvironmentService envService;
	
	
	@RequestMapping(value = {"/", "/init"} )
	public String init(Map<String, Object> model, HttpServletRequest request) throws ClientProtocolException, IOException {
		if (!envService.cePropertiesAvailable()) {
			model.put("error", "Please set your Cloud Elements environment variables before you continue. Go to <a href='/environment'>the environment page</a> to do so.");
		}
		
		request.getSession().putValue( "PAGETITLE", "Payables portal" );
		
		return "welcome/welcome";
	}
	
}
