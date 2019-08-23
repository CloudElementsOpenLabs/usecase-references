package com.cloudelements.demo.usecase.createinvoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudelements.demo.model.ConfigurationField;
import com.cloudelements.demo.model.Element;
import com.cloudelements.demo.model.ElementConfiguration;
import com.cloudelements.demo.model.ElementConfigurationOption;
import com.cloudelements.demo.model.InvoiceTwo;
import com.cloudelements.demo.model.Product;
import com.cloudelements.demo.usecase.authentication.AuthenticationController;
import com.cloudelements.demo.usecase.bulk.BulkController;
import com.cloudelements.demo.usecase.element.ElementService;
import com.cloudelements.demo.usecase.environment.EnvironmentService;
import com.cloudelements.demo.util.AuthenticationUtil;
import com.cloudelements.demo.util.HTTPUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This is the main ViewController for the app
 */
@Controller
public class CreateInvoiceViewController {

	@Autowired
	private EnvironmentService envService;
	
	@Autowired
	private ElementService elService;
	
	@Autowired
	private AuthenticationController authController;
	
	/*
	 * Returns the first page of the invoice creation process - the authentication page
	 * Notice it adds the elementKeys array onto the model, that's what is used in the UI to display the logos and logon details
	 * 
	 * Add any other element key to this array and refresh the page
	 * 
	 * The method also handles redirects once a CE instance has created and adds it onto the session so the UI can show
	 */
	@RequestMapping("/createInvoice")
	public String init(Map<String, Object> model, HttpServletRequest request) throws ClientProtocolException, IOException {
		if (!envService.cePropertiesAvailable()) {
			model.put("error", "Please set your Cloud Elements environment variables before you continue. Go to <a href='/environment'>the environment page</a> to do so.");
		}
		
		request.getSession().setAttribute("PAGETITLE", "Invoice creation use case");
		
		/* 
		 * Add any element key from the catalog to this array to make it appear on the UI 
		 * */
		String[] elementKeys = { "netsuiteerpv2", "freshbooksv2", "quickbooks", "intacct", "sfdc" };
		model.put("elementKeys", elementKeys);
		
		model.put("configurationField", new ConfigurationField());
		
		
		/* 
		 * This should be the real code instead of putting in the netsuiteToken by default
		 */
		if (request.getParameter("token") != null) { // CE Instance token received // element instance successfully created
			request.getSession().setAttribute("SELECTED_TOKEN", request.getParameter("token").toString());
		}
		
		return "createinvoice/createInvoice_1";
	}
	
	/*
	 * Handles page 2 of the app - Bulk download
	 */
	@RequestMapping("/createInvoice2")
	public String bulkDownload (Map<String, Object> model, HttpServletRequest request) {
		return "createinvoice/createInvoice_2";
	}
	
	/* 
	 * Handles page 3 of the app - Invoice creation
	 */
	@RequestMapping("/createInvoice3")
	public String loadInvoiceView (Map<String, Object> model, HttpServletRequest request) {
		return "createinvoice/createInvoice_3";
	}
	
	/*
	 * Handles the submission of the invoice creation (Last step)
	 * Gathers all the input data, constructs a json Object and send it to a VDR
	 */
	@RequestMapping("/createInvoice4/{customerId}")
	public String postInvoice (Map<String, Object> model, HttpServletRequest request, HttpServletResponse response, 
			@PathVariable String customerId, 
			@RequestParam String[] itemId, @RequestParam String[] itemQuantity, @RequestParam String[] itemRate,
			@RequestParam String currencySelection,
			@RequestParam String vatPercentage) throws IOException {
		ArrayList<Product> prodList = new ArrayList<Product>();
		
		double totalAmount = 0; // without tax
		for(int i = 0; i < itemId.length; i++) {
			Product prod = new Product(itemId[i], itemQuantity[i], itemRate[i]); 
			prod.setLocation("1");
			prodList.add( prod );
			
			totalAmount += Integer.valueOf(prod.getQuantity()) * Double.parseDouble(prod.getRate());
		}
		
		double total = totalAmount * Double.parseDouble( vatPercentage ) / 100;
		

		InvoiceTwo myInvoice = new InvoiceTwo ();
		
		myInvoice.setAmount( String.valueOf(totalAmount) );
		myInvoice.setCurrency("1");
		myInvoice.setEntity(customerId);
		myInvoice.setDescription("random product");
		myInvoice.setTotalValue(String.valueOf(total));
		myInvoice.setDueDate("2019-01-01T07:00:00Z");
		myInvoice.setTransactionDate("2019-01-01T07:00:00Z");
		
		myInvoice.setItem(prodList);
		myInvoice.setMessage ("Java invoice creation");
		myInvoice.setSubsidiary ("1");
		
		
		ObjectMapper mapper = new ObjectMapper ();
		System.out.println( mapper.writeValueAsString( myInvoice ) );

		
		JSONObject returnObj = HTTPUtil.doPost(BulkController.getToken(request), "/elements/api-v2/javaInvoice2", mapper.writeValueAsString(myInvoice) );
		if (returnObj != null) {
			model.put("invoiceId", (String) returnObj.get("id"));
		} else {
			// Invoice creation failed. Show returned error
			model.put("error", "Error creating invoice - " + returnObj.get("message") );
		}
		
		return "createinvoice/createInvoice_3";
	}
	
	/*
	 * This method captures all authentication fields and passes it on to the AuthenticationController
	 * TODO: this method might need to move to authenticationController in general
	 * 
	 * It does not return anything but the authcontroller provides a returnURL back to which we will redirect the user (holding the token)
	 */
	@RequestMapping(method=RequestMethod.POST, value="/doAuthentication")
	public void doAuthentication (Map<String, Object> model, HttpServletRequest request, HttpServletResponse response, @RequestParam String elementId) throws ClientProtocolException, IOException, ParseException  {
		Element el = elService.getSerializedElement(elementId);

		boolean isOAuth = false;
		
		ElementConfiguration elConfig = el.getConfig("authentication.type");
		if ( elConfig != null ) {
			for (ElementConfigurationOption elOption : elConfig.getOptions()) {
				if ( "oauth2".equalsIgnoreCase(elOption.getValue()) ) {
					isOAuth = true;
					break;
				}
			}
		} else { // Assume that if authentication.type is not set, the element is always oAuth
			isOAuth = true;
		}
		
		HashMap<String, String> configMap = new HashMap<String, String>();
		for ( String key : request.getParameterMap().keySet() ) {
			configMap.put ( key, String.valueOf(request.getParameter(key)) );
		}

		String redirectURL = "";
		if ( !isOAuth ) { // Handle BASIC authentication
			redirectURL = authController.handleBasicAuthentication(el, configMap);
		} else { // Handle OAUTH2 flow // redirect user 
			redirectURL = authController.handleOAuth2Authentication(el.getKey(), request.getParameter("oauth.api.key"), request.getParameter("oauth.api.secret"), request.getParameter("oauth.callback.url"));
		}
		
		response.sendRedirect(redirectURL);
	}
	
}
