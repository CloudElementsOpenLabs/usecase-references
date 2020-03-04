package com.cloudelements.demo;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.cloudelements.demo.util.AuthenticationUtil;

public class AuthenticationTest {

	

	
	@Test
	public void testJSONConfig () {
		HashMap<String, String> configMap = new HashMap<>();
		configMap.put("netsuite.sandbox", String.valueOf(false));
		JSONObject jsonAuth = AuthenticationUtil.createAuthConfiguration("netsuiteerpv2", "myInstance", new String[] {"test_tag"}, configMap);
		System.out.println(jsonAuth.toJSONString());
	/*
		{
			   "element": {
			     "key": "netsuiteerpv2"
			   },
			   "configuration": {
			      "netsuite.sandbox": false,
			   },
			   "tags": [
			     "test_tag"
			   ],
			   "name": "my_instance"
			 }
	*/
	}
}
