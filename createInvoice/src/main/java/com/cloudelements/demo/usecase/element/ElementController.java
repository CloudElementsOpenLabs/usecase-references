package com.cloudelements.demo.usecase.element;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudelements.demo.model.Element;

@RestController
public class ElementController {

	@Autowired
	private ElementService elementService;
	
	/* 
	 * Is used on the authentication page. Whenever the user clicks an icon, this call is done and returns a 
	 * serialized Element object
	 */
	@RequestMapping ("/getElement/{key}")
	public Element getElement (HttpServletRequest request, HttpServletResponse response, @PathVariable String key) throws ClientProtocolException, IOException {
		return elementService.getSerializedElement(key);
	}

}
