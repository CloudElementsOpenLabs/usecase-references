package com.cloudelements.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.HTTP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.thymeleaf.util.ArrayUtils;

import com.cloudelements.demo.model.BulkResult;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Utility class that holds easy methods to use to make CE GET/POST/DELETE calls 
 */
public class HTTPUtil {
	public static String LOCALDIRECTORY = System.getProperty("user.dir") + "/expensemanagement";

	private static EnvironmentService envService;
	public static void setEnvironmentService(EnvironmentService service) {
		envService = service;
	}
	
	public static void doDelete (String token, String URLStr) {
		String authorization = envService.getAuthorizationHeader() + (token != null ?  ", Element " + token : "");
		String contentType	 = "application/json";
		
		try {
			HttpClient client = new DefaultHttpClient();
            HttpDelete put = new HttpDelete(envService.getURL() + URLStr);
            put.addHeader("authorization", authorization);
			client.execute(put);
		} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JSONObject doPut (String token, String URLStr, Object jsonObject) {
		String authorization = envService.getAuthorizationHeader() + (token != null ?  ", Element " + token : "");
		String contentType	 = "application/json";
		
		HttpClient client = new DefaultHttpClient();
        try{
        	ObjectMapper mapper = new ObjectMapper();
        	String jsonBody = mapper.writeValueAsString(jsonObject);

        	HttpPut put = new HttpPut(envService.getURL() + URLStr);
            put.addHeader("authorization", authorization);
            StringEntity se = new StringEntity(  jsonBody );  
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, contentType));
            put.setEntity(se);
            se.setContentType(contentType);

            HttpResponse response = client.execute(put);
            
            if(response!=null) {
            	InputStream in = response.getEntity().getContent() ;
            	JSONParser parser = new JSONParser();
            	Object obj = parser.parse(new InputStreamReader(in));
            	if (obj instanceof JSONObject) {
            		JSONObject jsonObj = (JSONObject) obj;
            		if (jsonObj.get("message") != null) {
            			System.out.println("Received cloud elements error ");
            			System.out.println(jsonObj.get("message"));
            			System.out.println(jsonObj.get("providerMessage"));
            		} else {
            			return jsonObj;
            		}
            	}
            }
        } catch(Exception e){
            e.printStackTrace();
        }
		
		
		return null;
	}
	/*
	 * Either returns 
	 * 	null -> When exception occurred / end point failure ("message" is part of the jsonObject)
	 *  JSONObject -> when a proper response was received
	 */
	public static JSONObject doPost (String token, String URLStr, String jsonBody) {
		String authorization = envService.getAuthorizationHeader() + (token != null ?  ", Element " + token : "");
		String contentType	 = "application/json";
		
		HttpClient client = new DefaultHttpClient();
        try{
            HttpPost post = new HttpPost(envService.getURL() + URLStr);
            post.addHeader("authorization", authorization);
            
            if (jsonBody != null) {
            	StringEntity se = new StringEntity(  jsonBody );  
	            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, contentType));
	            post.setEntity(se);
	            se.setContentType(contentType);
            }
            HttpResponse response = client.execute(post);
            
            if(response!=null) {
            	InputStream in = response.getEntity().getContent() ;
            	JSONParser parser = new JSONParser();
            	Object obj = parser.parse(new InputStreamReader(in));
            	if (obj instanceof JSONObject) {
            		JSONObject jsonObj = (JSONObject) obj;
            		if (jsonObj.get("message") != null) {
            			System.out.println("Received cloud elements error ");
            			System.out.println(jsonObj.get("message"));
            		} else {
            			return jsonObj;
            		}
            	}
            }
        } catch(Exception e){
            e.printStackTrace();
        }
		
		
		return null;
	}
	
	
	
	public static JSONObject doGet (String token, String URLStr) throws ParseException {
		String authorization = envService.getAuthorizationHeader() + ( token != null ? ", Element " + token : "");
		
		HttpGet getter = new HttpGet( envService.getURL() +  URLStr);
		getter.addHeader("Authorization", authorization);
		getter.addHeader("Accept", "application/json"); // This forces bulk to return json instead of csv
		
		try {
			HttpClient client 		= new DefaultHttpClient();
			HttpResponse response 	= client.execute(getter);
			if(response!=null){
            	InputStream in = response.getEntity().getContent() ;
            	JSONParser parser = new JSONParser();
            	Object obj = parser.parse(new InputStreamReader(in));
            	return (JSONObject) obj;
			}
        	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static JSONArray doGetArray (String token, String URLStr) throws ParseException {
		String authorization = envService.getAuthorizationHeader() + ( token != null ? ", Element " + token : "");
		
		HttpGet getter = new HttpGet( envService.getURL() +  URLStr);
		getter.addHeader("Authorization", authorization);
		getter.addHeader("Accept", "application/json"); // This forces bulk to return json instead of csv
		
		try {
			HttpClient client 		= new DefaultHttpClient();
			HttpResponse response 	= client.execute(getter);
			if(response!=null){
            	InputStream in = response.getEntity().getContent() ;
            	JSONParser parser = new JSONParser();
            	Object obj = parser.parse(new InputStreamReader(in));
            	return (JSONArray) obj;
			}
        	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static JSONArray doGetArrayJSONL (String token, String URLStr) throws ParseException {
		String authorization = envService.getAuthorizationHeader() + (token == null ? "" : ", Element " + token);
		
		HttpGet getter = new HttpGet( envService.getURL() +  URLStr);
		getter.addHeader("Authorization", authorization);
		getter.addHeader("Accept", "application/json"); // This forces bulk to return json instead of csv
		
		try {
			HttpClient client 		= new DefaultHttpClient();
			HttpResponse response 	= client.execute(getter);
			
			if (! ArrayUtils.isEmpty(response.getHeaders("Elements-Next-Page-Token"))) {
				System.out.println("NEXT PAGE TOKEN: " + ((BufferedHeader) response.getHeaders("Elements-Next-Page-Token")[0]).getValue());
			}
			
			
			if(response != null){
            	InputStream in = response.getEntity().getContent() ;
            	JSONParser parser = new JSONParser();
            	
            	// **** INPUTSTREAMREADER *** Read file at once
            	//Object obj = parser.parse(new InputStreamReader(in));
            	//return (JSONArray) obj;
            	
            	
            	// **** BUFFERED READER
            	InputStream inputStream = response.getEntity().getContent();

            	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        	    StringBuilder builder = new StringBuilder();
        	    String line;
        	    JSONArray arr = new JSONArray();
        	    while ((line = bufferedReader.readLine()) != null) {
        	        builder.append(line + "\n");
        	        JSONObject obj = (JSONObject) parser.parse(line);
        	        arr.add(obj);
        	    }

        	    return arr;
        	   // return (JSONArray) parser.parse(builder.toString());
            	
			}
        	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static BulkResult doBulkArrayGet (String token, String URLStr) throws ParseException {
		String authorization = envService.getAuthorizationHeader() + ", Element " + token;
		
		HttpGet getter = new HttpGet( envService.getURL() +  URLStr);
		getter.addHeader("Authorization", authorization);
		
		try {
			HttpClient client 		= new DefaultHttpClient();
			HttpResponse response 	= client.execute(getter);
			if(response!=null){
            	InputStream in = response.getEntity().getContent() ;
            	JSONParser parser = new JSONParser();
            	Object obj = parser.parse(new InputStreamReader(in));
            	
            	Header nextPageToken = response.getFirstHeader("Elements-Next-Page-Token");
            	String nextPageTokenStr = null;
            	if (nextPageToken != null) {
            		nextPageTokenStr = nextPageToken.getValue();
            	} else {
            		nextPageToken = null;
            	}
            	BulkResult result 	 = new BulkResult (nextPageTokenStr, (JSONArray) obj);
            	
            	return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static String writeURLToLocalDisk(String sourceURL, String fileNameId, String mediaType, String subDir) throws IOException {
		String extension = mediaType.substring(mediaType.indexOf("/") +1);
		String filename = LOCALDIRECTORY + File.separator + (subDir != null ? subDir + File.separator : "") + fileNameId + "." + extension;
		
	    URL url = new URL(sourceURL);
	    try {
	    	FileUtils.copyURLToFile(url, new File(filename));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
	    return filename;
	}
	
	
	public static void saveSessionToken (HttpServletRequest request, String element, String token) {
		@SuppressWarnings("unchecked")
		Map<String, String> tokenMap = (Map<String, String>) request.getSession().getAttribute("TOKEN_MAP");
		if (tokenMap == null) {
			tokenMap = new HashMap<String, String>();
		}
		tokenMap.put(element, token);
		request.getSession().setAttribute("TOKEN_MAP", tokenMap);
	}
}
