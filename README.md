# todojava
Sample of todo item application in java. You can create, edit and delete todo tasks. You have to login first.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
The following technologies were used to develop this application: 

* Java 7
* Maven 3
* Toncat 7
* Eclipse IDE for Java EE Developers

## Running Locally

Make sure you have Java and Maven installed.  Also, install the [MySQL](https://toolbelt.heroku.com/).

    $ git clone https://github.com/mauriciopersico/todojava.git
    $ cd todojava
    $ mvn install

You have generated a spring-hibernate-template.war file in the *target* folder that it is in *todojava* root folder project.

Before starting your server, you have to run createtables.sql file (it is inside sql folder) on MySQL.  

Copy the spring-hibernate-template.war from target folder to the location you deploy web applications in Tomcat (default folder is "$CATALINA_BASE/webapps"). 
Start tomcat server on windos:

    %CATALINA_HOME%\bin\startup

Or start tomcat server on linux:

    $CATALINA_HOME/bin/startup.sh

If tomcat host is set to the default value you can access to the url [http://localhost:8080/spring-hibernate-template/](http://localhost:8080/spring-hibernate-template/) to test de app.

There is only one user available to login. The username is **admin@admin.com** and the password is **admin**.

## Development

Import this project from eclipse selecting:  

    1. File -> Import...
    2. Existing Maven Projects
    3. Select project folder
    4. Press finish button

Configure compiler option to generate JPA Metamodels automatically:

    1. Enter to the project options (right click over the project)
    2. Java Compiler -> Annotation Processing

Configure the Annotation Processing like this [link](https://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html#_eclipse) and press ok button.

Configure jpa option:

    1. Enter to the project options (right click over the project)
    2. JPA

Select in the section **Canonical metamodel (JPA 2.0)**,  the **Source Folder** to  **target/metamodel** and press ok button.

Before starting your server, you have to run createtables.sql file (it is inside sql folder) on MySQL.  

Run the application in your tomcat server from eclipse.

You can access to the url [http://localhost:8080/spring-hibernate-template/](http://localhost:8080/spring-hibernate-template/) to test de app.

There is only one user available to login. The username is **admin@admin.com** and the password is **admin**.

## Authors

* **Mauricio Pérsico**
