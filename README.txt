Simple Jbehave demo used to test the the a customer registration SOAP service

!!! WARNING !!!
The specifications of the web service being tested are needed to build this project. (contract-first implementation)
The required specification can be found by checking-out the repository "customer-service-spec" on git hub
https://github.com/kununickurra/customer-service-spec
and install it using maven (run mvn clean install inside the main directory)

The customer service need to be running when launching the Jbehave tests.
Checkout the repository "customer-service" https://github.com/kununickurra/customer-service
install it using maven and follow README.txt to start the service inside your IDE.

Then you can run/debug the tests and checkout the report

How to run the tests ?
----------------------

Installing the project using maven will automatically run the Jbehave tests.

How to debug the tests ?
------------------------

Simply debug com.cgi.poc.jbehave.registration.stories.RegistrationStory as Junit test.

Where can I get the reports ?
-----------------------------

Jbehave Report will be generated automatically in the following directory: (displayed in the console)
$REPOSITORY_LOCATION\demo-bdd-jbehave\registration-bdd-tests\target\jbehave

Configuration of the Customer service :
---------------------------------------
Default url is http://localhost:9001/CustomerService
Url is configured in the registration-steps-context.xml file.

Enjoy.

Comments are welcome :-)