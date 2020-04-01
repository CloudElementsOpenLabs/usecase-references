package com.cloudelements.demo.usecase.connection;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudelements.demo.usecase.authentication.AuthenticationController;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;

@Controller
public class ConnectionViewController {

	@Autowired
	private EnvironmentService envService;
	
	@Autowired
	private AuthenticationController authenticationController;
	
	@RequestMapping(value = {"/connect/{app}"} )
	public String init(Map<String, Object> model, HttpServletRequest request, @PathVariable String app) throws ClientProtocolException, IOException {
		
		return "connection/connect_" + app + ".html";
	}
	
	
	
	@RequestMapping (value = { "/getAuthRedirect/{elementKey}" } )
	public String requestRedirect(Map<String, Object> model, HttpServletRequest request, @PathVariable String elementKey) throws ClientProtocolException, IOException, ParseException {
		
		String url = authenticationController.handleOAuth2Authentication("40", 
				"YOUR_QBO_APP_TOKEN", 
				"YOUR_QBO_APP_SECRET", 
				envService.getCallbackBaseURL() + "/handleOAuthCallback",
				"&scope=com.intuit.quickbooks.accounting&authentication.type=oauth2");
		
		model.put("redirect_url", url);
		return "connection/connect_" + elementKey + ".html";
	}
	
	
	
	@RequestMapping(value = {"/connection_successful/{token}"} )
	public String connectionSuccessful(Map<String, Object> model, HttpServletRequest request, @PathVariable String token) throws ClientProtocolException, IOException {
		
		request.getSession().setAttribute("SELECTED_TOKEN", token.replaceAll("_-_", "/"));
		
		return "connection/connection_successful.html";
	}
	
	
	
}
