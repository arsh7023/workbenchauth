# workbenchauth
Authentication system for the AURIN Workbench

Workbenchauth is implemented as a Java Spring MVC application that provides
REST-API functions for authentication and authorization services to web
applications.  There is also a simple administrative interface implemented
using AngularJS.

A basic user manual is available for [user administration](doc/UserGuide.md).

To build with maven:

	export AURIN_DIR='/etc/aurin'
	mvn clean package -Ddeployment=development -Dsystem=ali-dev -Daurin.dir=$AURIN_DIR

## Requirements

The system has been tested on Ubuntu 14.04 LTS with Apache Tomcat 7 and the Postfix MTA.
