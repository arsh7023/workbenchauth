//var app = angular.module('auth.controllers', ['auth.services', 'checklist-model', 'colorpicker.module']);
var app= angular.module('pass.controllers', ['auth.services']);

//app.value("urlPrefix", "http://localhost/workbenchauth/")

//app.controller('userController', function(urlPrefix,$scope,$http) {
app.controller('passController', ['$scope','$http','UsersFactory', function ($scope,$http, UsersFactory) {
	
	
	$scope.init = function(url, user_id) {
     $scope.url = url;
     $scope.user_id = user_id;
     $scope.oldpassword = "";
     $scope.newpassword = "";
     $scope.repeatpassword = "";
     
	
  };
	

  $scope.changepass = function () {
  
     	//$scope.changepassForm.$error.passmatch = false;
  	   var lsw=false;
  	   if ($scope.changepassForm.$valid)
  	  	{
  	  	 
  	  	    if ($scope.newpassword != $scope.repeatpassword)
  	  	    	{
  	  	    	 $scope.changepassForm.$error.passmatch = true;
  	  	    	}
  	  	    else
  	  	    	{
  	  	    	
	  	  	    	if($scope.newpassword.length >5)
	  	  	    	{
		  	  	    	$scope.changepassForm.$error.passmatch = false;
		  	  	    	lsw = true;
	  	  	    	}
	  	  	    	else
	  	  	    	{
	  	  	    		$scope.changepassForm.$error.passmatch = true;
	  	  	    	}	
  	  	    	}

  	  	}
  	   else
  	  	{
		  	  if ($scope.newpassword != $scope.repeatpassword)
		 	    	{
		 	    	 $scope.changepassForm.$error.passmatch = true;
		 	    	}
		 	    else
		 	    	{
		  	    	if($scope.newpassword.length >5)
		  	    	{
			 	    	    $scope.changepassForm.$error.passmatch = false;
			 	    	    lsw = true;
		  	    	}
		  	    	else
		  	    	{
		  	    		$scope.changepassForm.$error.passmatch = true;
		  	    	}	
		 	    	}
  	  	} 
  	   
  	   
  	   if (lsw == true)
 	  	{

	  	  	 UsersFactory.changePassword($scope.url + "changeoldpassword",$scope.user_id,$scope.oldpassword, $scope.newpassword).success(function(status){
	  	  		 if (status==true)
	  	  			{
	  	  			 $scope.oldpassword = "";
	  	  			 $scope.newpassword = "";
	  	  			 $scope.repeatpassword = "";

	  	  			 //alert('success');   
	  	  			 $scope.successTextAlert = "Password changed.";
	  	  			 $scope.showSuccessAlert = true;
	  	  			}
	  	  		 else
	  	  			 {
	  	  			   //alert('failed');
	  	  			   $scope.changepassForm.$error.passerror = true;
	  	  			 }
			     	  
		       });
 	  	    
 	  	}
 	   else
 	  	{
 	  	 
 	  	} 
  	   
  	    
	
  };
  
  


}]);
