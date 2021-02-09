package com.cloudelements.demo.usecase.vdr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudelements.demo.usecase.payments.PaymentDataService;
import com.cloudelements.demo.util.HTTPUtil;

@RestController
public class VDRRestController {
	
	@Autowired
	private PaymentDataService dataService;
	private static File file;
	
	@RequestMapping(value = {"/getVDRs"} )
	public JSONArray getVDRs(HttpServletRequest request) throws ParseException {
		
		
		return HTTPUtil.doGetArray(null, "/elements/api-v2/vdrs");
		
	}
	
	@RequestMapping(value = {"/getVDRs/{id}"} )
	public JSONObject getVDRById(HttpServletRequest request, @PathVariable String id) throws ParseException {
		return HTTPUtil.doGet(null, "/elements/api-v2/vdrs/" + id);
	}
	
	
	@RequestMapping(value = {"/getVDRDetail/{elementId}/{vdrName}"} )
	public JSONObject getVDRMapping(HttpServletRequest request, @PathVariable String elementId, @PathVariable String vdrName) throws ParseException {
		return HTTPUtil.doGet(null, "/elements/api-v2/organizations/elements/" + elementId + "/transformations/" + vdrName);
	}
	
	
	
	
	@RequestMapping(value = { "/getVDRFieldOptions/{elementId}/vendors" } )
	public ArrayList<JSONObject> getVDRFieldOptions(HttpServletRequest request, @PathVariable String elementId) throws ParseException {
		return getVDRFieldList(elementId);
	}
	
	
	public ArrayList<JSONObject> getVDRFieldList (String elementId) throws ParseException {
		JSONObject vdrObj = HTTPUtil.doGet(null, "/elements/api-v2/elements/" + elementId + "/objects/vendors/metadata?method=GET") ;
		
		//JSONObject vdrObj = HTTPUtil.doGet(null, "/elements/api-v2/objects/vendors/metadata?method=GET") ;
		
		JSONArray fieldArr = null;
		ArrayList<JSONObject> vdrFieldList = new ArrayList<JSONObject>();
		try {
			fieldArr = (JSONArray) vdrObj.get("fields");
			for (int i = 0; i < fieldArr.size(); i++) {
				JSONObject vendorObj = (JSONObject) fieldArr.get (i);
				
				vdrFieldList.add(vendorObj);
			}
		} catch (Exception e) {
			System.out.println("Failed to fetch vdr field list for vendor data **** NullPointerException");
		}
		
		return vdrFieldList;
	}
	
	
	

	@RequestMapping(value = {"/refreshVendorList"} )
	public String refreshVendorList (Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		file = new File("eventDetails.txt");
	
		System.out.println("Checking for  event .... ");
		if (file.exists()) {
			file.delete();
			return "REDIRECT";
		}
		return "DONOTHING";
	}
}
