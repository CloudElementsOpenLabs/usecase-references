package com.cloudelements.demo.usecase.bulk;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudelements.demo.model.BulkResult;
import com.cloudelements.demo.model.BulkStatus;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

@Service
public class BulkService {
	private static final Logger logger = LoggerFactory.getLogger(BulkService.class);
	
	@Autowired
	private EnvironmentService envService;
	
	/*
	 * Will return a {bulkId} which is to be used in the following calls
	 * {resource} builds the query parameter "select * from {resource}"
	 */
	public String launchBulk (String token, String resource, String limit) throws ParseException {
		return launchBulk(token, resource, limit, true, null);
	}
	
	public String launchBulk (String token, String resource, String limit, boolean isInitialCall, String bulkId) throws ParseException {
		if (limit != null) {
			limit = "%20LIMIT%20" + limit;
		} else limit = "";
		limit = "%20LIMIT%205";
		String apiResource = null;
		if (isInitialCall) {
			apiResource = "/elements/api-v2/bulk/query?q=select%20*%20from%20" + resource + limit;
		} else { // Continue from the previous job ID
			apiResource = "/elements/api-v2/bulk/query?continueFromJobId=" + bulkId;
		}
		
		JSONObject jsonObj = HTTPUtil.doPost(token, apiResource, null);
		return String.valueOf( jsonObj.get("id") );
	}
	
	/*
	 * Once bulk is scheduled to Cloud elements, regularly try to get its status 
	 * using the returned {bulkId}
	 */
	public BulkStatus fetchStatus (String token, String bulkId) throws ParseException, JsonParseException, JsonMappingException, IOException {
		JSONObject jsonObj = HTTPUtil.doGet(token, "/elements/api-v2/bulk/" + bulkId + "/status");
		
		ObjectMapper objectMapper=new ObjectMapper();
		BulkStatus statusObj = objectMapper.readValue(jsonObj.toString(), BulkStatus.class);
		
		return statusObj;
	}
	
	
	/*
	 * Download the bulk in either CSV/JSON 
	 * Method returns a List of JSONL objects
	 * You need {bulkId} and {resource} from the first bulk schedule call
	 */
	public ArrayList<JSONObject> downloadBulk (String token, String bulkId, String resource) throws ParseException {
		JSONArray jsonArray = HTTPUtil.doGetArrayJSONL(token, "/elements/api-v2/bulk/" + bulkId + "/" + resource);
		
		try { // Might fail in JSONObject cast
			ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
			for (Object obj : jsonArray) {
				JSONObject jsonObj = (JSONObject) obj;
				
				objList.add(jsonObj);
			}
			
			return objList;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public ArrayList<JSONObject> downloadBulkPart (String token, String bulkId, String resource, int pageSize, int totalRecordCount) throws ParseException, InterruptedException {
		BulkResult bulkResult = null;
		String query = "?pageSize=" + pageSize;
		ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
		
		
		ArrayList<String> tokenList = new ArrayList<>();
		int i = 1;
		do {
			if (bulkResult != null && bulkResult.getNextPage() != null) {
				query = "?pageSize=" + pageSize + "&nextPage=" + bulkResult.getNextPage();
			}
			String url = "/elements/api-v2/bulk/" + bulkId + "/" + resource + "/data" + query;
			bulkResult = HTTPUtil.doBulkArrayGet(token, url);
			
			try { // Might fail in JSONObject cast
				for (Object obj : bulkResult.getResponseArr()) {
					JSONObject jsonObj = (JSONObject) obj;
					
					objList.add(jsonObj);
				}
				
			} catch (Exception e) {
				return null;
			}
			logger.debug("ARRSIZE " + objList.size() + " TIME " + i + " NEXTTOKEN " + bulkResult.getNextPage() + " -- " + url);
			
			i++;
			tokenList.add(bulkResult.getNextPage());
			//Thread.sleep(5000); // If not, have seen nextpage token conflicts
		}  while (bulkResult != null && bulkResult.getNextPage() != null && objList.size() <= totalRecordCount);

		System.out.println("Token list \n" + tokenList);
		
		return objList;
	}
	
	
		
	
	
	/*
	 * USING *** GSON *** - that's why this is a completely separata method (doing the same as the others)
	 * Download the bulk in either CSV/JSON 
	 * Method returns a List of JSONL objects
	 * You need {bulkId} and {resource} from the first bulk schedule call
	 */
	public JSONArray downloadBulkGSON (String token, String bulkId, String resource) throws ParseException, IOException {
		String authorization = envService.getAuthorizationHeader() + ", Element " + token;

		URL url = new URL(envService.getURL() + "/elements/api-v2/bulk/" + bulkId + "/" + resource);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", authorization);
		urlConnection.setRequestProperty("Accept", "application/json");
		
		
		JsonReader reader 	= new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        JSONArray jsonArray = new Gson().fromJson(reader, JSONArray.class);
		return jsonArray;
	}
	
}
