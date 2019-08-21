package com.cloudelements.demo.util;

import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AuthenticationUtil {

	public static JSONObject createAuthConfiguration (String elKey, String elName, String[] elTags, HashMap<String, String> elConfig ) {
		JSONObject all = new JSONObject();
		
		JSONObject element = new JSONObject();
		element.put("key", elKey);
		
		JSONObject configuration = new JSONObject();
		
		for (String key : elConfig.keySet()) {
			configuration.put(key, elConfig.get(key));
		}
		
		JSONArray tags = new JSONArray();
		tags.addAll(Arrays.asList( elTags) );
		
		all.put("element", element);
		all.put("configuration", configuration);
		all.put("tags", tags );
		all.put("name", elName);	
		
		return all;
	}
}
