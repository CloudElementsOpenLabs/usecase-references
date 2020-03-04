package com.cloudelements.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.cloudelements.demo.model.APIEvent;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class APIResponseTest {

	@Test
	public void parseEventMessageTest() throws ParseException, JsonParseException, JsonMappingException, IOException {
		Object obj = new JSONParser().parse(getPayload());
		assertTrue(obj instanceof JSONObject);
		
		JSONObject jsonObj = (JSONObject) obj;
		assertTrue(jsonObj.get("message") != null);

		jsonObj = (JSONObject) jsonObj.get("message");
		assertNotNull(jsonObj);
		
		JSONArray eventsArr = (JSONArray) jsonObj.get("events");
		JSONObject currObject = (JSONObject) eventsArr.get(0);
		
		ObjectMapper mapper = new ObjectMapper();
		APIEvent apiEvent = mapper.readValue (currObject.toJSONString(), APIEvent.class);
		
		assertNotNull(apiEvent);
		assertTrue(apiEvent.getObjectType().equals("invoices"));
		
	}
	
	// This is an example payload of an event produced by Cloud Elements
	private String getPayload () { 
		return "{\n" + 
				"	\"severity\": \"medium\",\n" + 
				"	\"createdDate\": \"Mon Aug 20 15:30:07 UTC 2018\",\n" + 
				"	\"topic\": \"instance-7950647-netsuiteerpv2-events\",\n" + 
				"	\"action\": \"create\",\n" + 
				"	\"id\": \"1496771\",\n" + 
				"	\"message\": {\n" + 
				"		\"elementKey\": \"netsuiteerpv2\",\n" + 
				"		\"accountId\": 874,\n" + 
				"		\"eventId\": \"AWVX9OTIhdQRdI1F7Q7v\",\n" + 
				"		\"companyId\": 291,\n" + 
				"		\"instanceId\": 7950647,\n" + 
				"		\"instance_id\": 7950647,\n" + 
				"		\"instanceName\": \"Local netsuite\",\n" + 
				"		\"instanceTags\": [\"Local netsuite\"],\n" + 
				"		\"raw\": {\n" + 
				"			\"invoices\": [{\n" + 
				"				\"tranId\": \"INV10000925\",\n" + 
				"				\"estGrossProfitPercent\": 100,\n" + 
				"				\"salesRep\": {\n" + 
				"					\"internalId\": \"-5\",\n" + 
				"					\"name\": \"Alex Wolfe\"\n" + 
				"				},\n" + 
				"				\"dueDate\": \"2018-07-16T07:00:00Z\",\n" + 
				"				\"taxTotal\": 0,\n" + 
				"				\"source\": \"Web Services\",\n" + 
				"				\"subTotal\": 2000,\n" + 
				"				\"internalId\": \"14682\",\n" + 
				"				\"total\": 2000,\n" + 
				"				\"currencyName\": \"USA\",\n" + 
				"				\"exchangeRate\": 1,\n" + 
				"				\"terms\": {\n" + 
				"					\"internalId\": \"2\",\n" + 
				"					\"name\": \"Net 30\"\n" + 
				"				},\n" + 
				"				\"currency\": {\n" + 
				"					\"internalId\": \"1\",\n" + 
				"					\"name\": \"USA\"\n" + 
				"				},\n" + 
				"				\"timeDiscPrint\": false,\n" + 
				"				\"customFieldList\": {\n" + 
				"					\"customField\": [{\n" + 
				"						\"internalId\": \"4563\",\n" + 
				"						\"scriptId\": \"custbody_cust_priority\",\n" + 
				"						\"value\": 50\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"179\",\n" + 
				"						\"scriptId\": \"custbody_fmt_finance_app\",\n" + 
				"						\"value\": false\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"181\",\n" + 
				"						\"scriptId\": \"custbody_fmt_finance_declined\",\n" + 
				"						\"value\": false\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"180\",\n" + 
				"						\"scriptId\": \"custbody_fmt_req_financial_app\",\n" + 
				"						\"value\": false\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"174\",\n" + 
				"						\"scriptId\": \"custbody_fmt_req_sales_manager_app\",\n" + 
				"						\"value\": false\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"169\",\n" + 
				"						\"scriptId\": \"custbody_fmt_sales_manager_app\",\n" + 
				"						\"value\": false\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"171\",\n" + 
				"						\"scriptId\": \"custbody_fmt_sales_manager_declined\",\n" + 
				"						\"value\": false\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"167\",\n" + 
				"						\"scriptId\": \"custbody_fmt_senior_exec_declined\",\n" + 
				"						\"value\": false\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"168\",\n" + 
				"						\"scriptId\": \"custbody_fmt_senior_executive_app\",\n" + 
				"						\"value\": false\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"4\",\n" + 
				"						\"scriptId\": \"custbody_promisedate\",\n" + 
				"						\"value\": \"2018-08-24T07:00:00Z\"\n" + 
				"					}, {\n" + 
				"						\"internalId\": \"197\",\n" + 
				"						\"scriptId\": \"custbody_promisedate_2\",\n" + 
				"						\"value\": \"2018-08-24T07:00:00Z\"\n" + 
				"					}]\n" + 
				"				},\n" + 
				"				\"toBeFaxed\": false,\n" + 
				"				\"totalCostEstimate\": 0,\n" + 
				"				\"itemCostDiscPrint\": false,\n" + 
				"				\"shippingCost\": 0,\n" + 
				"				\"toBePrinted\": false,\n" + 
				"				\"lastModifiedDate\": \"2018-08-20T15:29:06Z\",\n" + 
				"				\"expCostDiscPrint\": false,\n" + 
				"				\"message\": \"The author and publisher disclaim any warranties (express or implied), merchantability, or fitness for any particular purpose. The author and publisher shall in no event be held liable to any party for any direct, indirect, punitive, special, incidental or other consequential damages arising directly or indirectly from any use of this material, which is provided “as is”, and without warranties.\",\n" + 
				"				\"shipDate\": \"2018-08-20T07:00:00Z\",\n" + 
				"				\"subsidiary\": {\n" + 
				"					\"internalId\": \"4\",\n" + 
				"					\"name\": \"test sub\"\n" + 
				"				},\n" + 
				"				\"shippingTaxCode\": {\n" + 
				"					\"internalId\": \"-7\",\n" + 
				"					\"name\": \"-Not Taxable-\"\n" + 
				"				},\n" + 
				"				\"altShippingCost\": 0,\n" + 
				"				\"createdDate\": \"2018-08-20T15:29:06Z\",\n" + 
				"				\"shipMethod\": {\n" + 
				"					\"internalId\": \"2\",\n" + 
				"					\"name\": \"Pick-up at store\"\n" + 
				"				},\n" + 
				"				\"toBeEmailed\": false,\n" + 
				"				\"estGrossProfit\": 2000,\n" + 
				"				\"postingPeriod\": {\n" + 
				"					\"internalId\": \"156\",\n" + 
				"					\"name\": \"Jan 2010\"\n" + 
				"				},\n" + 
				"				\"tranDate\": \"2018-08-20T07:00:00Z\",\n" + 
				"				\"shipIsResidential\": false,\n" + 
				"				\"entity\": {\n" + 
				"					\"internalId\": \"1948\",\n" + 
				"					\"name\": \"Acme\"\n" + 
				"				},\n" + 
				"				\"status\": \"Open\"\n" + 
				"			}],\n" + 
				"			\"pollDate\": 1534778974827,\n" + 
				"			\"objectType\": \"invoices\"\n" + 
				"		},\n" + 
				"		\"userId\": 2749,\n" + 
				"		\"events\": [{\n" + 
				"			\"date\": \"2018-08-20T15:29:06Z\",\n" + 
				"			\"elementKey\": \"netsuiteerpv2\",\n" + 
				"			\"pollDate\": \"2018-08-20T15:29:34Z\",\n" + 
				"			\"eventType\": \"CREATED\",\n" + 
				"			\"hubKey\": \"erp\",\n" + 
				"			\"objectId\": \"14682\",\n" + 
				"			\"objectType\": \"invoices\"\n" + 
				"		}]\n" + 
				"	},\n" + 
				"	\"user\": \"notifications@cloud-elements.com\"\n" + 
				"}";
	}
	
	
	

	@Test
	public void parseObjectIdFromEvent() throws ParseException, JsonParseException, JsonMappingException, IOException {
		Object obj 			= new JSONParser().parse(getEventsPayload());
		JSONObject jsonObj 	= (JSONObject) obj;
		jsonObj 			= (JSONObject) jsonObj.get("message");
		
		JSONArray eventsArr = (JSONArray) jsonObj.get("events");
		String objectId = String.valueOf(((JSONObject) eventsArr.get(0)).get("objectId"));
		
		String invoiceId = objectId.substring(0, objectId.indexOf("|"));
		
		assertEquals("2", invoiceId);
	}
	
	
	public String getEventsPayload() {
		return "{\n" + 
				"	\"severity\": \"medium\",\n" + 
				"	\"createdDate\": \"Fri Feb 21 16:43:41 UTC 2020\",\n" + 
				"	\"topic\": \"instance-8948659-quickbooks-events\",\n" + 
				"	\"action\": \"create\",\n" + 
				"	\"id\": \"69182068\",\n" + 
				"	\"message\": {\n" + 
				"		\"elementKey\": \"quickbooks\",\n" + 
				"		\"accountId\": 643,\n" + 
				"		\"eventId\": \"AXBooiL_qrZEur0mJV08\",\n" + 
				"		\"companyId\": 280,\n" + 
				"		\"instanceId\": 8948659,\n" + 
				"		\"instance_id\": 8948659,\n" + 
				"		\"instanceName\": \"Demoday\",\n" + 
				"		\"instanceTags\": [\"Demoday\"],\n" + 
				"		\"raw\": {\n" + 
				"			\"invoices\": [{\n" + 
				"				\"balance\": 10000,\n" + 
				"				\"id\": \"2|0\",\n" + 
				"				\"allowIPNPayment\": false,\n" + 
				"				\"objectId\": \"2|0\",\n" + 
				"				\"systemId\": \"2\",\n" + 
				"				\"printStatus\": \"NOT_SET\",\n" + 
				"				\"eventType\": \"CREATED\",\n" + 
				"				\"salesTermRef\": {\n" + 
				"					\"value\": \"3\"\n" + 
				"				}\n" + 
				"			}]\n" + 
				"		},\n" + 
				"		\"userId\": 2374,\n" + 
				"		\"events\": [{\n" + 
				"			\"date\": \"2020-02-21T16:43:41Z\",\n" + 
				"			\"elementKey\": \"quickbooks\",\n" + 
				"			\"pollDate\": \"2020-02-21T16:43:39Z\",\n" + 
				"			\"eventType\": \"CREATED\",\n" + 
				"			\"hubKey\": \"finance\",\n" + 
				"			\"objectId\": \"2|0\",\n" + 
				"			\"objectType\": \"invoices\"\n" + 
				"		}]\n" + 
				"	},\n" + 
				"	\"user\": \"notifications@cloud-elements.com\"\n" + 
				"}";
	}
}
