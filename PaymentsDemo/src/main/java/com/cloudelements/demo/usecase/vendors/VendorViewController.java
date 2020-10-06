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
import com.cloudelements.demo.usecase.payments.PaymentDataService;
import com.cloudelements.demo.usecase.vdr.VDRRestController;
import com.cloudelements.demo.util.HTTPUtil;

@Controller
public class VendorViewController {

	@Autowired
	private EnvironmentService envService;
	
	@Autowired
	private VDRRestController vdrController;
	
	
	@Autowired
	private PaymentDataService dataService;
	
	@RequestMapping(value = {"/getVendors"} )
	public String getPayables (Map<String, Object> model, HttpServletRequest request) throws ParseException {
		ArrayList<JSONObject> vendorList = (ArrayList<JSONObject>) request.getSession().getAttribute("vendorList");
				
				
		if (vendorList == null || request.getParameter("init")!= null) {
			JSONArray vendorArr = HTTPUtil.doGetArray(request.getSession().getAttribute("SELECTED_TOKEN").toString(), "/elements/api-v2/javaVendor?pageSize=20");
			
			vendorList = new ArrayList<JSONObject>();
			for (int i = 0; i < vendorArr.size(); i++) {
				JSONObject vendorObj = (JSONObject) vendorArr.get (i);
				
				vendorList.add(vendorObj);
			}
			request.getSession().setAttribute("vendorList", vendorList);
		}
		
		model.put("vendorList", vendorList);
		return "vendor/vendors";
	}
	
	
	
	
	@RequestMapping(value = {"/getVendorsLimited/{limit}"} )
	public String getPayablesLimit5 (Map<String, Object> model, HttpServletRequest request, @PathVariable String limit) throws ParseException {
			JSONArray vendorArr = HTTPUtil.doGetArray(request.getSession().getAttribute("SELECTED_TOKEN").toString(), "/elements/api-v2/javaVendor?pageSize=" + limit);
			
			ArrayList<JSONObject> vendorList = new ArrayList<JSONObject>();
			for (int i = 0; i < vendorArr.size(); i++) {
				JSONObject vendorObj = (JSONObject) vendorArr.get (i);
				
				vendorList.add(vendorObj);
			}
		
		model.put("vendorList", vendorList);
		model.put("vdrFieldList", vdrController.getVDRFieldList("170")); // Display all options for VDR dropdowns for the given element // hardcoded to QBO
		return "connection/connection_successful.html";
	}
	
	
	
	@RequestMapping(value = {"/saveVendorVDRMapping"} )
	public String saveVendorVDRMapping (Map<String, Object> model, HttpServletRequest request) throws ParseException {
		return getPayablesLimit5(model, request, "5");
	}
	

	
	
}
