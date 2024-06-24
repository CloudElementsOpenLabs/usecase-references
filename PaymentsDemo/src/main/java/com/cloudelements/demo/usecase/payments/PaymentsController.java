package com.cloudelements.demo.usecase.payments;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentsController.class);
	
	@Autowired
	private PaymentDataService dataService;
	
	@RequestMapping("/getSessionPayables")
	public List<JSONObject> getPayableList (HttpServletRequest request, HttpServletResponse response) {
		return (List<JSONObject>) request.getSession().getAttribute("NEWPAYABLES");
	}
	
	
	@RequestMapping("/getPaymentDataFromService")
	public List<JSONObject> getPaymentDataFromService () {
		ArrayList<JSONObject> returnList = dataService.getPayablesList();
		
		dataService.init();
		
		return returnList;
	}
	

}
