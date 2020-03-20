package com.cloudelements.demo.usecase.vendors;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;

@Controller
public class VendorViewController {

	@Autowired
	private EnvironmentService envService;
	
	
	@RequestMapping(value = {"/getVendors"} )
	public String getPayables (Map<String, Object> model, HttpServletRequest request) throws ParseException {
		JSONArray vendorArr = HTTPUtil.doGetArray(request.getSession().getAttribute("SELECTED_TOKEN").toString(), "/elements/api-v2/javaVendor");
		
		ArrayList<JSONObject> vendorList = new ArrayList<JSONObject>();
		for (int i = 0; i < vendorArr.size(); i++) {
			JSONObject vendorObj = (JSONObject) vendorArr.get (i);
			
			vendorList.add(vendorObj);
		}
		
		model.put("vendorList", vendorList);
		request.getSession().setAttribute("vendorList", vendorList);
		
		return "vendor/vendors";
	}
	
	
	
}
