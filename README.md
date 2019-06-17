# Open Liberty Cloudant Workshop


## What you'll learn
You will learn how to build and deploy two microservices that can send and retrieve data from a no-sql database service on the cloud called Cloudant. This type of database takes documents in the form of JSON and does this via HTTP requests or via the Java API that is provided for us.

You will use the IBM Cloud's graphical user interface to provision an instance of Cloudant, create and name your database and set up your access via an API key. You will then take two microservices that have been built for you and edit them to save data to the cloud rather than to your local storage. The first microservice `System` gets information about your system and the JVM. The second microservice `Inventory` stores that information into a map in Java. You are going to change that so it sends that data to the cloud in the form of JSON. You can do all this by simply using REST calls but as a Cloudant Java API has been provided we will use that and allow the HTTP calls to happen under the covers to make our lives simple.

#### Overview of Cloudant
IBM Cloudant is a fully managed JSON document database that offers independent serverless scaling of provisioned throughput capacity and storage. Cloudant is compatible with Apache CouchDB and accessible through a simple to use HTTPS API for web, mobile, serverless, and IoT applications. Cloudant is SOC2 and ISO 27001 compliant with HIPAA readiness optional for Dedicated Hardware environments. Cloudant Standard plan instances come with a 99.95% SLA. All data is encrypted at rest and over the wire. Cloudant JSON documents are stored in triplicate across three separate availability zones for in-region HA/DR in regions that support AZ's. Any Cloudant instance deployed from the Frankfurt region/location will be in an EU-managed environment. See https://ibm.com/cloud/cloudant or the documentation link below for more details.

## Prerequisites

### Configuring the Cloudant service
Before starting this tutorial, you will need to set up a Cloudant database on your IBM Cloud account.

#### To create an instance of Cloudant:
1. Search for Cloudant in the IBM Cloud catalogue
2. Have the service name set to whatever you like and set the authentication method to Legacy and IAM.
3. Once the service is launched, click on it from your service list and then click the button Launch Cloudant dashboard.
4. Once the dashboard has opened click create database in the top right and give it whatever name you desire.

#### Create an API Key and grant it permissions to use Cloudant
1. Now go back to your other tab with IBM Cloud open and then click on Manage then Access in the top right corner.
2. In the bottom left click on IBM Cloud API Keys then click create a new IBM Cloud API Key.
3. Give it a name and description then once it is created save it to your local machine and copy it to your clipboard.
4. Now go back to your Cloudant dashboard and click on permissions once you have selected your newly created database.
5. Paste in the API key you just created and click grant permission.
6. Now in the panel above grant all permission to your newly added API key using the tick boxes.
7. Finally make a note of the enpdoint that is located under Account settings. Use the preferred external endpoint.

## Step 1 - Getting Started
The fastest way to work through this guide is to clone the Git repository and use the projects that are provided inside:
```
git clone https://github.com/jamiecoleman92/guide-cloudant-openliberty.git
cd guide-cloudant-openliberty
```
The `start` directory contains the starting project that you will build upon.

The `finish` directory contains the finished project that you will build.

#### Try what you'll build

The `finish` directory in the root of this guide contains the finished application. Give it a try before you proceed.

Before you build and run the microservices you need to put in the API key you created earlier and the endpoint that can be found on the Cloudant dashboard when you click on your database. The Java file you need to edit is located here: `/finish/src/main/java/io/openliberty/guides/system/SystemResource.java`

Replace the value of the `apiKey` and `endpoint` fields to be equal to the values you copied from the credentials from the IBM Cloud dashboard.

```
private String apiKey = /*Insert your API key here!*/
private String endpoint = /*Insert your Cloudant URL here!*/
```
Save the file the head to the root `finish` directory and run the following command:
`mvn install liberty:run-server`

Once the server has started you can test out the application at the following URL:

`http://localhost:9080/system/properties`

Now if you have a look in the Cloudant dashboard you will notice a new document has been created in your database that has all your JVM information. You can now delete this then stop the server pressing `Ctrl C` in your terminal.

Now navigate into the start directory to start this guide.

## Step 2 - Acquiring Cloudant Dependencies with Maven

In the start directory you will notice a file named `pom.xml`. This file lists all the dependencies you require for this project. You will now add the maven dependency into the dependency section:

```
<dependency>
  <groupId>com.cloudant</groupId>
  <artifactId>cloudant-client</artifactId>
  <version>2.17.0</version>
</dependency>
```
This tells Maven which dependencies need to be retrieved when compiling the application, and these dependencies are retrieved from online repositories.

## Step 3 - Connecting to Cloudant
Now you are going to add the required code that is required to connect to Cloudant. The Cloudant Java API is very simple to use and takes care of all the HTTP calls in the background. You can do all the HTTP calls yourself but to make life easier we are going to use the provided API.

Open the following file in your IDE:
`/start/src/main/java/io/openliberty/guides/system/SystemResource.java`

Add the following code into the `getProperties()` method:

```
URL test = null;
try {
test = new URL(endpoint);
} catch (MalformedURLException e) {
e.printStackTrace();
}

// Connect to your Cloudant account
CloudantClient clientDB = com.cloudant.client.api.ClientBuilder.url(test)
       .iamApiKey(apiKey)
       .build();

// List all the Databases
List<String> databases = clientDB.getAllDbs();
System.out.println("All my databases : ");
for ( String db : databases ) {
  System.out.println(db);
}

// Connect to database
Database db = clientDB.database(dbName, false);

// Save new document to database
db.save(System.getProperties());

System.out.println("Data has been added to the DB");
```

Now add the required imports for Cloudant:

```
// Cloudant
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
```
Now finally add in your endpoint, API key and the name of your Database as Strings like so:

```
String endpoint = "your endpoint url";
String apiKey = "your api key";
String dbName = "your db name";
```
## Step 4 - Building the Application
Now you have added the required code simply go back to the root directory `/start`

Run the command `mvn install` to build the application then run `mvn liberty:run-server` to start up the server.

Once the server has started go to the following URL and you should see your JVM properties in your browser:  `http://localhost:9080/system/properties`
Now if you go back to your Cloudant Dashboard you should see a new document with your JVM information.

### Well Done!
You have successfully created a non-sql database on the cloud, Authenticated with it and stored some data via Java.
