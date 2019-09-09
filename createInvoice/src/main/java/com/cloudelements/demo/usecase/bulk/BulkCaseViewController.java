package com.cloudelements.demo.usecase.bulk;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudelements.demo.model.BulkStatus;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;

/*
 * This is the main ViewController for demoing the BULK use case
 * This is NOT the view controller for the 'create invoice view controller'
 */
@Controller
public class BulkCaseViewController {

	@Autowired
	private EnvironmentService envService;
	
	@Autowired
	private BulkController bulkController;
	
	@RequestMapping("/bulkCase")
	public String init(Map<String, Object> model, HttpServletRequest request) throws ClientProtocolException, IOException {
		if (!envService.cePropertiesAvailable()) {
			model.put("error", "Please set your Cloud Elements environment variables before you continue. Go to <a href='/environment'>the environment page</a> to do so.");
		}
		
		request.getSession().putValue( "PAGETITLE", "Bulk use case (BI / Analytics)" );
		
		String[] elementKeys = new String[] { "sfdc", "jira", "netsuiteerpv2" };
		
		model.put("elementKeys", elementKeys);
		
		String netsuiteToken = "fmZ42Q1kvpfp7eAXV0ozRIonJIA2jHXlaWEoHcDAVNY=";
		request.getSession().setAttribute("SELECTED_TOKEN", netsuiteToken);
		
		
		return "bulk/bulkAuth";
	}
	
	/*
	 * Do a /objects call to CE and provide the response. It's a JSON String array
	 */
	@RequestMapping ("/bulkObjects")
	public String getObjects (Map<String, Object> model, HttpServletRequest request) throws ParseException {
		String[] objArr = new String[] { "Opportunities", "Lead", "Account", "Contact" };
		
		JSONArray obj = HTTPUtil.doGetArray(request.getSession().getAttribute("SELECTED_TOKEN").toString(), "/elements/api-v2/objects");
		
		
		
		model.put("objectList", Arrays.asList(obj.toArray()) );
		return "bulk/bulkAuth";
	}
	
	/*
	 * Currently only supports a single bulk object to be selected
	 * Method currently not taken into account for scheduling the bulk
	 * 
	 * Potential to expand: create a VDR for the opportunity with a series of fields.  Any of these fields we can then show in the UI as 
	 * automatically flagged. All other fields returned by the /objects/{object}/metadata call can be flagged in addition
	 * 
	 * !! Currently not used for the /bulkCase !!
	 * 
	 */
	@RequestMapping ("/bulkMetadata/{objectName}")
	public String getMetadata (Map<String, Object> model, HttpServletRequest request, @PathVariable String objectName) {
		String[] metaArr = new String [] {"First name", "Last name", "Address 1", "Address 2", "City", "Zip", "Country", "ID"};
		
		model.put("metaList", Arrays.asList(metaArr) );
		model.put("selectedObject", objectName);
		
		return "bulk/bulkMetadata";
	}
	
	/*
	 * Do a POST /bulk/select * from {objectName}
	 */
	@RequestMapping ("/scheduleBulk/{selectedResource}")
	public String scheduleBulk (Map<String, Object> model, HttpServletRequest request, HttpServletResponse response, @PathVariable String selectedResource) throws ClientProtocolException, IOException, ParseException {
		
		String bulkId = bulkController.doBulk(request, response, selectedResource);
		
		
		BulkStatus status = new BulkStatus();
		status = bulkController.fetchStatus(request, response, bulkId);
		
		status.infoMessage = "Bulk download scheduled with ID " + status.getId();
		
		//model.put("selectedMetadata", metadata);
		model.put("selectedObject", selectedResource);
		model.put("bulkStatus", status);
		return "bulk/bulkAuth";
	}
	
	
}
