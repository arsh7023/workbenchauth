<!DOCTYPE html>
<html>


<link rel="stylesheet" href = "https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-route.js"></script>
 
 <script src="static/services.js"></script> 
<script src="static/controller.js"></script>
<script src="static/checklist-model.js"></script>


<body>
<form name="myForm" ng-app="auth.controllers" ng-controller="userController as users" ng-init="init('${url}', '${username}', '${password}')">

<div class="container">

<h3>Users</h3>
   <a href='${url}'> Home</a><br>

<div class="span6">
    
    
    <div class="alert alert-warning" ng-show="myForm.$error.required">
      Required!
   </div>
   
   	 <div class="alert alert-warning" ng-show="myForm.$error.email">
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
            <th style="width:20px;"> </th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="user in users">
            <td><input type="text"  required ng-disabled='true' ng-model="user.firstname"></td>
            <td><input type="text"  required ng-disabled='true' ng-model="user.lastname"></td>
            <td><input type="email" required ng-disabled='true' ng-model="user.email"></td>
  

            <!-- <td><select size="10" id="myselection" multiple ng-multiple="true" ng-model="selectedColors" ng-options="c.name+' ('+c.shade+')' for c in colors"></select></td> -->
            <td><label ng-repeat="role in userRoles"><input type="checkbox" checklist-model="user.roles" checklist-value="role.role_id"> {{role.rolename}}</label></td>
            
            <td><label ng-repeat="org in userOrgs"><input type="checkbox" checklist-model="user.orgs" checklist-value="org.org_id"> {{org.orgname}}</label></td>
            
            <td><label ng-repeat="app in userApps"><input type="checkbox" checklist-model="user.apps" checklist-value="app.app_id"> {{app.appname}}</label></td>
            
            <td><label ng-repeat="acc in userAccessLevels"><input type="checkbox" checklist-model="user.accs" checklist-value="acc.acclvl_id"> {{acc.acclvlname}}</label></td>
            
            
            <td><a ng-click="editUser(user.user_id)" class="btn btn-small btn-primary">Edit</a></td>
            <td><a ng-click="resetPass(user.email)" class="btn btn-small btn-danger">Reset Password</a></td> 
        </tr>
          
        </tbody>
    </table>
    <!-- <a ng-click="createNewUser()" class="btn btn-small">create new user</a> <button ng-click="clickMe()">Click Me</button>-->
    <a href='${url}'> Home</a><br>
</div>



  	
</div>


</body>

</html>