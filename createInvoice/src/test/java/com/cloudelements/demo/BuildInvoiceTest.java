package com.cloudelements.demo;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import com.cloudelements.demo.model.InvoiceTwo;
import com.cloudelements.demo.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BuildInvoiceTest {

	@Test
	public void buildInvoiceTest() {
		double totalAmount = 0; // without tax
		Product prod = new Product("123", "10", "20"); 
		
		totalAmount += Integer.valueOf(prod.getQuantity()) * Double.parseDouble(prod.getRate());
		double total = totalAmount * Double.parseDouble( "21" ) / 100;
		
		JSONObject obj = new JSONObject();
		obj.put("amount", totalAmount);
		obj.put("currency", "USD");
		obj.put("entity", "111");
		obj.put("description", "random product");
		obj.put("totalValue", String.valueOf(total) );
		
		obj.put("customForm.internalId", "123");
		obj.put("dueDate", "12/12/2019");

		JSONArray itemArr = new JSONArray();
		JSONObject itemObj = new JSONObject();
		itemObj.put("quantity", prod.getQuantity());
		itemObj.put("description", "BUILD INVOICE");
		itemObj.put("id", prod.getId());
		
		itemArr.add(itemObj);
		obj.put("item", itemArr);

		obj.put("message", "JUNIT Build");
		obj.put("subsidiary", "4");
		
		System.out.println(obj.toJSONString());
	}
	
	
	@Test
	public void BuildUsingObject () throws JsonProcessingException {
		double totalAmount = 0; // without tax
		Product prod = new Product("123", "10", "20"); 
		
		totalAmount += Integer.valueOf(prod.getQuantity()) * Double.parseDouble(prod.getRate());
		double total = totalAmount * Double.parseDouble( "21" ) / 100;
		
		
		InvoiceTwo myInvoice = new InvoiceTwo ();
		
		myInvoice.setAmount( String.valueOf(totalAmount) );
		myInvoice.setCurrency("USD");
		myInvoice.setEntity("111");
		myInvoice.setDescription("random product");
		myInvoice.setTotalValue(String.valueOf(total));
		myInvoice.setDueDate("12/12/2019");
		
		ArrayList<Product> prodList = new ArrayList<Product>();
		prodList.add(prod);
		myInvoice.setItem(prodList);
		myInvoice.setMessage ("JUNIT Build");
		myInvoice.setSubsidiary ("4");
		
		
		ObjectMapper mapper = new ObjectMapper ();
		System.out.println( mapper.writeValueAsString( myInvoice ) );
	}
}
