package com.cloudelements.demo.usecase.payments;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;

@Controller
public class PaymentsViewController {

	@Autowired
	private EnvironmentService envService;
	
	
	@RequestMapping("/paymentsInit")
	public String init(Map<String, Object> model, HttpServletRequest request) throws ClientProtocolException, IOException {
		if (!envService.cePropertiesAvailable()) {
			model.put("error", "Please set your Cloud Elements environment variables before you continue. Go to <a href='/environment'>the environment page</a> to do so.");
		}
		
		request.getSession().putValue( "PAGETITLE", "Payments initiation" );
		request.getSession().putValue("TOKEN", "YqR2zkjbqO3UBK5XvNFI2vsqJEGMj6n7kFPlwwnPWmw=");
		return "payments/paymentsInit";
	}
	
	
	@RequestMapping("/getPayables")
	public String getPayables (Map<String, Object> model, HttpServletRequest request, @RequestParam String token) throws ParseException {
		request.getSession().putValue("TOKEN", token); // Set token on request so it can be used throughout Session
		
		JSONArray payables = HTTPUtil.doGetArray(token, "/elements/api-v2/javaInvoice2");
		
		ArrayList<JSONObject> payableList = new ArrayList<JSONObject> ();
		for (int i = 0; i < payables.size(); i++) {
			JSONObject payablesObj = (JSONObject) payables.get (i);
			
			payableList.add(payablesObj);
		}
		
		model.put("payableList", payableList);
		model.put("total", calculateTotal (payableList) + " " + payableList.get(0).get("currency"));
		request.getSession().putValue("PAYABLELIST", payableList);
		
		return "payments/payables";
	}
	
	private String calculateTotal (List<JSONObject> objList) {
		double total = 0;
		for (JSONObject obj : objList) {
			total += Double.parseDouble(obj.get("total").toString());
		}
		
        DecimalFormat df = new DecimalFormat("#.##");
		return df.format(total);
	}
	
	@RequestMapping("/doPayment/{invoiceId}")
	public String doPayment (Map<String, Object> model, HttpServletRequest request, @PathVariable String invoiceId) throws ParseException {
		// Fetch latest payment information for selected invoice
		String token = request.getSession().getAttribute("TOKEN").toString();
		JSONObject payableObj = HTTPUtil.doGet( token, "/elements/api-v2/javaInvoice2/" + invoiceId);
		
		String totalVal = "";
		if (payableObj != null) {
			
			String payload = "{\n" + 
					"  \"amount\": \"" + payableObj.get("total") + "\"," + 
					"  \"vendorId\": \"" + payableObj.get("vendorId") + "\"," + 
					"  \"invoiceId\": \"" + payableObj.get("id") + "\"," + 
					"  \"currency\": \"" + payableObj.get("currency") + "\"," + 
					"  \"source\": \"1\"," + 
					"  \"paymentDate\": \"2020-02-02\"" + 
					"}";
			
			HTTPUtil.doPost(token, "/elements/api-v2/javaPaymentReceipt", payload);
			
			totalVal = "for " + payableObj.get("total") + " " + payableObj.get("currency") + " to " + payableObj.get("vendorName") + " ";
		}
		
		model.put("message", "Payment " + totalVal + "scheduled. This Payment information has been reconciled to its source!");
		
		
		
		return getPayables(model, request, token);
	}
	
	
	
}
