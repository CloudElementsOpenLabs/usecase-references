package com.cloudelements.demo.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudelements.demo.usecase.environment.EnvironmentService;

/*
 * This class helps injecting the required services in the HTTPUtil class. This makes sure the util class can reuse the application.properties defined variables
 */

@Component
public class StaticContextInitializer {
	
	@Autowired
	private EnvironmentService envService;
	
	@PostConstruct
	public void init() {
		HTTPUtil.setEnvironmentService (envService);
	}
}
