<!DOCTYPE html>
<html>
<!-- <head>hello</head> -->

<link rel="stylesheet" href = "https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-route.js"></script>
 
 <script src="static/services.js"></script> 
<script src="static/controller.js"></script>
<script src="static/checklist-model.js"></script>

<script src="static/bootstrap-colorpicker-module.js"></script>
<link rel="stylesheet" href = "static/colorpicker.css">

<body>

<form name="additemForm" ng-app="auth.controllers" ng-controller="userController as users" ng-init="init('${url}', '${username}', '${password}')">

<div class="container">

<h3>Adding new item</h3>

   <a href='${url}'> Home</a><br>

  <div class="alert alert-warning" ng-show="additemForm.$error.roletaken">
      Sorry, the role {{ additem.role }} is already taken
  </div>
    <div class="alert alert-warning" ng-show="additemForm.$error.orgtaken">
      Sorry, the organisation {{ additem.org }} is already taken
  </div>
   <div class="alert alert-warning" ng-show="additemForm.$error.apptaken">
      Sorry, the application {{ additem.app }} is already taken
  </div>
  <div class="alert alert-warning" ng-show="additemForm.$error.acctaken">
      Sorry, the access level {{ additem.acc }} is already taken
  </div>  
  
	<div class="alert alert-success" ng-show="showSuccessAddItem">
	    <strong>Done!</strong> {{successAddItemAlert}}
	</div>
	
	 <div class="alert alert-warning" ng-show="additemForm.$error.required">
      Name Required!
   </div>
   

    <table class="table table-striped table-condensed">
        <thead>
        <tr>
            <th style="min-width: 80px;">Role</th>
            <th style="min-width: 80px;">Organisation</th>
            <th style="min-width: 80px;">Application</th>
            <th style="min-width: 80px;">Access Level</th>
            <th style="width:20px;"> </th>
        </tr>
        </thead>
        
       <tbody>
       
        <tr>
            <td><input type="text" required ng-model="additem.role"></td>
            <td><input type="text" required ng-model="additem.org"></td>
            <td><input type="text" required ng-model="additem.app"></td>
            <td><input type="text" required ng-model="additem.acc"></td>
       </tr>             
       <tr>
            <td><a ng-click="addRole(additem.role)" class="btn btn-small btn-primary">Add Role</a></td>
            <td><a ng-click="addOrg(additem.org)" class="btn btn-small btn-primary">Add Organisation</a></td>
            <td><a ng-click="addApp(additem.app)" class="btn btn-small btn-primary">Add Application</a></td>
            <td><a ng-click="addAcc(additem.acc)" class="btn btn-small btn-primary">Add Access Level</a></td>
        </tr>  
        
        </tbody>
        
        
    </table>
    
</div>

</form>

</body>

  	

</html>