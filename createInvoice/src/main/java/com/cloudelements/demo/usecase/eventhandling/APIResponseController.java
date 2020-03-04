package com.cloudelements.demo.usecase.eventhandling;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloudelements.demo.model.APIEvent;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This restcontroller allows the app to always listen for incoming EVENTS.
 * Meaning upon element authentication you can just plug in your_url:8080/apiResponse
 * 
 * As our event structure is standard across the platform, the handleAPIResponse method will parse
 * the retrieved json into an APIEvent object
 */

@RestController
public class APIResponseController {

	private static final Logger logger = LoggerFactory.getLogger(APIResponseController.class);	
	
	@RequestMapping(value="/apiResponse", method=RequestMethod.POST)

	public void handleAPIResponse(@RequestBody String payload, HttpServletRequest request) throws ParseException, JsonParseException, JsonMappingException, IOException {
		Object obj 			= new JSONParser().parse(payload);
		JSONObject jsonObj 	= (JSONObject) obj;
		jsonObj 			= (JSONObject) jsonObj.get("message");
		

		String token = "FORCE_TOKEN_IN";
		if (request.getSession().getAttribute("TOKEN") != null) {
			token = request.getSession().getAttribute("TOKEN").toString();
		}
		try {
			JSONArray eventsArr = (JSONArray) jsonObj.get("events");
			String objectId = String.valueOf(((JSONObject) eventsArr.get(0)).get("objectId"));
			
			String invoiceId = objectId.substring(0, objectId.indexOf("|"));
			
			JSONObject payableObj = HTTPUtil.doGet( token, "/elements/api-v2/javaInvoice2/" + invoiceId);
			
			ArrayList<JSONObject> objList = (ArrayList<JSONObject>) request.getSession().getAttribute("NEWPAYABLES");
			if (objList == null) {
				objList = new ArrayList<JSONObject>();
			}
			
			objList.add(payableObj);
			
			request.getSession().putValue("NEWPAYABLES", objList);
		} catch (Exception e) {
			System.out.println("Failed to capture payable from source system ");
		}
		System.out.println("\n\n****EVENT RECEIVED****");
		System.out.println(payload);

	}
	
}
