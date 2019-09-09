package com.cloudelements.demo.usecase.environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/* 
 * This view controller provides a UI to save changes to the application.properties file
 * with regards to your CE data
 * 
 * Note that after being updated you still need to restart your environment
 */
@Controller
public class EnvironmentViewController {

	@Autowired
	private Environment env;
	
	@RequestMapping("/environment")
	public String welcome(Map<String, Object> model, HttpServletRequest request) throws IOException {
		setPropertiesTo(model);
		
		return "environment/environment";
	}
	
	
	
	@RequestMapping("/updateEnvironment")
	public String postInvoice (Map<String, Object> model, HttpServletRequest request, HttpServletResponse response, 
			@RequestParam String url,
			@RequestParam String organization,
			@RequestParam String user) throws IOException {
		
		Properties props = new Properties();
	    props.setProperty("cloudelements.url", url);
	    props.setProperty("cloudelements.organization", organization);
	    props.setProperty("cloudelements.user", user);
	    
	    File f = new File("application.properties");
	    OutputStream out = new FileOutputStream( f );
	    
		DefaultPropertiesPersister p = new DefaultPropertiesPersister();
	    p.store(props, out, "");
		
	    model.put("confirmation", "Properties successfully set to application.properties. Restart your environment for them to be active.");
	    setPropertiesTo (model);
	    
	    return "environment/environment";
	}
	
	private void setPropertiesTo (Map<String, Object> model) {
		model.put("url", 			env.getProperty("cloudelements.url"));
		model.put("organization", 	env.getProperty("cloudelements.organization"));
		model.put("user", 			env.getProperty("cloudelements.user"));
	}
	
}
