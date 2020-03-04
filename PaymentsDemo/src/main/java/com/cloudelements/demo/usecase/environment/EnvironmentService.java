package com.cloudelements.demo.usecase.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.Data;

/*
 * This service is used to set the CE properties into the application.properties file
 * Apart from that provides easy authorization header + url setup for whenever the app needs to do CE calls
 */
@Service
@Data
public class EnvironmentService {

	@Autowired
	private Environment env;
	
	private String urlProperty = "cloudelements.url";
	private String orgProperty = "cloudelements.organization";
	private String usrProperty = "cloudelements.user";
	
	public boolean cePropertiesAvailable () {
		
		if (StringUtils.isEmpty(env.getProperty(urlProperty)) || 
				StringUtils.isEmpty(env.getProperty(orgProperty)) || 
				StringUtils.isEmpty(env.getProperty(usrProperty)) ) {
			return false;
		}
		
		return true;
	}
	
	
	public String getAuthorizationHeader () {
		if (env != null) {
			return "Organization " + env.getProperty(orgProperty) + ", User " + env.getProperty(usrProperty);
		} else {
			return "Organization " + orgProperty + ", User " + usrProperty;
		}
			
	}
	
	public String getURL () {
		if (env != null) {
			return env.getProperty(urlProperty);
		} else {
			return urlProperty;
		}
	}
}
