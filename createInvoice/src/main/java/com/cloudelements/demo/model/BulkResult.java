package com.cloudelements.demo.model;

import org.json.simple.JSONArray;

import lombok.Data;

@Data
public class BulkResult {
	private String nextPage;
	private JSONArray responseArr;
	
	public BulkResult(String nextPage, JSONArray arr) {
		this.nextPage = nextPage;
		this.responseArr = arr;
	}
}
