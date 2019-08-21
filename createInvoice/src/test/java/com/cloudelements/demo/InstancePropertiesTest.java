package com.cloudelements.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class InstancePropertiesTest {
	
	
	@Test
	public void createFileOutput () throws ParseException, IOException {
		String[] elementArr = new String[] {"40", "4933", "12532", "7335", "12406", "1404", "5358"};
		
		List<String> lines = new ArrayList<String>();
		for (String elementId : elementArr) {
			lines.addAll( fetchInstanceDetails (elementId) );
		}
		
		Path file = Paths.get("/Users/guyvanwert/java_output/output.txt");
		Files.write(file, lines, Charset.forName("UTF-8"));
		System.out.println(file.toAbsolutePath().toString() + " written");
	}
	
	
	public List<String> fetchInstanceDetails(String elementId) throws ParseException {
		try {
			System.out.println("Running for **** " + elementId);
			String authorization = "User HONfNsYq/SGbz/GPN9Wq/iiJRW8Oa5hn89+eYoVaLz8=, Organization 8f8ccb87520d0082c1230d1c553d83eb";
			
			HttpGet getter = new HttpGet("http://staging.cloud-elements.com/elements/api-v2/elements/" + elementId);
			getter.addHeader("Authorization", authorization);
			
			HttpClient client = new DefaultHttpClient();
			
			HttpResponse response = client.execute(getter);
	    
			
           if(response!=null){
            	InputStream in = response.getEntity().getContent() ;
            	JSONParser parser = new JSONParser();
            	JSONObject obj = (JSONObject) parser.parse(new InputStreamReader(in));
            	
            	String elementName = obj.get("name").toString();
            	
            	
            	List<String> lines = new ArrayList<String>();
            	
            	JSONArray transformations = (JSONArray) obj.get("defaultTransformations");
            	if (transformations != null) {
            		for (int y = 0; y < transformations.size(); y++) {
            			JSONObject transformObj = (JSONObject) transformations.get(y);
            			lines.add(addRow (elementName, transformObj, null));
            		}
            	}
            	
            	JSONArray resources = (JSONArray) obj.get("resources");
            	if (resources != null) {
	        		for (int i = 0; i < resources.size(); i++) {
	        			JSONObject o = (JSONObject) resources.get(i);
	        			
	        			JSONArray params = (JSONArray) o.get("parameters");
	        			if (params != null) {
		        			for (int y = 0; y < params.size(); y++) {
		        				JSONObject paramObj = (JSONObject) params.get(y);
		        			}
	        			}
	        			
	        			
	        			
	        			
	        			if ( o.get("model") != null ) {
		        			JSONObject model = (JSONObject) o.get("model");
		        			JSONObject swagger = (JSONObject) model.get("swagger");
		        			for (Object key : swagger.keySet()) {
		        				
	        					JSONObject objKey = (JSONObject) swagger.get(key);
	        					JSONObject properties = (JSONObject) objKey.get("properties");
	        					if (properties != null ) {
		        					for (Object propKey : properties.keySet()) {
		        						lines.add(addRow (elementName, o, key + "." + propKey));
		        					}
	        					}
	        				}
	        			}
	    			}
            	} 
        			
    			System.out.println("Executed for " + elementName);
    			return lines;
    		}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("**** EXCEPTION UPON CONNECTING TO URL ****");
			e.printStackTrace();
		}
		return null;
	}
	
	private String addRow (String element, JSONObject obj, String str) {
		String ceName,vendorName, method, apiType;
		
		if (obj.get("path") != null) {
			ceName = obj.get("path").toString();
		} else {
			ceName = obj.get("name").toString();
		}
		if (obj.get("vendorPath") != null) {
			vendorName = obj.get("vendorPath").toString();
		} else {
			vendorName= obj.get("vendorName").toString();
		}
		
		method = obj.get("method") != null ? obj.get("method").toString() : "";
		apiType = obj.get("type") != null ? obj.get("type").toString() : "";
		
		String returnStr = String.format("%s, %s, %s, %s, %s, %s", 
				 element, 
				 ceName, 
				 vendorName,
				 method, 
				 apiType,
				str);
		 System.out.println(returnStr);
		 return returnStr;
	}
}
