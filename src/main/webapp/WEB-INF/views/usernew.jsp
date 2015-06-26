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

<form name="usernewForm" ng-app="auth.controllers" ng-controller="userController as users" ng-init="init('${url}', '${username}', '${password}')">

<div class="container">

<h3>New User</h3>

   <a href='${url}'> Home</a><br>

  <div class="alert alert-warning" ng-show="usernewForm.$error.emailtaken">
      Sorry, the email {{ newuser.email }} is already taken
  </div>
  
	<div class="alert alert-success" ng-show="showSuccessAlert">
	    <strong>Done!</strong> {{successTextAlert}}
	</div>
	
	 <div class="alert alert-warning" ng-show="usernewForm.$error.required">
      Required!
   </div>
   
   	 <div class="alert alert-warning" ng-show="usernewForm.$error.email">
      Invalid email!
    </div>

    <table class="table table-striped table-condensed">
        <thead>
        <tr>
            <th style="min-width: 80px;">First name</th>
            <th style="min-width: 80px;">Last name</th>
            <th style="min-width: 80px;">Email</th>
            <th style="min-width: 80px;">Roles</th>
            <th style="min-width: 80px;">Organisations</th>
            <th style="min-width: 80px;">Applications</th>
            <th style="min-width: 80px;">Access Levels</th>
            <th style="width:20px;"> </th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><input type="text" required ng-model="newuser.firstname"></td>
            <td><input type="text" required ng-model="newuser.lastname"></td>
            <td><input type="email" required ng-model="newuser.email"></td>
  

            <td><label ng-repeat="role in userRoles"><input type="checkbox"  checklist-model="newuser.roles"  checklist-value="role.role_id"> {{role.rolename}}</label></td>
            
            <td><label ng-repeat="org in userOrgs"><input type="checkbox"  checklist-model="newuser.orgs" checklist-value="org.org_id"> {{org.orgname}}</label></td>
            
            <td><label ng-repeat="app in userApps"><input type="checkbox"  checklist-model="newuser.apps" checklist-value="app.app_id"> {{app.appname}}</label></td>
            
            <td><label ng-repeat="acc in userAccessLevels"><input type="checkbox"  checklist-model="newuser.accs" checklist-value="acc.acclvl_id"> {{acc.acclvlname}}</label></td>
            
            
            <td><a ng-click="addUser()" class="btn btn-small btn-primary">Add new user</a></td>
        </tr>
         <!-- 
          <div ng-message="recordAvailable">The email address is already in use...</div> -->
        </tbody>
        
        
    </table>
    
</div>

</form>

</body>

  	

</html>