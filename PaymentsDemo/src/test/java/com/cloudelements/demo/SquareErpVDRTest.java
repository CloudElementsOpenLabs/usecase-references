package com.cloudelements.demo;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.cloudelements.demo.model.vdr.ErpCatalogItem;
import com.cloudelements.demo.model.vdr.ErpLocation;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SquareErpVDRTest {
	private String b1token 			= "+956CG5AIM/t7PWFf9F0nktHu2UflCcGXA9dTnMhrQg=";
	private String netsuiteToken 	= "x/WSOJF1ufVH0cvIsNR5r0RpzmLL3ST68NcG/K3lpMM=";
	

	@Before
	public void environmentSetup() {
		EnvironmentService environment = new EnvironmentService();
		environment.setOrgProperty("f17a0a6fbbe3a77e779ee1d077ef859b");
		environment.setUsrProperty("HZ7IqJzdyrZOoD2DYSmwlPUuSYe+6GSRatOBY26G3/U=");
		environment.setUrlProperty("https://staging.cloud-elements.com");
		HTTPUtil.setEnvironmentService(environment);
	}
	
	//@Test
	public void serializedLocationTest() throws ParseException, JsonParseException, JsonMappingException, IOException {
		JSONArray jsonObj = HTTPUtil.doGetArray(b1token, "/elements/api-v2/ErpLocation");
		
		ObjectMapper objectMapper	= new ObjectMapper();
		ErpLocation[] locationArr 	= objectMapper.readValue(jsonObj.toString(), ErpLocation[].class);
		
		assertNotNull(locationArr);
		
		System.out.println("\n\n**** PRINTING ARRAY ****");
		for (ErpLocation location : locationArr) {
			System.out.println(location);
		}
	}
	
	
	@Test
	public void serializedCatalogItemTest() throws ParseException, JsonParseException, JsonMappingException, IOException {
		JSONArray jsonObj = HTTPUtil.doGetArray(netsuiteToken, "/elements/api-v2/ErpCatalogItem");
		
		ObjectMapper objectMapper		= new ObjectMapper();
		ErpCatalogItem[] catalogArr 	= objectMapper.readValue(jsonObj.toString(), ErpCatalogItem[].class);
		
		assertNotNull(catalogArr);
		
		System.out.println("\n\n**** PRINTING ARRAY ****");
		for (ErpCatalogItem item : catalogArr) {
			System.out.println(item);
		}
	}
}
