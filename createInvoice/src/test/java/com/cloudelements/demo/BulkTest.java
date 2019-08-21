package com.cloudelements.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.cloudelements.demo.model.BulkStatus;
import com.cloudelements.demo.usecase.bulk.BulkService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class BulkTest {

	@Test
	public void fetchBulkInParts() throws ParseException, JsonParseException, JsonMappingException, IOException, NumberFormatException, InterruptedException {
		Runtime runtime = Runtime.getRuntime();
	    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
	    
		
		Date startTime = new Date();
		BulkService service = new BulkService();
		
		System.out.println("*** initiating bulk");
		//String token = "5MMqNiRuycVKJ7GJu4mAZIdC12ROFVDQwfJLslMyctM=";
		//String resource = "myLead";
		String token = "6zgi0Flx/8mwEWCsRlTviCcd3edle+qZeDob292g/Ts=";
		String resource = "Vendor";
		String bulkId = service.launchBulk(token, resource, null);
		
		assertNotNull(bulkId);
		
		
		System.out.println("*** Fetching status");
		BulkStatus status = null;
		do {
			System.out.println("***** Fetching bulk " + bulkId + " status");
			
			status = service.fetchStatus(token, bulkId);
			if (!isCompleted(status)) {
				Thread.sleep(50);
			}
		} while ( !isCompleted(status) ); 
		
		assertNotNull(status);
		assertEquals("COMPLETED", status.getStatus());
		
		/* DO BULK DOWNLOAD */
		ArrayList<JSONObject> arr = service.downloadBulkPart(token, bulkId, "myLead", 200, Integer.parseInt(status.getRecordsCount()) );
		System.out.println("EXPECTED RESULT SIZE: " + status.getRecordsCount());
		System.out.println("ACTUAL SIZE AFTER DOWNLOAD: " + arr.size());
		
		/* PRINT MEMORY USAGE */
		Date endTime = new Date();
		printTimeDifference (startTime, endTime);
		
		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used Memory before " + usedMemoryBefore / (1024 * 1024) + " MB");
		System.out.println("Used Memory after " + usedMemoryAfter / (1024 * 1024) + " MB");
		System.out.println("Memory increased " + (usedMemoryAfter-usedMemoryBefore) / (1024 * 1024) + " MB");
	}
	
	//@Test
	public void fetchBulkWithGSON() throws ParseException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		Runtime runtime = Runtime.getRuntime();
	    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
	    
		
		Date startTime = new Date();
		String limit = null;
		String token = "5MMqNiRuycVKJ7GJu4mAZIdC12ROFVDQwfJLslMyctM=";
		BulkService service = new BulkService();

		System.out.println("*** initiating bulk");
		String resource = "myLead";
		String bulkId = service.launchBulk(token, resource, limit);
		
		assertNotNull(bulkId);
		
		
		System.out.println("*** Fetching status");
		BulkStatus status = null;
		do {
			System.out.println("***** Fetching bulk " + bulkId + " status");
			
			status = service.fetchStatus(token, bulkId);
			if (!isCompleted(status)) {
				Thread.sleep(50);
			}
		} while ( !isCompleted(status) ); 
		
		assertNotNull(status);
		assertEquals("COMPLETED", status.getStatus());
		
		
		System.out.println("*** Downloading bulk");
		
		//JSONArray arr = service.downloadBulkGSON(token, bulkId, resource);

		ArrayList<JSONObject> arr = service.downloadBulk(token, bulkId, resource);
		for (Object obj : arr) {
			System.out.println( obj );
		}
		
		assertNotNull(arr);
		
		System.out.println("* DONE (BulkId " + bulkId + ")* - Size: " + arr.size() );
		Date endTime = new Date();
		printTimeDifference (startTime, endTime);
		
		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used Memory before " + usedMemoryBefore / (1024 * 1024) + " MB");
		System.out.println("Used Memory after " + usedMemoryAfter / (1024 * 1024) + " MB");
		System.out.println("Memory increased " + (usedMemoryAfter-usedMemoryBefore) / (1024 * 1024) + " MB");
	}
	
	
	/*
	 * Run bulk download all at once, only fetch when CE is done
	 */
