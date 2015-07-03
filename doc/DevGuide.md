# WorkbenchAuth developer guide

Workbenchauth is a simple RESTful authentication/authorization provider
implemented as a Maven based Java Spring MVC applicaion.  It also includes a
simple user administration area which is implemented using AngularJS.

# REST API

There are a number of REST API calls tahat are available:

Service URL | Example hearders | Description
--------------------------------------------
/getUser    | "X-AURIN-USER-ID:aurin" "user:user@email.address" "password:demoPass" | Return a user's data
/getLicense | "X-AURIN-USER-ID:aurin" "user\_id:1" "app\_id:1" "org\_id:1" | Return the license for a specific user\_id, app\_id and org\_id
/putLicense | "X-AURIN-USER-ID:aurin" "user\_id:1" "app\_id:1" "org\_id:1" | Update the license for a specific user\_id, app\_id and org\_id
/getUsers | |

It is possible to thest the REST API using curl or the Chrome extension "REST Console".  An example curl command line:

	curl -i -H "X-AURIN-USER-ID:aurin" "user:user@email.address" "password:demoPass" -H "Accept:application/json" -H "Content-Type:application/json" -XGET "https://whatif-demo/workbenchauth/getUser" -k


