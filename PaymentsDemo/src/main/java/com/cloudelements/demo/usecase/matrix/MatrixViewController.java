package com.cloudelements.demo.usecase.matrix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;

import lombok.Data;

@Controller
public class MatrixViewController {

	@Autowired
	private EnvironmentService envService;
	private String token = "DGiUmPRnblT97vK3nDAuyBTLFvXOlRmSA4tGWl/IbYA=";
	
	@RequestMapping(value = {"/getMatrix"} )
	public String getAllHubMatrix (Map<String, Object> model, HttpServletRequest request) throws ParseException {
		JSONArray hubElements = HTTPUtil.doGetArray(null, "/elements/api-v2/hubs");
		
		if (request.getSession().getAttribute("matrix") == null) {
			int i = 0;
			for (Object obj : hubElements) {
				i++;
				JSONObject hubObj = (JSONObject) obj;
				
				queryEventDetails(request, hubObj.get("key").toString());
				//if (i == 2) break;
			}
		}
		
		model.put("matrix", request.getSession().getAttribute("matrix"));
		model.put("events", request.getSession().getAttribute("events"));
		
		return "matrix/matrix";
	}
	
	@RequestMapping(value = {"/getMatrix/{hub}"} )
	public String getMatrix (Map<String, Object> model, HttpServletRequest request, @PathVariable String hub) throws ParseException {
		
		if (request.getSession().getAttribute("matrix") == null) {
		
			queryEventDetails(request, hub);
		}

		model.put("matrix", request.getSession().getAttribute("matrix"));
		model.put("events", request.getSession().getAttribute("events"));
		
		return "matrix/matrix";
	}

	private void queryEventDetails(HttpServletRequest request, String hub) throws ParseException {
		HashMap<String, MatrixObj> matrix = new HashMap<String, MatrixObj>();
		if (request.getSession().getAttribute("matrix") != null) {
			matrix = (HashMap<String, MatrixObj>) request.getSession().getAttribute("matrix");
		}
		
		HashSet<String> eventSet = new HashSet<String> ();
		if (request.getSession().getAttribute("events") != null) {
			eventSet = (HashSet<String>) request.getSession().getAttribute("events");
		}
		
		JSONObject hubElements = HTTPUtil.doGet(token, "/elements/api-v2/elementsForHub/" + hub);
		JSONArray objectArr = (JSONArray) hubElements.get("objects");
		
		if (objectArr != null) {
			for (Object elObj : objectArr) {
				JSONObject elementObj = (JSONObject) elObj;
				
				try {
					JSONArray eventArr = HTTPUtil.doGetArray(token, "/elements/api-v2/eventsForElement2/" + elementObj.get("key"));
					
					System.out.println(hub + " EVENTS FOR " + elementObj.get("key"));
					eventSet.addAll(eventArr); // Have one var that holds all events
					
					MatrixObj matrixObj = new MatrixObj();
					matrixObj.hub = hub;
					matrixObj.elementKey = elementObj.get("key").toString();
					matrixObj.elementName = elementObj.get("name").toString();
					matrixObj.supportedEvents = eventArr;
					
					matrix.put ( matrixObj.getElementKey(), matrixObj);
				} catch (Exception e) {
				}
			}
		} else { 
			System.out.println(">>>> " + hub + " has no elements");
		}
		
		
		request.getSession().setAttribute("matrix", matrix);
		request.getSession().setAttribute("events", eventSet);
		
		System.out.println(matrix);
	}
	
	@Data
	private class MatrixObj {
		String hub;
		String elementKey;
		String elementName;
		JSONArray supportedEvents;
	}
	
}


