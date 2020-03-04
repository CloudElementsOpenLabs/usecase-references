package com.cloudelements.demo;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.cloudelements.demo.model.Transformation;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@SpringBootTest
public class TransformationInstanceTest {
	private static String userToken = "";
	private static String orgToken = "";
	
	
	@Test
	public void createFileOutput () throws ParseException, IOException {
		String[] instanceArr = new String[] {"8944441", "8891480", "8795303", "8521612", "8915100", "8915106"};
		
		for (String instanceId : instanceArr) {
			fetchTransformation(instanceId, "javaInvoice2");
		}
		
	}
	
	
	private Transformation fetchTransformation (String id, String vdr) throws JsonParseException, JsonMappingException, IOException, ParseException {
		
		String authorization = "User " + userToken + ", Organization " + orgToken;
		
		EnvironmentService envService = new EnvironmentService ();
		envService.setOrgProperty("ba5ff859ca8f60c4f379fd104b1d9135");
		envService.setUsrProperty("8Gyea4V3hgi+I9Y0HdUwrroCPDH1YkxtJPlJJaUB5aM\\=");
		envService.setUrlProperty("https://api.cloud-elements.co.uk");
		
		
		HTTPUtil.setEnvironmentService(envService);
		
		JSONObject instanceObj = HTTPUtil.doGet (null, "/elements/api-v2/instances/" + id + "/transformations/" + vdr);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Transformation transformationObj = objectMapper.readValue(instanceObj.toJSONString(), Transformation.class);
		
		return transformationObj;
	}


}
