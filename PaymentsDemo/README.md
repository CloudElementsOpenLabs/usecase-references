# Video 
[Download a walkthrough video for the contents of this repo](src/main/resources/demobank_walkthrough.mp4) 


# Getting started

Connect to Github and clone the repo to your local machine. Open your favorite IDE and import the project.  This is a springboot application so you should be able to just build the project, get required libraries imported and spin up on your IDE’s webserver.

Upon initial launch the app does not know of your Cloud elements account yet.  To complete this configuration do any of the following
* Open application.properties and complete the following parameters (cloudelements.url, cloudelements.organization, cloudelements.user)
* Use the UI to configure the above by using  http://localhost:8080/environment 


You can go through the application’s UI by going to http://localhost:8080 or (/createInvoice)

# Application goal
This app is designed to showcase scalability using the Cloud Elements platform.  You can connect to any relevant application by just adding the element identification key to the array on the CreateInvoice controller init method. The app displays all elements from that app array onto the “Authentication step”.  Everything else onwards from there is handled using the same code and Cloud Element’s back-end APIs.  Beyond this one code change, you scale up by going into our platform and mapping the VDRs to the new application.  javaInvoice, javaProduct, javaVendor, javaCurrency 

The end result is 3 steps. 
* Authenticate to any element from the array
* Download Product, Vendor and Currency information from the 3rd party app in bulk calls
* Create an invoice using the data that was just fetched

# Application design
The app itself does not really have any business logic apart from combining UI data and pushing into a Virtual Data Resource. 

### CreateInvoiceViewController
	This is the main ViewController adding and processing the necessary data in order to show the 3 steps in the UI.  The init method is the one that defines the array to which elements the app can authenticate to.  This should naturally match to the VDR app mappings setup within Cloud elements - unless you are just using the app to showcase authentication scalability of course.

### ElementController
	Once you click any of the listed elements in the UI /elementDetail/{key} will be triggered which actually performs an outbound call to Cloud element’s platform API call /elelements/{key}. the elementDetail.html page handles which parts of the configuration should be shown on screen.

### EnvironmentViewController
Not core to the application but provides a user interface to set your Cloud elements configuration parameters into the application.properties file.

### AuthenticationController
	This controller is used to handle differences between the Basic & oAuth flow plus exposes the /handleOAuthCallback redirect message.  This last method is used to set as callback URL on oauth2 applications.  (As example on quickbooks you will need to add this as trusted URL https://your_ngrok:8080/handleOAuthCallback)


### BulkController
	This class is designed to represent the Cloud Elements Bulk download functionality.  It has following  methods 
* Initiate a bulk download call on whichever object and get a bulk ID in return
* Fetch the status of any given bulk download using it’s bulk ID
* Download the JSON result of the Bulk download and parse each returned line into JSONObjects

### APIResponseController
	Added this class to generically handle and parse any incoming “event” to a standardized JSON Object to work from. Will also print to system output that an event was received. You can plug in specific actions from this apiResponse method.

###Model classes
Notice that the model classes are a representation of the VDRs you find within Cloud Elements. Product, Invoice, Vendor, Currency.  These are (de)serialized when calling the VDRs back & forth and take complexity away of having to do manual work. All of a sudden both front-end and back-end know what a ‘vendor’ is.


### The UI
The front-end is designed using Thymeleaf with jquery and bootstrap components. Find following HTML files representing the flow you are seeing
* createInvoice_1.html (init method in CreateInvoiceViewController)
* createInvoice_2.html (BulkDownload method in CreateInvoiceViewController)
* createInvoice_3.html (Displays step 3 of the UI and is the loadInvoiceView method in CreateInvoiceViewController)
* environment.html (Handles the environment setup with EnvironmentViewController)
* footer.html & header.html are defining js libraries and layout components

### Utility classes
* AuthenticationUtil - Simple utility class that puts the json together to what Cloud Elements is expecting to create an instance, disregarding authentication type
* HTTPUtil - This class has a set of similar methods but is easy to use as it defaults the GET, POST, DELETE operations to the CE Platform, including correct authentication etc. Returns properly parsed JSON Objects to work with in the controller/service classes.

### JUNIT Classes
There ’s a few JUNIT classes that test off methods like the bulk framework etc.  You can use these to continue to work from in case you want to test/add additional functionality

# Things to do
* oAuth authentication does only work for step 1 - generically. Step 2: handling the callback url is in place but how do we know which properties CE is expecting upon the instanceCreation call. Look into the example of Quickbooks and Salesforce 
* Setup VDRs to all ERP elements we have
* Perhaps we want to create a dedicated CE account to this holding all configuration. Which means that you can spin up the app with default credentials in there and you don’t have to do anything. It just works!
* Exception handling on the actual invoice creation is not ok yet
* The authentication part of the UI is using downloaded images. Our APIs are not properly exposing where the logos are located. If it would, we could load the elements from the /elements API call and display the logos as exposed there
* After authentication I'm currently adding the instancetoken as request param. Not the best secure example and should refactor to store directly onto session object


