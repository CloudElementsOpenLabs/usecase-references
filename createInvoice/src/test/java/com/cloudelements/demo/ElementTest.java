package com.cloudelements.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BufferedHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.thymeleaf.util.ArrayUtils;

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;

@WebMvcTest (EnvironmentService.class)
public class ElementTest {

	
	@Test
	public void getAllElements() throws ParseException {
		//EnvironmentService envService = new EnvironmentService();
		//private String urlProperty = "cloudelements.url";
		//private String orgProperty = "cloudelements.organization";
		//private String usrProperty = "cloudelements.user";
		/*
		envService.setUsrProperty("8Gyea4V3hgi+I9Y0HdUwrroCPDH1YkxtJPlJJaUB5aM=");
		envService.setOrgProperty("ba5ff859ca8f60c4f379fd104b1d9135");
		envService.setUrlProperty("https://api.cloud-elements.co.uk");
		HTTPUtil.setEnvironmentService(envService);
		*/

		
		String authorization = "User CPJ4cs42CGv0gdWkjIFRdlUn0diRQBVp+juSxN9/Ig8=, Organization ddea88152f99768f50e88b098c9fbda3";
		
		HttpGet getter = new HttpGet( "https://api.cloud-elements.com/elements/api-v2/elements" );
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
        	        System.out.println(line);
        	        JSONArray obj = (JSONArray) parser.parse(line);
        	        arr.add(obj);
        	    }

            	int i =0;
        	    for (Object arrObj : arr.toArray()) {
        	    	JSONArray obj = (JSONArray) arrObj;
        	    	for (Object o : obj.toArray()) {
        	    		JSONObject jsonObj = (JSONObject) o;
        	    		System.out.println(jsonObj.get("name") + "," + jsonObj.get("hub"));
        	    		i++;
        	    	}
        	    }
        	    System.out.println("#Elements: " + i);
			}
        	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}
