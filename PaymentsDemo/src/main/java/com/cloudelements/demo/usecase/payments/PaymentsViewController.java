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

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.CustomSessionTokenService;
import com.cloudelements.demo.util.HTTPUtil;

@Controller
public class PaymentsViewController {

	@Autowired
	private EnvironmentService envService;
	
	@Autowired
	private PaymentDataService dataService;
	
	@Autowired
	private CustomSessionTokenService sessionService;
	
	private static DecimalFormat df2 = new DecimalFormat("###,###,###.##");
	

	
	@RequestMapping("/getPayables")
	public String getPayables (Map<String, Object> model, HttpServletRequest request) throws ParseException {
		ArrayList<JSONObject> payableList = (ArrayList<JSONObject>) request.getSession().getAttribute("PAYABLELIST");
		
		double total =0;
		if (payableList == null || request.getParameter("init") != null) {
			JSONArray payables = HTTPUtil.doGetArray(sessionService.getToken(), "/elements/api-v2/javaInvoice2?pageSize=20");
			dataService.init();
			
			payableList = new ArrayList<JSONObject>();
			for (int i = 0; i < payables.size(); i++) {
				JSONObject payablesObj = (JSONObject) payables.get (i);
				
				try {
					total += Double.parseDouble( String.valueOf(payablesObj.get("total")) );
					payablesObj.put("totalStr", df2.format( Double.parseDouble( payablesObj.get("total").toString() )) );
				} catch (NumberFormatException e) {
					payablesObj.put("totalStr", 0);
				}
				payableList.add(payablesObj);
			}
			
			request.getSession().setAttribute("PAYABLELIST", payableList);
			model.put("mytotal", String.valueOf(total));
		}
		model.put("payableList", payableList);
		model.put("totalOutstanding", String.valueOf( df2.format( Double.parseDouble(String.valueOf(calculateTotal (payableList) ))) ) + " " + payableList.get(0).get("currency"));

		return "payments/payables";
	}
	
	private Double calculateTotal (List<JSONObject> objList) {
		double total = 0;
		for (JSONObject obj : objList) {
			try {
				total += Double.parseDouble(obj.get("total").toString());
			} catch (NullPointerException e) {
				total += 0;
			}
		}
		
        
		return total;
	}
	
	@RequestMapping("/doPayment/{invoiceId}")
	public String doPayment (Map<String, Object> model, HttpServletRequest request, @PathVariable String invoiceId) throws ParseException {
		// Fetch latest payment information for selected invoice
		JSONObject payableObj = HTTPUtil.doGet( sessionService.getToken(), "/elements/api-v2/javaInvoice2/" + invoiceId);
		
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
			
			HTTPUtil.doPost(sessionService.getToken(), "/elements/api-v2/javaPaymentReceipt", payload);
			
			totalVal = "for " + payableObj.get("total") + " " + payableObj.get("currency") + " to " + payableObj.get("vendorName") + " ";
		}
		model.put("message", "Payment " + totalVal + "scheduled. This payment information has been reconciled to its source!");
		return getPayables(model, request);
	}
	
	
	
}
