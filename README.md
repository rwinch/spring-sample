Instructions
=================

Your are likely viewing this because you have submitted a forum question or a JIRA and this is a sample project to illustrate points made in my response. You can either run this from the commandline or an IDE that supports Maven (i.e. Spring Tool Suite).

Running from the commandline
---------------------------------

To run the sample application you must have Maven 3 installed. After doing so you can easily run the application using the cargo plugin. Specifically:

* Navigate to the folder on the commandline
* Execute

    mvn package spring-boot:run 

* Open http://localhost:8080/user/hello or http://localhost:8080/admin/hello in your browser
* You can log in with user/password or admin/password (depending on the chosen URL)
* To stop the container use Ctrl-C from the commandline
