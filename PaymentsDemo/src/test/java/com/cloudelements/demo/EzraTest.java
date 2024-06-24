package com.cloudelements.demo;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class EzraTest {

	@Test
	public void parseEventMessageTest() throws ParseException, JsonParseException, JsonMappingException, IOException {
		String[] keys = new String[] { "moneycorp_2" }; //, "netsuiteerpv2", "quickbooks" };
		
		
		EnvironmentService envService = new EnvironmentService();
		envService.setEzraApplicationId("8ee1a21f-f4db-4826-b259-fe4b0f0769d3");
		envService.setOrgProperty("e1f946ee90cd6b79ce08b7c37b7d1c36");
		envService.setUsrProperty("0TeUYJWd+PhNPwOUYN8ypQLLPWJnxAvCtUHNrua8WTY=");
		envService.setUrlProperty("https://staging.cloud-elements.com");
		
		HTTPUtil.setEnvironmentService(envService);

		for (String key : keys) {
			org.json.simple.JSONObject obj = HTTPUtil.doGet(null, "http://localhost:8080/getRedirectURL/" + key);
	
			System.out.println(key + " - " + obj);
		}
	}
	
	
	
}
