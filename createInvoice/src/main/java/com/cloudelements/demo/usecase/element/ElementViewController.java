package com.cloudelements.demo.usecase.element;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudelements.demo.model.Element;
import com.cloudelements.demo.model.ElementConfiguration;
import com.cloudelements.demo.usecase.environment.EnvironmentService;

/*
 * TODO: remove the defaults in the welcome method but are currently easy to debug
 */
@Controller
public class ElementViewController {

	@Autowired
	private ElementService elementService;
	
	@Autowired
	private EnvironmentService envService;
	
	@RequestMapping ("/elementDetail/{key}")
	public String welcome(Map<String, Object> model, HttpServletRequest request, @PathVariable String key) throws ClientProtocolException, IOException {
		Element el = elementService.getSerializedElement(key);
		
		model.put("element", el);
		
		return "createinvoice/elementDetail";
	}
	
}
