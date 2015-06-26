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
<!-- <div ng-app="auth.controllers" ng-controller="userController as users"> -->

<P> </P>

  
<div class="container">

<h3>Search</h3>

<a href='${url}'> Home</a><br>

<%-- <div class="span6" ng-init="init('${msg}')"> --%>
<div class="span6">

    <table class="table table-striped table-condensed">
    

        <thead>
        <tr>
            <th style="min-width: 80px;">First name</th>
            <th style="min-width: 80px;">Last name</th>
            <th style="min-width: 80px;">Email</th>
        </tr>
        </thead>
        <tbody>
        <tr>

            <td><input type="text"  ng-model="namesearch"></td>
            <td><input type="text"  ng-model="familysearch"></td>
            <td><input type="text"  ng-model="emailsearch"></td>
         </tr>
         <tr><td><a ng-click="searchUser()" class="btn btn-small btn-primary">Search</a></td></tr>
         </tbody>
            
    </table>
    
    <div class="alert alert-warning" ng-show="myForm.$error.required">
      Required!
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
        <tr ng-repeat="user in usersSearch">
            <td><input type="text" required ng-disabled='true' ng-model="user.firstname"></td>
            <td><input type="text" required ng-disabled='true' ng-model="user.lastname"></td>
            <td><input type="email" required ng-disabled='true' ng-model="user.email"></td>
  

           <!--  <td><select size="10" id="myselection" multiple ng-multiple="true" ng-model="selectedColors" ng-options="c.name+' ('+c.shade+')' for c in colors"></select></td> -->
            <td><label ng-repeat="role in userRoles"><input type="checkbox" checklist-model="user.roles" checklist-value="role.role_id"> {{role.rolename}}</label></td>
            
            <td><label ng-repeat="org in userOrgs"><input type="checkbox" checklist-model="user.orgs" checklist-value="org.org_id"> {{org.orgname}}</label></td>
            
            <td><label ng-repeat="app in userApps"><input type="checkbox" checklist-model="user.apps" checklist-value="app.app_id"> {{app.appname}}</label></td>
            
            <td><label ng-repeat="acc in userAccessLevels"><input type="checkbox" checklist-model="user.accs" checklist-value="acc.acclvl_id"> {{acc.acclvlname}}</label></td>
            
            
            <td><a ng-click="editUserSearch(user.user_id)" class="btn btn-small btn-primary">Edit</a></td>
           <td><a ng-click="resetPass(user.email)" class="btn btn-small btn-danger">Reset Password</a></td> 
        </tr>
          
        </tbody>
    </table>  
   <!--  <a href="/workbenchauth/"> Home</a><br> -->
   <a href='${url}'> Home</a><br>
</div>


  	
</div>


</body>

</html>
