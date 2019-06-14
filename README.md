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
3. Once the service is launched, click on it from your service list and then click the button Launch cloudant dashboard.
4. Once the dashboard has opened click create database in the top right and give it whatever name you desire.

#### Create an API Key and grant it permissions to use Cloudant
1. Now go back to your other tab with IBM Cloud open and then click on Manage then Access in the top right corner.
2. In the bottom left click on IBM Cloud API Keys then click create a new IBM Cloud API Key.
3. Give it a name and description then once it is created save it to your local machine and copy it to your clipboard.
4. Now go back to your Cloudant dashboard and click on permissions once you have selected your newly created database.
5. Paste in the API key you just created and click grant permission.
6. Now in the panel above grant all permission to your newly added API key using the tick boxes.

## Step 1 - Getting Started
