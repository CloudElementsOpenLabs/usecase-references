package com.cloudelements.demo.usecase.element;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudelements.demo.model.Element;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@Service
public class ElementService {

	@Autowired
	private EnvironmentService envService;
	
	/*
	 * Returns an array of Element objects based on the query provided as param
	 */
	public Element[] getSerializedElementArray (String query) throws ClientProtocolException, IOException {
		HttpGet getter = new HttpGet(envService.getURL() + "/elements/api-v2/elements" + (query != null ? query : "") );
		
		getter.addHeader("Authorization", envService.getAuthorizationHeader());
		
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(getter);
		
        if(response!=null){
        	InputStream in = response.getEntity().getContent() ;
        	ObjectMapper mapper = new ObjectMapper();
        	try {
        		Element[] resource = mapper.readValue( new InputStreamReader (response.getEntity().getContent()), Element[].class);
        		return resource;
        	} catch (UnrecognizedPropertyException e) {
        		e.printStackTrace();
        	}
    	}
	
		return null;
	}
	
	/*
	 * Returns a single Element object based upon the elementId or elementKey. 
	 */
	public Element getSerializedElement (String elementId) throws ClientProtocolException, IOException {
		HttpGet getter = new HttpGet(envService.getURL() + "/elements/api-v2/elements/" + elementId);
		
		getter.addHeader("Authorization", envService.getAuthorizationHeader() );
		
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(getter);
		
		if(response!=null){
			InputStream in = response.getEntity().getContent() ;
			ObjectMapper mapper = new ObjectMapper();
			try {
				Element resource = mapper.readValue( new InputStreamReader (response.getEntity().getContent()), Element.class);
				return resource;
			} catch (UnrecognizedPropertyException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
