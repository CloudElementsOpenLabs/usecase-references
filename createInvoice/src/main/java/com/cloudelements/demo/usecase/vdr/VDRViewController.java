package com.cloudelements.demo.usecase.vdr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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

import com.cloudelements.demo.model.Instance;
import com.cloudelements.demo.model.Resource;
import com.cloudelements.demo.model.Transformation;
import com.cloudelements.demo.model.VDR;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.HTTPUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	 * Clear all session variables upon init
	 */
	private void clearSession (HttpServletRequest request) {
		while (request.getSession().getAttributeNames().hasMoreElements()) {
			String sessionName = request.getSession().getAttributeNames().nextElement();
			request.getSession().removeAttribute(sessionName);
		}
	}
	
	/*
	 * Call /organizations/objects/definitions to list all VDRs. Note we only care about the names at this stage
	 */
	@RequestMapping ("/listVDRs")
	public String listVDRs (Map<String, Object> model, HttpServletRequest request) throws ParseException, JsonParseException, JsonMappingException, IOException {
		clearSession (request);

		request.getSession().setAttribute("PAGETITLE", "VDR Overview");
		
		
		JSONObject obj = HTTPUtil.doGet(null, "/elements/api-v2/organizations/objects/definitions");
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, VDR> vdrMapList = new HashMap<String, VDR>();
		for (Object o :  obj.entrySet()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, JSONObject> e = (Map.Entry<String, JSONObject>) o;
			
			VDR vdrObj = objectMapper.readValue(e.getValue().toJSONString(), VDR.class);
			
			Arrays.sort (vdrObj.getFields()); // sort fields alphabetically
			vdrMapList.put(vdrObj.getObjectName() , vdrObj);
		}
		
		request.getSession().setAttribute("vdrMapList", vdrMapList); // we need this list in order to make sure we even have the VDRs that don't have any mapping
		
		return "vdr/vdrOverview";
	}
	
	@RequestMapping ("/vdrInstances/{vdr}")
	public String getVDRInstances (Map<String, Object> model, HttpServletRequest request, @PathVariable String vdr) throws ParseException {
		// returns all element instances but we just use this to know which elements we have instances for
		JSONArray instancesArr = HTTPUtil.doGetArray (null, "/elements/api-v2/instances");
		
		HashMap<String, Integer> vdrMap = new HashMap<String, Integer>();
		HashMap<String, ArrayList<Instance>> vdrInstanceMap = new HashMap <String, ArrayList<Instance>> ();
		int instanceCounter = 0;
		for (Object arr : instancesArr.toArray()) {
			JSONObject instanceJsonObj = (JSONObject) arr;
			
			JSONArray transformationDataArr = (JSONArray) instanceJsonObj.get("transformationData");
			if (transformationDataArr != null) {
				for (Object transformationObj : transformationDataArr.toArray()) {
					JSONObject transformationJsonObj = (JSONObject) transformationObj;
					String objectName = transformationJsonObj.get("objectName").toString();
					
					if (vdrMap.get(objectName) == null) { // Make sure to add every VDR to the map and initiate by 1
						vdrMap.put(objectName, 1);
					} else { // VDR already created, add 1 to the existing value
						instanceCounter = vdrMap.get(objectName).intValue();
						vdrMap.put(objectName, Integer.valueOf(++instanceCounter));
					}
					
					Instance instance = new Instance ();
					instance.setId(instanceJsonObj.get("id").toString());
					instance.setName(instanceJsonObj.get("name").toString());
					
					JSONObject element = (JSONObject) instanceJsonObj.get("element");
					instance.setElement(element.get("key").toString());
					
					ArrayList<Instance> instanceArr = vdrInstanceMap.get(objectName);
					if (instanceArr == null) {
						instanceArr = new ArrayList<Instance>();
					} 
					instanceArr.add(instance);
					
					Collections.sort(instanceArr);
					
					vdrInstanceMap.put(objectName, instanceArr);
				}
			} 
		}
		request.getSession().setAttribute("vdrMap", vdrMap);
		request.getSession().setAttribute("vdrInstanceMap", vdrInstanceMap);
		request.getSession().setAttribute("selectedVDR", vdr);
		
		return "vdr/vdrOverview";
	}
	
	
	@RequestMapping ("/transformations/{vdr}/{instanceId}")
	public String getInstanceTransformations (Map<String, Object> model, HttpServletRequest request,
			@PathVariable String vdr, 
			@PathVariable String instanceId) throws ParseException, JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		
		// Do a quick instance lookup as we need the token for the object metadata call
		JSONObject instanceJsonObj = HTTPUtil.doGet (null, "/elements/api-v2/instances/" + instanceId);
		String token = instanceJsonObj.get("token").toString();
		
		// returns transformations for a specific ID and Object (= VDR)
		JSONObject transformationJsonObj = HTTPUtil.doGet (null, "/elements/api-v2/instances/" + instanceId + "/transformations/" + vdr);
		Transformation transformationObj = objectMapper.readValue(transformationJsonObj.toJSONString(), Transformation.class);
		
		model.put("transformation", transformationObj);
		
		
		JSONObject metadataJsonObj = HTTPUtil.doGet (token, "/elements/api-v2/objects/" + vdr + "/metadata");
		Resource metadataObj = objectMapper.readValue(metadataJsonObj.toJSONString(), Resource.class);
		model.put("metadata", metadataObj);
		
		return "vdr/vdrOverview";
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
