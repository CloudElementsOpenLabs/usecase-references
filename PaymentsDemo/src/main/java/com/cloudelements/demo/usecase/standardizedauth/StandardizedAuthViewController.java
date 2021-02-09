package com.cloudelements.demo.usecase.standardizedauth;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;

@Controller
public class StandardizedAuthViewController {

	@Autowired
	private EnvironmentService envService;
	
	
	@RequestMapping(value = {"/authtest"} )
	public String authtest(Map<String, Object> model, HttpServletRequest request) throws ClientProtocolException, IOException, ParseException {
		
		request.getSession().putValue( "PAGETITLE", "Standardized auth test" );
		
		/*
		JSONArray arr = HTTPUtil.doGetArray(null, "/elements/api-v2/elements");
		
		for (Object o : arr) {
			JSONObject obj = (JSONObject) o;
			
			String key = obj.get("key").toString();
			String sessionURL = "/v1alpha1/elements/normalized-instances/applications/" + envService.getEzraApplicationId() + "/sessions?elementKey=" + key;
			JSONObject objPost = HTTPUtil.doPost(null, sessionURL, null);
			System.out.println(key + " - " + objPost);
		
		}
		*/
			
		return "standardizedauth/auth";
	}
	
}
