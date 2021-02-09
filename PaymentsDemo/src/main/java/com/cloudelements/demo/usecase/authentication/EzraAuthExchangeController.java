package com.cloudelements.demo.usecase.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.CustomSessionTokenService;
import com.cloudelements.demo.util.HTTPUtil;

@RestController
public class EzraAuthExchangeController {

	@Autowired
	private EnvironmentService envService;
	
	@Autowired
	private CustomSessionTokenService sessionService;
	
	/*
	 * After successful Ezra application setup within CE use the sessions API to generate a URL to guide your users to
	 */
	@RequestMapping (value = "/getRedirectURL/{elementKey}", method = RequestMethod.GET)
	public JSONObject getRedirectURL (HttpServletResponse response, @PathVariable String elementKey) throws ParseException, IOException {
		sessionService.setElement(elementKey);
		String sessionURL = "/v1alpha1/elements/normalized-instances/applications/" + envService.getEzraApplicationId() + "/sessions?elementKey=" + elementKey;
		
		JSONObject obj = HTTPUtil.doPost(null, sessionURL, null);
		
		System.out.println("JSON OBJ: " + obj);
		return obj;
	}
	
	
	/*
	 * Listens to incoming responses from user flow moving through Ezra.  
	 * The end result will be the CE instance token ID
	 * 
	 * In the Ezra application setup, please setup your response URL + "/authListener"
	 * When setting up OAuth apps for 3rd party applications such as QBO, Xero, ... use that exact same URL incl the /authListener
 	 * 
	 */
	@RequestMapping (value = "/authListener")
	public void remoteAuthListener (HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestBody String payload) throws ParseException, IOException {
		System.out.println(payload);
		/*
		 * Retrieve token & store in database
		 */
		JSONParser parse = new JSONParser();
		JSONObject obj = (JSONObject) parse.parse(payload);
		
		String token = ((JSONObject) obj.get("elementInstance")).get("token").toString();
		
		System.out.println("Token received " + token);
		
		sessionService.setToken(token);
	}
	
	@RequestMapping (value = "/refreshNotification")
	public JSONObject getLastNotification (HttpServletRequest request, HttpServletResponse response, ModelMap model) throws ParseException {
		/*
		curl -H "Authorization: Bearer d666462aad5aaac1cd91b87b3c64a910" \
		  "https://api.pipedream.com/v1/sources/dc_4Ou4WEV/event_summaries?expand=event"
		*/
		JSONObject obj = doGet ("https://api.pipedream.com/v1/sources/dc_4Ou4WEV/event_summaries?expand=event");
		JSONArray arr = (JSONArray) obj.get("data");
		
		return (JSONObject) arr.get(0);
	}
	
	private JSONObject doGet (String URLStr) throws ParseException {
		HttpGet getter = new HttpGet(URLStr);
		
		getter.addHeader("Authorization", "Bearer d666462aad5aaac1cd91b87b3c64a910");
		getter.addHeader("Accept", "application/json"); // This forces bulk to return json instead of csv
		
		try {
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(getter);
			if(response!=null){
            	InputStream in = response.getEntity().getContent() ;
            	JSONParser parser = new JSONParser();
            	Object obj = parser.parse(new InputStreamReader(in));
            	return (JSONObject) obj;
			}
        	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
