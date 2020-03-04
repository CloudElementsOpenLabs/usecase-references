package com.cloudelements.demo.usecase.workshop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudelements.demo.model.Instance;
import com.cloudelements.demo.model.Resource;
import com.cloudelements.demo.model.Transformation;
import com.cloudelements.demo.model.VDR;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This is the Rest controller called by the JS
 */
@RestController
public class WorkshopRestController {

	@Autowired
	private EnvironmentService envService;
	
	
	@RequestMapping("/getTestObj")
	public JSONObject getObj () {
		
		JSONObject obj = new JSONObject();
		obj.put("testObj1", "I am a test object 1");
		obj.put("testObj2", "I am a test object 2");
		obj.put("testObj3", "I am a test object 3");
		obj.put("testObj4", "I am a test object 4");
		return obj;
	}
	
	
	@RequestMapping("/getTestArr")
	public JSONArray getArr () {
		JSONArray arr = new JSONArray();
		
		for (int i = 1; i <= 10; i++) {
			JSONObject obj = new JSONObject();
			obj.put("testObj", "I am a test object " + i);
			arr.add(obj);
		}
		return arr;
	}
	
	
	@RequestMapping("/resourceCall/{instanceToken}/{resource}")
	public JSONArray executeElementResource (@PathVariable String instanceToken, @PathVariable String resource) throws ParseException {
		return HTTPUtil.doGetArray(instanceToken, "/elements/api-v2/" + resource);
	}
	
	
}
