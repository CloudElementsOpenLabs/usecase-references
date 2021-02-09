# Video 
[Download a walkthrough video for the contents of this repo](src/main/resources/demobank_walkthrough.mp4) 
*TO BE UPDATED*

# Application goal
This app is designed to showcase scalability using the Cloud Elements platform.  You can connect to any relevant application by just adding the element identification key to the array on the CreateInvoice controller init method. The app displays all elements from that app array onto the “Authentication step”.  Everything else onwards from there is handled using the same code and Cloud Element’s back-end APIs.  Beyond this one code change, you scale up by going into our platform and mapping the VDRs to the new application.  javaInvoice, javaProduct, javaVendor, javaCurrency 

The end result is 3 steps. 
* Authenticate to any element from the array
* Download Product, Vendor and Currency information from the 3rd party app in bulk calls
* Create an invoice using the data that was just fetched


# Getting started

This is a springboot web application leveraging Thymeleaf for the UI component. Clone the repo into your IDE, build and import the required libraries, then run as Java project.


### Springboot app specific configuration
When you launch the springboot app, it has no knowledge of your Cloud Elements account just yet.  You can set these properties by going to http://localhost:8080/environment.  Upon saving the form, the *EnvironmentViewController* will be updating the *application.properties* file. In order for this to take effect, make sure to stop and restart the app in your IDE.  Upon restart the "set your environment" message is now no longer shown and you can go ahead using the app.

*add screenshot of: environment page + explanation what each of the inputs are*


### Element specific details
Upon writing the app supports connections to Quickbooks, Xero, Netsuite, SAP S4, Dynamics 365 Finance and Operations.  In order to use any of these and actually authenticate to them, you will need credentials. Quickbooks and Xero have free trial programs on their website that you can connect to.

- Free trial for *Quickbooks* at https://quickbooks.intuit.com/pricing/
- Free trial for *Xero* at https://www.xero.com/us/signup/?escape=true


### Cloud-elements specific configuration
The springboot app shows scalability to pull in *vendor* and *invoice* data across multiple 3rd party applications. In order to do so we have created a few Virtual Data Resources (VDRs) that are exposed as APIs on top of the actual elements. We have bundled them as assets for you to import into your Cloud Elements environment.  To make sure they are easy to recognize we prefixed them with "java".  Download them here and then use the *Manage environments* in CE to import the assets.

- javaVendor
- javaInvoice2
- javaPayment

*add screenshots of: The doctor + VDR screen + mapping*





## Authentication
To authenticate to any of these elements the springboot app uses the CE Standardized Authentication & Provisioning service.  You will notice there's two 2 methods in *AuthenticationController*.

- getRedirectURL: Creates an authentication session on CE via a single API call using the elementKey
- remoteAuthListener: Acts as a webhook to capture the CE response upon instance provisioning




### The UI
The front-end is designed using Thymeleaf with jquery and bootstrap components. Find following HTML files representing the flow you are seeing
* createInvoice_1.html (init method in CreateInvoiceViewController)
* createInvoice_2.html (BulkDownload method in CreateInvoiceViewController)
* createInvoice_3.html (Displays step 3 of the UI and is the loadInvoiceView method in CreateInvoiceViewController)
* environment.html (Handles the environment setup with EnvironmentViewController)
* footer.html & header.html are defining js libraries and layout components

*TODO: rewrite*


# Things to do
* Setup VDRs to all ERP elements we have
* Perhaps we want to create a dedicated CE account to this holding all configuration. Which means that you can spin up the app with default credentials in there and you don’t have to do anything. It just works!

