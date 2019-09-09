package com.cloudelements.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.springframework.stereotype.Service;

import com.cloudelements.demo.model.Element;
import com.cloudelements.demo.model.ElementConfiguration;
import com.cloudelements.demo.model.ElementConfigurationOption;
import com.cloudelements.demo.usecase.element.ElementService;

@Service
public class ElementServiceTest {

	//@Test
	public void fetchElementsTest() throws ClientProtocolException, IOException {
		ElementService service = new ElementService();
		
		Element[] arr = service.getSerializedElementArray(null);
		
		assertNotNull(arr);
	}
	
	@Test
	public void fetchNetsuiteElement() throws ClientProtocolException, IOException {
		ElementService service = new ElementService();
		
		Element el = service.getSerializedElement("188");
		
		assertNotNull(el);
		assertNotNull(el.getConfiguration());
		
		
		for (ElementConfiguration conf : el.getConfiguration()) {
			if ( "authentication.type".equals(conf.getKey())) {
				assertNotNull(conf.getOptions());
				assertEquals(2, conf.getOptions().length);
				
				for (ElementConfigurationOption option : conf.getOptions()) {
					System.out.println(option);
				}
			}
		}
	}
	
	
	
	
	
}
