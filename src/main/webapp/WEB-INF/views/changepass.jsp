<!DOCTYPE html>
<html>
<head></head>

<link rel="stylesheet" href = "https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-route.js"></script>
 
 <script src="../static/services.js"></script> 
<script src="../static/passcontroller.js"></script>
<!-- <script src="../static/checklist-model.js"></script> -->



<body>
<form name="changepassForm" ng-app="pass.controllers" ng-controller="passController as pass" ng-init="init('${msg}', '${id}')">


<div class="container">

	<h3>Change password</h3>
	
	 <div class="alert alert-warning" ng-show="changepassForm.$error.passerror">
       make sure the password is correct!
   </div>
	
	<div class="alert alert-success" ng-show="showSuccessAlert">
	    <strong>Done!</strong> {{successTextAlert}}
	</div>
	
	 <div class="alert alert-warning" ng-show="changepassForm.$error.required">
      Required!
   </div>
      
	<div class="alert alert-warning" ng-show="changepassForm.$error.passmatch">
      password length must be greater than 6 and new passwords must match.
   </div>
	       
	  <table>     
	     <tr><td>Enter old password</td><td><input type="password" required ng-model="oldpassword"></td></tr>  
	     <tr><td>Enter new password</td><td><input type="password" required ng-model="newpassword"></td></tr>  
	     <tr><td>Repeat new password</td><td><input type="password" required ng-model="repeatpassword"></td></tr>  
	     <tr><td></td></tr>    
	     <tr><td><a ng-click="changepass()" class="btn btn-small btn-primary">Change Password</a></td></tr>
	   </table>
	      
	
	    
	</div>




</body>

</html>