//	@Test
	public void fetchBulkAllTest() throws ParseException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		Date startTime = new Date();
		String limit = null;
		String token = "5MMqNiRuycVKJ7GJu4mAZIdC12ROFVDQwfJLslMyctM=";
		BulkService service = new BulkService();

		System.out.println("*** initiating bulk");
		String resource = "leads";
		String bulkId = service.launchBulk(token, resource, limit);
		
		assertNotNull(bulkId);
		
		
		System.out.println("*** Fetching status");
		BulkStatus status = null;
		do {
			System.out.println("***** Fetching bulk " + bulkId + " status");
			
			status = service.fetchStatus(token, bulkId);
			if (!isCompleted(status)) {
				Thread.sleep(50);
			}
		} while ( !isCompleted(status) ); 
		
		assertNotNull(status);
		assertEquals("COMPLETED", status.getStatus());
		
		
		System.out.println("*** Downloading bulk");
		ArrayList<JSONObject> objList = service.downloadBulk(token, bulkId, resource);
		
		assertNotNull(objList);
		if (limit != null) {
			assertEquals(limit, objList.size());
		}
		
		System.out.println("* DONE * - Size: " + objList.size() );
		Date endTime = new Date();
		printTimeDifference (startTime, endTime);
	}
	
	
	/*
	 * Run bulk download multiple times using LIMIT x
	 */
	//@Test
	public void fetchBulkWithLimitTest() throws ParseException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		Date startTime = new Date();
		
		String token 					= "5MMqNiRuycVKJ7GJu4mAZIdC12ROFVDQwfJLslMyctM=";
		String bulkId 					= null;
		String limit 					= "3";
		String resource 				= "leads";
		BulkService service 			= new BulkService();
		ArrayList<JSONObject> objList = new ArrayList<>();

		System.out.println("*** initiating bulk");
		
		boolean continueFromBulk = true;
		
		for (int i = 0; i < 3; i++) {
			
			// Do bulk call
			if (i > 0) {
				continueFromBulk = false;
			}
			if (bulkId == null) {
				bulkId = service.launchBulk (token, resource, limit, continueFromBulk, bulkId);
			} else {
				service.launchBulk (token, resource, limit, continueFromBulk, bulkId);
			}
			assertNotNull(bulkId);
		
			
			// Fetch status for bulk
			System.out.println("*** Fetching status");
			BulkStatus status = null;
			do {
				System.out.println("***** Fetching bulk " + bulkId + " status");
				
				status = service.fetchStatus(token, bulkId);
				if (!isCompleted(status)) {
					Thread.sleep(50);
				}
			} while ( !isCompleted(status) ); 
		
			assertNotNull(status);
			assertEquals("COMPLETED", status.getStatus());
			
			System.out.println("*** Downloading bulk");
			objList.addAll( service.downloadBulk(token, bulkId, resource) );
			
			
			System.out.println("\n\n\n\n");
				System.out.println("MOD ON RUN " + i + " = " + (objList.size() % Integer.parseInt(limit)));
			System.out.println("\n\n\n\n");
			
		}
		
		for (JSONObject obj : objList) {
			System.out.println(obj.get("FirstName"));
		}
		
		if (objList.size() % Integer.parseInt(limit) != 0) { // we didn't receive the full batch yet
			
		}
		
		
		System.out.println("* DONE * - Size: " + objList.size() );
		Date endTime = new Date();
		printTimeDifference (startTime, endTime);
	}

	private boolean isCompleted(BulkStatus status) {
		return status != null && "COMPLETED".equals(status.getStatus());
	}
	
	private void printTimeDifference (Date start, Date end) {
		System.out.println("Start " + start + "\nEnd   " + end);
		
		long seconds = (end.getTime() - start.getTime())/1000;
		System.out.println("Total of " + seconds + " seconds");
	}
}
