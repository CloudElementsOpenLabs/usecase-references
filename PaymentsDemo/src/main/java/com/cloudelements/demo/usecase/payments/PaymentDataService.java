package com.cloudelements.demo.usecase.payments;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class PaymentDataService {
	private static final Logger logger = LoggerFactory.getLogger(PaymentDataService.class);
	
	private ArrayList<JSONObject> payablesList;
	
	
	public void init () {
		payablesList = new ArrayList<JSONObject>();
	}
}
