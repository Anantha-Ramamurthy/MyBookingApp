# MyBookingApp:
-------------
This is a  a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats in a performance venue.
The application is written in Java.
The requested seats have to be put on Hold first, and then confirmed.
Any seats that are not confirmed within a preset time, would be considered expired and sent back to the available pool. A period evictor helps checking for expired seats and reclaiming them.
The application can be built and unit tested with Maven (Instructions below).
A sample CLI client is also provided for testing a simple booking experince.

Note: All commands in this document are to be run from the application root directory.


# ASSUMPTIONS:
1. The seats are always numbered starting from 1 to N (configurable) - 1 being the closest to the stage and N being the farthest.
2. The best seats are the ones closer to the stage. 
3. All data is managed in-memory and there is no persistent storage or data services in this implementation.
4. Email ID format validation is not part of the scope.


# ENVIRONMENT SET UP (Prerequisite)
1. Needs JDK 1.8 or higher to be installed and be on the System Path.
2. Needs Apache Maven 3.5.2 or higher installed and be on the System Path.
3. Needs Git installation to check out the source from GitHub.


# LOGGING
The application audits all Hold, Reserve, Eviction activities and Seat Status using Log4j.
They can be found in the following location after build and unit testing or when testing through the CLI Client.
        MyBookingApp/bookingApp.log
To tail the logs, as we test the application through the CLI client, use the following command.
        tail -f bookingApp.log


# BUILD INSTRUCTIONS (Refer to CONFIGURATIONS section below for details on customizable configurations)
1. Check out the code from GitHub(https://github.com/Anantha-Ramamurthy/MyBookingApp) to a local directory.
2. Navigate to the MyBookingApp folder of the checked out source.
3. To build and run the unit tests, run the following command.

     mvn clean package
		
    This would download any dependencies and compile the source, run the unit tests and package the application as a Jar in the "target" directory.
4. After a successful build, to just run the Unit test cases alone, use the following command.

     mvn compile test


# TEST USING CLI
The sample CLI client attached can be used to hold, reserve tickets in realtime, by answering to the prompts. 
Use the following command when in the MyBookingApp directory for launching the client.

     mvn exec:java


# DOCUMENTATION
The API documentation and Project Dependency documentation can be found at the following location. Open the following files in a browser to view the documentation.

API Docs: MyBookingApp/target/site/apidocs/index.html

Project Docs: MyBookingApp/target/site/index.html


# CONFIGURATIONS
The application allows configuring the following values.
1. Venue capacity - Seat Capacity of the Venue
2. Evictor thread frequency (cycle time in milliseconds) - This is the time in milliseconds between the runs of the Evictor thread that reclaims the seats that were not confirmed.
3. Seat Hold expiration time - Time in milliseconds after which a seat hold is considered expired.

The above values can be configured in the MyBookingApp/pom.xml file. Edit the following section of the file for the values to be used. All time values are in milliseconds.

&lt;properties&gt;

 &lt;project.build.sourceEncoding&gt;UTF-8&lt;/project.build.sourceEncoding&gt;
  
  &lt;venue.capacity&gt;venue.capacity&lt;/venue.capacity&gt;
	
  &lt;venue.capacity.value&gt;50&lt;/venue.capacity.value&gt; &lt;!-- Change this for Venu capacity --&gt;
	
  &lt;evictor.cycleTime&gt;evictor.cycleTime&lt;/evictor.cycleTime&gt;
	
  &lt;evictor.cycleTime.value&gt;25000&lt;/evictor.cycleTime.value&gt; &lt;!-- Change this for adjusting the Evictor Cycle time--&gt;
	
  &lt;seat.expirationTime&gt;seat.expirationTime&lt;/seat.expirationTime&gt;
	
  &lt;seat.expirationTime.value&gt;25000&lt;/seat.expirationTime.value&gt; &lt;!-- Change this for setting the seat expiration time --&gt;
  
&lt;/properties&gt;

# NOTE: The values shown above are the default values used by the application, if not customized.

