package com.cloudelements.demo.usecase.bulk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudelements.demo.model.BulkStatus;
import com.cloudelements.demo.usecase.authentication.AuthenticationController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class BulkController {
	private static final Logger logger = LoggerFactory.getLogger(BulkController.class);
	
	@Autowired
	private BulkService bulkService;
	
	/*
	 * Use this method for CE to initiate a bulk call.  The bulkService will do a POST /bulk/resource
	 * Example call is /doBulk/Accounts (or a VDR)
	 * 
	 * Returns the bulk ID from CE (Store this as you need it to get status / download the file
	 */
	@RequestMapping ("/doBulk/{resource}")
	public String doBulk (HttpServletRequest request, HttpServletResponse response, @PathVariable String resource) throws ClientProtocolException, IOException, ParseException {
		String token 	= getToken(request);
		String bulkId 	= bulkService.launchBulk(token, resource, null);
		
		return bulkId;
	}
	
	
	/*
	 * Use this method to fetch the status of a bulk operation. You will need the bulk ID from the doBulk method
	 * Returns a BulkStatus object
	 */
	@RequestMapping ("/fetchBulkStatus/{bulkId}")
	public BulkStatus fetchStatus (HttpServletRequest request, HttpServletResponse response, @PathVariable String bulkId) throws JsonParseException, JsonMappingException, ParseException, IOException {
		String token 		= getToken(request);
		BulkStatus status 	= bulkService.fetchStatus(token, bulkId);
		
		return status;
	}


	/*
	 * Use this method to download the bulk file. CE sends it in either csv/json. This method only handles JSON return
	 * Per line in the bulk, the bulkService will parse the line into an actual JSONObject
	 * 
	 * For the purpose of this demo app, notice that the bulk result is stored on session so it would be accessible 
	 * over multiple pages within the app.
	 * 
	 * Returns the size of the array
	 */
	@RequestMapping ("/processDownload/{bulkId}/{resource}")
	public String processBulkDownload (Map<String, Object> model, HttpServletRequest request, @PathVariable String bulkId, @PathVariable String resource) throws ParseException, IOException {
		ArrayList<JSONObject> objList = bulkService.downloadBulk(BulkController.getToken(request), bulkId, resource);
		request.getSession().setAttribute("MODEL_" + resource, objList);
		
		logger.debug("Downloaded " + objList.size() + " records stored as MODEL_" + resource);
		return String.valueOf(objList.size());
	}
	
	//TODO REMOVE ME
	public static String getToken(HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("SELECTED_TOKEN");
		return token;
	}

}
