package com.cloudelements.demo.usecase.catalog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudelements.demo.model.Element;
import com.cloudelements.demo.usecase.element.ElementService;

@Controller
public class CatalogViewController {

	@Autowired
	private ElementService elementService;
	
	@RequestMapping(value = {"/catalog/{elementId}", "/catalog"} )
	public String welcome(Map<String, Object> model, HttpServletRequest request, @PathVariable Optional<String> elementId) throws ClientProtocolException, IOException {
		Element[] elementArr = elementService.getSerializedElementArray(null);
		model.put("elementList", elementArr);

		
		if (elementId.isPresent()) {
			model.put("element", elementService.getSerializedElement(elementId.get()));
		}
		
		return "catalog/catalog";
	}
}
