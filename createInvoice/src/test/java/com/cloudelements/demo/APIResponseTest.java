package com.cloudelements.demo;

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
		String payload = "{\"severity\":\"medium\",\"createdDate\":\"Mon Aug 20 15:30:07 UTC 2018\",\"topic\":\"instance-7950647-netsuiteerpv2-events\",\"action\":\"create\",\"id\":\"1496771\",\"message\":{\"elementKey\":\"netsuiteerpv2\",\"accountId\":874,\"eventId\":\"AWVX9OTIhdQRdI1F7Q7v\",\"companyId\":291,\"instanceId\":7950647,\"instance_id\":7950647,\"instanceName\":\"Local netsuite\",\"instanceTags\":[\"Local netsuite\"],\"raw\":{\"invoices\":[{\"tranId\":\"INV10000925\",\"estGrossProfitPercent\":100,\"salesRep\":{\"internalId\":\"-5\",\"name\":\"Alex Wolfe\"},\"dueDate\":\"2018-07-16T07:00:00Z\",\"taxTotal\":0,\"source\":\"Web Services\",\"subTotal\":2000,\"internalId\":\"14682\",\"total\":2000,\"currencyName\":\"USA\",\"exchangeRate\":1,\"terms\":{\"internalId\":\"2\",\"name\":\"Net 30\"},\"currency\":{\"internalId\":\"1\",\"name\":\"USA\"},\"timeDiscPrint\":false,\"customFieldList\":{\"customField\":[{\"internalId\":\"4563\",\"scriptId\":\"custbody_cust_priority\",\"value\":50},{\"internalId\":\"179\",\"scriptId\":\"custbody_fmt_finance_app\",\"value\":false},{\"internalId\":\"181\",\"scriptId\":\"custbody_fmt_finance_declined\",\"value\":false},{\"internalId\":\"180\",\"scriptId\":\"custbody_fmt_req_financial_app\",\"value\":false},{\"internalId\":\"174\",\"scriptId\":\"custbody_fmt_req_sales_manager_app\",\"value\":false},{\"internalId\":\"169\",\"scriptId\":\"custbody_fmt_sales_manager_app\",\"value\":false},{\"internalId\":\"171\",\"scriptId\":\"custbody_fmt_sales_manager_declined\",\"value\":false},{\"internalId\":\"167\",\"scriptId\":\"custbody_fmt_senior_exec_declined\",\"value\":false},{\"internalId\":\"168\",\"scriptId\":\"custbody_fmt_senior_executive_app\",\"value\":false},{\"internalId\":\"4\",\"scriptId\":\"custbody_promisedate\",\"value\":\"2018-08-24T07:00:00Z\"},{\"internalId\":\"197\",\"scriptId\":\"custbody_promisedate_2\",\"value\":\"2018-08-24T07:00:00Z\"}]},\"toBeFaxed\":false,\"totalCostEstimate\":0,\"itemCostDiscPrint\":false,\"shippingCost\":0,\"toBePrinted\":false,\"lastModifiedDate\":\"2018-08-20T15:29:06Z\",\"expCostDiscPrint\":false,\"message\":\"The author and publisher disclaim any warranties (express or implied), merchantability, or fitness for any particular purpose. The author and publisher shall in no event be held liable to any party for any direct, indirect, punitive, special, incidental or other consequential damages arising directly or indirectly from any use of this material, which is provided \u201cas is\u201d, and without warranties.\",\"shipDate\":\"2018-08-20T07:00:00Z\",\"subsidiary\":{\"internalId\":\"4\",\"name\":\"test sub\"},\"shippingTaxCode\":{\"internalId\":\"-7\",\"name\":\"-Not Taxable-\"},\"altShippingCost\":0,\"createdDate\":\"2018-08-20T15:29:06Z\",\"shipMethod\":{\"internalId\":\"2\",\"name\":\"Pick-up at store\"},\"toBeEmailed\":false,\"estGrossProfit\":2000,\"postingPeriod\":{\"internalId\":\"156\",\"name\":\"Jan 2010\"},\"tranDate\":\"2018-08-20T07:00:00Z\",\"shipIsResidential\":false,\"entity\":{\"internalId\":\"1948\",\"name\":\"Acme\"},\"status\":\"Open\"}],\"pollDate\":1534778974827,\"objectType\":\"invoices\"},\"userId\":2749,\"events\":[{\"date\":\"2018-08-20T15:29:06Z\",\"elementKey\":\"netsuiteerpv2\",\"pollDate\":\"2018-08-20T15:29:34Z\",\"eventType\":\"CREATED\",\"hubKey\":\"erp\",\"objectId\":\"14682\",\"objectType\":\"invoices\"}]},\"user\":\"notifications@cloud-elements.com\"}";
	
		Object obj = new JSONParser().parse(payload);
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
}
