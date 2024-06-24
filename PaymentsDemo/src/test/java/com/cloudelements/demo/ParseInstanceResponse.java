package com.cloudelements.demo;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@Service
@SpringBootTest
public class ParseInstanceResponse {
	
	
	@Test
	public void testGetInstanceToken() {
		try {
			JSONParser parse = new JSONParser();
			JSONObject obj;
			obj = (JSONObject) parse.parse(successResponse);
			String token = ((JSONObject) obj.get("elementInstance")).get("token").toString();
			
			assertEquals("mFUAYbw/v2iuN6C5EBmPIbjLXQ9n1nkM9JxhYdM0WzQ=", token);
			
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		
	}
	
	
	private String successResponse = "{\n"
			+ "	\"status\": \"success\",\n"
			+ "	\"elementInstance\": {\n"
			+ "		\"id\": 835844,\n"
			+ "		\"name\": \"ezraNewTunnel\",\n"
			+ "		\"token\": \"mFUAYbw/v2iuN6C5EBmPIbjLXQ9n1nkM9JxhYdM0WzQ=\", \n"
			+ "		\"valid\": true,\n"
			+ "		\"disabled\": false,\n"
			+ "		\"maxCacheSize\": 0,\n"
			+ "		\"cacheTimeToLive\": 0,\n"
			+ "		\"configuration\": {\n"
			+ "			\"allow.select.fields\": \"true\",\n"
			+ "			\"authentication.type\": \"basic\",\n"
			+ "			\"bulk.attribute.created_time\": \"dateCreated\",\n"
			+ "			\"bulk.attribute.modified_time\": \"lastModifiedDate\",\n"
			+ "			\"bulk.query.date_mask\": \"yyyy-MM-dd'T'HH:mm:ss.SXXX\",\n"
			+ "			\"bulk.query.field_name\": \"lastModifiedDate\",\n"
			+ "			\"bulk.query.operator\": \"\\u003e=\",\n"
			+ "			\"consumer_key\": \"a6afdd743dbf2cfd3e57163fba29065967fa9cef447f13092dfbeafe935f09a1\",\n"
			+ "			\"consumer_secret\": \"30283cb67216556dee9675eba5c50898de2e2fc4a24a367127152286128cd358\",\n"
			+ "			\"default.select.fields.map\": \"\",\n"
			+ "			\"event.notification.basic.password\": \"********\",\n"
			+ "			\"event.notification.basic.username\": \"\",\n"
			+ "			\"event.notification.callback.headers\": \"\",\n"
			+ "			\"event.notification.callback.url\": \"\",\n"
			+ "			\"event.notification.enabled\": \"false\",\n"
			+ "			\"event.notification.signature.key\": \"\",\n"
			+ "			\"event.notification.subscription.id\": \"\",\n"
			+ "			\"event.poller.configuration\": \"{\\n  \\\"customers\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/customers?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"dateCreated\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"employees\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/employees?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"dateCreated\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"estimates\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/estimates?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"invoices\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/invoices?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"journal-entries\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/journal-entries?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"payments\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/payments?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"products\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/products?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"purchase-orders\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/purchase-orders?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"time-activities\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/time-activities?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"vendor-payments\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/vendor-payments?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"vendors\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/vendors?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"dateCreated\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"accounts\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/accounts?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"dateCreated\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"contacts\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/contacts?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"dateCreated\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"leads\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/leads?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"dateCreated\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"activities\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/activities?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"opportunities\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/opportunities?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"bills\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/bills?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"discount-items\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/discount-items?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"cases\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/cases?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"createdDate\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"projects\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/projects?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"dateCreated\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  },\\n  \\\"prospects\\\": {\\n    \\\"url\\\": \\\"/hubs/erp/prospects?where=lastModifiedDate\\u003e'${date:yyyy-MM-dd'T'HH:mm:ssXXX}'\\\",\\n    \\\"idField\\\": \\\"internalId\\\",\\n    \\\"filterByUpdatedDate\\\": true,\\n    \\\"datesConfiguration\\\": {\\n      \\\"updatedDateField\\\": \\\"lastModifiedDate\\\",\\n      \\\"updatedDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\",\\n      \\\"createdDateField\\\": \\\"dateCreated\\\",\\n      \\\"createdDateFormat\\\": \\\"yyyy-MM-dd'T'HH:mm:ssXXX\\\"\\n    }\\n  }\\n}\",\n"
			+ "			\"event.poller.refresh_interval\": \"15\",\n"
			+ "			\"event.vendor.type\": \"polling\",\n"
			+ "			\"filter.response.nulls\": \"true\",\n"
			+ "			\"instance.variables\": \"\",\n"
			+ "			\"netsuite.accountId\": \"TSTDRV1776438\",\n"
			+ "			\"netsuite.appId\": \"83FD701E-EC85-437F-AFD2-9AB3C972779D\",\n"
			+ "			\"netsuite.preferences.disable_customfield_validation\": \"false\",\n"
			+ "			\"netsuite.sandbox\": \"false\",\n"
			+ "			\"netsuite.single.session\": \"false\",\n"
			+ "			\"netsuite.single.session.key\": \"\",\n"
			+ "			\"netsuite.sso.roleId\": \"3\",\n"
			+ "			\"restlet.url\": \"\",\n"
			+ "			\"token_id\": \"782a8b89fbb8440d70347db61d323e5720ccd83f3f1ba1601c7d6ca8196432b0\",\n"
			+ "			\"token_secret\": \"e50a850f739b0884a1f87a06b43940966f5cec301d2ec0100bc78636d0f648aa\",\n"
			+ "			\"use_role_id\": \"true\",\n"
			+ "			\"user.password\": \"********\",\n"
			+ "			\"user.username\": \"developerpoc@cloud-elements.com\"\n"
			+ "		},\n"
			+ "		\"eventsEnabled\": false,\n"
			+ "		\"traceLoggingEnabled\": false,\n"
			+ "		\"externalAuthentication\": \"none\",\n"
			+ "		\"cachingEnabled\": false,\n"
			+ "		\"element\": {\n"
			+ "			\"id\": 1712,\n"
			+ "			\"name\": \"Netsuite ERP\",\n"
			+ "			\"key\": \"netsuiteerpv2\",\n"
			+ "			\"description\": \"Add a Netsuite 2019 Release 1 Instance to connect your existing Netsuite account to the ERP Hub, allowing you to manage all of your ERP activities across multiple ERP Elements. You will need your Netsuite account information to add an instance.\",\n"
			+ "			\"image\": \"elements/provider_netsuite.png\",\n"
			+ "			\"logo\": \"netsuitecrm\",\n"
			+ "			\"active\": true,\n"
			+ "			\"deleted\": false\n"
			+ "		}\n"
			+ "	}\n"
			+ "}";


}
