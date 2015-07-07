# WorkbenchAuth developer guide

Workbenchauth is a simple RESTful authentication/authorization provider
implemented as a Maven based Java Spring MVC applicaion.  It also includes a
simple user administration area which is implemented using AngularJS.

# REST API

There are a number of REST API calls tahat are available:

Service URL | Example hearders | Description
--------------------------------------------
/getUsers   | "X-AURIN-USER-ID:aurin" "X-AURIN-PASSWORD:demoPassword" | Get a list of all users and associated data, using administrative password
/getUser    | "X-AURIN-USER-ID:aurin" "user:user@email.address" "password:userPass" | Return a single user's data, using that user's password
/getLicense | "X-AURIN-USER-ID:aurin" "user\_id:1" "app\_id:1" "org\_id:1" | Return the license for a specific user\_id, app\_id and org\_id
/putLicense | "X-AURIN-USER-ID:aurin" "user\_id:1" "app\_id:1" "org\_id:1" | Update the license for a specific user\_id, app\_id and org\_id, and set license state to "agreed"
/resetLicese | "X-AURIN-USER-ID:aurin" "user\_id:1" "app\_id:1" "org\_id:1" | For a specific user\_id, app\_id and org\_id, set license state to "not agreed"
/getPDFLicense | | Sends AURIN-license.pdf to the browser

It is possible to thest the REST API using curl or the Chrome extension "REST Console".  Example curl command lines:

	curl -i -H "X-AURIN-USER-ID:aurin" -H "user:user@email.address" -H "password:userPassword" -H "Accept:application/json" -H "Content-Type:application/json" -XGET "https://whatif-demo/workbenchauth/getUser" -k
	curl -i -H "X-AURIN-USER-ID:aurin" -H "X-AURIN-PASSWORD:demoPassword" -H "Accept:application/json" -H "Content-Type:application/json" -XGET "https://whatif-demo/workbenchauth/getUsers" -k
