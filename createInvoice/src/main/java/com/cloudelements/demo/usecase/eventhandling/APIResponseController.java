package com.cloudelements.demo.usecase.eventhandling;

import java.io.IOException;

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
	public void handleAPIResponse(@RequestBody String payload) throws ParseException, JsonParseException, JsonMappingException, IOException {
		logger.debug("*** Received event *** \n" + payload);
		
		
		Object obj 			= new JSONParser().parse(payload);
		JSONObject jsonObj 	= (JSONObject) obj;
		jsonObj 			= (JSONObject) jsonObj.get("message");
		
		JSONArray eventsArr = (JSONArray) jsonObj.get("events");
		
		for (int i = 0; i < eventsArr.size(); i++) {
			JSONObject currObject = (JSONObject) eventsArr.get(i);
			
			ObjectMapper mapper = new ObjectMapper();
			APIEvent apiEvent = mapper.readValue (currObject.toJSONString(), APIEvent.class);
			
			logger.debug("**** EVENT " + i + " ****");
			logger.debug(apiEvent.getObjectType() + " " + apiEvent.getEventType() + " with id " + apiEvent.getObjectId());
		}
	}
	
}
