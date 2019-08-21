package com.cloudelements.demo.usecase.vdr;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;

/*
 * This is the main ViewController for the app
 */
@Controller
public class VDRViewController {

	@Autowired
	private EnvironmentService envService;
	
	@RequestMapping("/vdrScreen")
	public String init(Map<String, Object> model, HttpServletRequest request) throws ClientProtocolException, IOException {
		if (!envService.cePropertiesAvailable()) {
			model.put("error", "Please set your Cloud Elements environment variables before you continue. Go to <a href='/environment'>the environment page</a> to do so.");
		}
		request.getSession().setAttribute("PAGETITLE", "Example model mapping");
		return "vdr/vdrScreen";
	}
	
	/*
	 * Call /organizations/objects/definitions to list all VDRs. Note we only care about the names at this stage
	 */
	@RequestMapping ("/listVDRs")
	public String listVDRs (Map<String, Object> model, HttpServletRequest request) {
		return "vdr/vdrScreen";
	}
	
	/*
	 * https://api.cloud-elements.co.uk/elements/api-v2/instances?hydrate=false
	 * Returns a list of all instances in my account. We are after the instances with "transformationData" not null
	 * For each of them we can list their vdrs.  Once user clicks the vdr we can get the data
	 */
	@RequestMapping ("/listElements")
	public String listAllElements (Map<String, Object> model, HttpServletRequest request) {
		return "vdrScreen";
	}
	
	@RequestMapping ("/listInstances")
	public String listAllInstances (Map<String, Object> model, HttpServletRequest request) throws ParseException {
		JSONArray instanceArr = HTTPUtil.doGetArrayJSONL(null, "/elements/api-v2/instances");
		
		model.put("instanceList", instanceArr);
		return "vdr/instances";
	}
	
	
}
