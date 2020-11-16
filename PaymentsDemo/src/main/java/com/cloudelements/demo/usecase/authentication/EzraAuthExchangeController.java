package com.cloudelements.demo.usecase.authentication;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.CustomSessionTokenService;
import com.cloudelements.demo.util.HTTPUtil;

@RestController
public class EzraAuthExchangeController {

	@Autowired
	private EnvironmentService envService;
	
	@Autowired
	private CustomSessionTokenService sessionService;
	
	/*
	 * After successful Ezra application setup within CE use the sessions API to generate a URL to guide your users to
	 */
	@RequestMapping (value = "/getRedirectURL/{elementKey}", method = RequestMethod.GET)
	public JSONObject getRedirectURL (HttpServletResponse response, @PathVariable String elementKey) throws ParseException, IOException {
		sessionService.setElement(elementKey);
		String sessionURL = "/v1alpha1/elements/normalized-instances/applications/" + envService.getEzraApplicationId() + "/sessions?elementKey=" + elementKey;
		
		JSONObject obj = HTTPUtil.doPost(null, sessionURL, null);
		return obj;
	}
	
	
	/*
	 * Listens to incoming responses from user flow moving through Ezra.  
	 * The end result will be the CE instance token ID
	 * 
	 * In the Ezra application setup, please setup your response URL + "/authListener"
	 * When setting up OAuth apps for 3rd party applications such as QBO, Xero, ... use that exact same URL incl the /authListener
 	 * 
	 */
	@RequestMapping (value = "/authListener")
	public void remoteAuthListener (HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestBody String payload) throws ParseException, IOException {
		System.out.println(payload);
		/*
		 * Retrieve token & store in database
		 */
		JSONParser parse = new JSONParser();
		JSONObject obj = (JSONObject) parse.parse(payload);
		
		String token = ((JSONObject) obj.get("elementInstance")).get("token").toString();
		
		System.out.println("Token received " + token);
		
		sessionService.setToken(token);
	}
}
