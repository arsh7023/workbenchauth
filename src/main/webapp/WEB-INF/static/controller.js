//var app = angular.module('auth.controllers', ['auth.services', 'checklist-model', 'colorpicker.module']);
var app= angular.module('auth.controllers', ['auth.services', 'checklist-model']);

//app.value("urlPrefix", "http://localhost/workbenchauth/")

//app.controller('userController', function(urlPrefix,$scope,$http) {
app.controller('userController', ['$scope','$http','UsersFactory', function ($scope,$http, UsersFactory) {
	
	
	$scope.init = function(url, username,password) {
    $scope.url = url;
 
			var config = {
					'X-AURIN-USER-ID' : username,
					'X-AURIN-PASSWORD' : password,
					'Content_Type' : 'application/json',
					}; 
			
			$scope.username = username;
			$scope.password = password;
			
		  var vurl =  ""; //http://localhost/workbenchauth/getUsers
      vurl = $scope.url + "getUsers";
      var promise = $http({method: 'GET', url: vurl, headers: config})
          .success(function(res) {
       			console.log(res);

       		   /////////////////////////////////////////////////////////
       		   
       		  var usersArray=[];
      		  
     		   for (var k=0; k<res.length; k++)
     		   {			
     		  		var x ={};
     		  		x.user_id = res[k].user_id;
     		  		x.firstname = res[k].firstname;
     		  		x.lastname = res[k].lastname;
     		  		x.email = res[k].email;
     		  		
     		  		//roles
     		  		var ar =[];
     		  		for (var i=0; i<res[k].userRoles.length; i++)
     		  		{	
     			        ar.push(res[k].userRoles[i].role_id);
     		  		}
     		  		x.roles = ar;
     		  		
     		  		//orgs
     		  		var gr =[];
     		  		for (var i=0; i<res[k].userOrganisations.length; i++)
     		  		{	
     			        gr.push(res[k].userOrganisations[i].org_id);
     		  		}
     		  		x.orgs = gr;
     		  		
     		  		//apps
     		  		var ap =[];
     		  		for (var i=0; i<res[k].userApplications.length; i++)
     		  		{	
     		  			ap.push(res[k].userApplications[i].app_id);
     		  		}
     		  		x.apps = ap;
  	  		
     		  		//accessLevels
     		  		var acc =[];
     		  		for (var i=0; i<res[k].userAccessLevels.length; i++)
     		  		{	
     		  			acc.push(res[k].userAccessLevels[i].acclvl_id);
     		  		}
     		  		x.accs = acc;
     		  		usersArray.push(x);
     		  		
//     		  		$scope.userRoles = res[k].userRoles;
//     		  		$scope.userOrganisations = res[k].userOrganisations;
     		   }		
     		
     		   $scope.users= usersArray; 
       		   
       		   
       		   
       		}); 
      
      vurl = $scope.url + "getAllRoles";
      var roles = $http({method: 'GET', url: vurl, headers: config})
          .success(function(res) {
       			console.log(res);  		
     		  		$scope.userRoles = res;
     	
       		}); 
      
      vurl = $scope.url + "getAllOrganisations";
      var orgs = $http({method: 'GET', url: vurl, headers: config})
          .success(function(res) {
       			console.log(res);  		
     		  		$scope.userOrgs = res;
     	
       		}); 
      
      vurl = $scope.url + "getAllApplications";
      var apps = $http({method: 'GET', url: vurl, headers: config})
          .success(function(res) {
       			console.log(res);  		
     		  		$scope.userApps = res;
     	
       		}); 
      
      vurl = $scope.url + "getAllAccessLevels";
      var accss = $http({method: 'GET', url: vurl, headers: config})
          .success(function(res) {
       			console.log(res);  		
     		  		$scope.userAccessLevels = res;
     	
       		}); 
      
    //alert(url);
      $scope.namesearch="";
      $scope.familysearch="";
      $scope.emailsearch="";
      $scope.usersSearch= []; 
      
      var y ={};
  		y.firstname = "";
  		y.lastname = "";
  		y.email = "";
  		y.roles = [];
  		y.orgs = [];
  		y.apps = [];
  		y.accs = [];
  		
  		$scope.newuser=y;
  		
  		var z = {};
  		z.role = "";
  		z.org = "";
  		z.app = "";
  		z.acc = "";
  		
  		$scope.additem=z;
  		
  		$scope.cp="";
    
  };
	
    $scope.editUser = function (user_id) {
	  	angular.forEach($scope.users, function(value, key) {	  		
	  		  if(value.user_id == user_id)
	  		  	{
               //alert(value.firstname);
               //var vurl =  "http://localhost/workbenchauth/putRoles";
             	  //alert($scope.url); 
	  			      UsersFactory.updateRoles($scope.username,$scope.password,$scope.url + "putRoles",user_id,value.roles,function(status){
	  			     	  alert(status);   
	  			       });
	  			      UsersFactory.updateOrgs($scope.username,$scope.password,$scope.url + "putOrgs",user_id,value.orgs,function(status){
	  			     	  alert(status);   
	  			       });
	  			      UsersFactory.updateApps($scope.username,$scope.password,$scope.url + "putApps",user_id,value.apps,function(status){
	  			     	  alert(status);   
	  			       });
	  			      
	  			      UsersFactory.updateAccs($scope.username,$scope.password,$scope.url + "putAccss",user_id,value.accs,function(status){
	  			     	  alert(status);   
	  			       });
	  			      alert('updated'); 
	  			    
	  		  	}	  	
	  	});  	  	 
    };
    
    
    $scope.editUserSearch = function (user_id) {
 	   if ($scope.myForm.$valid)
	  	{
		  	angular.forEach($scope.usersSearch, function(value, key) {	  		
		  		  if(value.user_id == user_id)
		  		  	{
	               //alert(value.firstname);
	               //var vurl =  "http://localhost/workbenchauth/putRoles";
	             	  //alert($scope.url); 
		  			      UsersFactory.updateRoles($scope.username,$scope.password,$scope.url + "putRoles",user_id,value.roles,function(status){
		  			     	  alert(status);   
		  			       });
		  			      UsersFactory.updateOrgs($scope.username,$scope.password,$scope.url + "putOrgs",user_id,value.orgs,function(status){
		  			     	  alert(status);   
		  			       });
		  			      UsersFactory.updateApps($scope.username,$scope.password,$scope.url + "putApps",user_id,value.apps,function(status){
		  			     	  alert(status);   
		  			       });
		  			      
		  			      UsersFactory.updateAccs($scope.username,$scope.password,$scope.url + "putAccss",user_id,value.accs,function(status){
		  			     	  alert(status);   
		  			       });
		  			    
		  			      UsersFactory.updateUserDetails($scope.username,$scope.password,$scope.url + "putUserDetails",user_id,value.firstname,value.lastname,value.email,function(status){
		  			     	  alert(status);   
		  			       });
		  			      alert('updated'); 
		  			    
		  		  	}	  	
		  	}); 
	  	}
    };
    
    $scope.deleteUser = function (user_id,  $window) {
    
    	if (confirm("Are you sure?")) {
        	UsersFactory.deleteUser($scope.username,$scope.password,$scope.url + "deleteUser",user_id,function(status){
  	     	  alert(status);   
  	       });
	      	alert('Deleted'); 
	      	$scope.usersSearch= [];
    	}
       
    	
    };
    
    $scope.resetPass = function (email) {
    	UsersFactory.resetPass($scope.username,$scope.password,$scope.url + "sendEmailLinkPasswordChange",email,function(status){
     	  alert(status);   
       });
      alert('Email sent'); 
  };
  
  $scope.addRole = function (role) {
  	UsersFactory.insertRole($scope.username,$scope.password,$scope.url + "addRole",role).success(function(status){
 		 if (status==true)
 			{
 			 $scope.additem.role = "";
 			 $scope.successAddItemAlert = "Role inserted.";
 			 $scope.showSuccessAddItem = true;
 			 $scope.additemForm.$error.roletaken = false;
   
 			}
 		 else
 			 {
 			 	$scope.additemForm.$error.roletaken = true;
 			 }
    	  
    });

	};
	
  $scope.addOrg = function (org) {
  	UsersFactory.insertOrg($scope.username,$scope.password,$scope.url + "addOrg",org).success(function(status){
 		 if (status==true)
 			{
 			 $scope.additem.org = "";
 			 $scope.successAddItemAlert = "Organisation inserted.";
 			 $scope.showSuccessAddItem = true;
 			$scope.additemForm.$error.orgtaken = false;
   
 			}
 		 else
 			 {
 			 	$scope.additemForm.$error.orgtaken = true;
 			 }
    	  
    });

	};
	
  $scope.addApp = function (app) {
  	UsersFactory.insertApp($scope.username,$scope.password,$scope.url + "addApp",app).success(function(status){
 		 if (status==true)
 			{
 			 $scope.additem.app = "";
 			 $scope.successAddItemAlert = "Application inserted.";
 			 $scope.showSuccessAddItem = true;
 			$scope.additemForm.$error.apptaken = false;
   
 			}
 		 else
 			 {
 			 	$scope.additemForm.$error.apptaken = true;
 			 }
    	  
    });

	};
	
  $scope.addAcc = function (acc) {
  	UsersFactory.insertAcc($scope.username,$scope.password,$scope.url + "addAcc",acc).success(function(status){
 		 if (status==true)
 			{
 			 $scope.additem.acc = "";
 			 $scope.successAddItemAlert = "Access level inserted.";
 			 $scope.showSuccessAddItem = true;
 			 $scope.additemForm.$error.acctaken = false;
 			
 			}
 		 else
 			 {
 			 	$scope.additemForm.$error.acctaken = true;
 			 }
    	  
    });

	};
	
    
    $scope.searchUser = function () {
    	
    	//alert($scope.namesearch);
    	 UsersFactory.searchUser($scope.username,$scope.password,$scope.url + "searchUser", $scope.namesearch, $scope.familysearch, $scope.emailsearch).success(function(res){
      	
        
    		 var usersArray=[];
   		    alert(res.length + " found");
   		   for (var k=0; k<res.length; k++)
   		   {			
   		  		var x ={};
   		  		x.user_id = res[k].user_id;
   		  		x.firstname = res[k].firstname;
   		  		x.lastname = res[k].lastname;
   		  		x.email = res[k].email;
   		  		
   		  		//roles
   		  		var ar =[];
   		  		for (var i=0; i<res[k].userRoles.length; i++)
   		  		{	
   			        ar.push(res[k].userRoles[i].role_id);
   		  		}
   		  		x.roles = ar;
   		  		
   		  		//orgs
   		  		var gr =[];
   		  		for (var i=0; i<res[k].userOrganisations.length; i++)
   		  		{	
   			        gr.push(res[k].userOrganisations[i].org_id);
   		  		}
   		  		x.orgs = gr;
   		  		
   		  		//apps
   		  		var ap =[];
   		  		for (var i=0; i<res[k].userApplications.length; i++)
   		  		{	
   		  			ap.push(res[k].userApplications[i].app_id);
   		  		}
   		  		x.apps = ap;
   		  		
   		  		//accessLevels
   		  		var acc =[];
   		  		for (var i=0; i<res[k].userAccessLevels.length; i++)
   		  		{	
   		  			acc.push(res[k].userAccessLevels[i].acclvl_id);
   		  		}
   		  		x.accs = acc;
   		  		
   		  		usersArray.push(x);
   		  		
//   		  		$scope.userRoles = res[k].userRoles;
//   		  		$scope.userOrganisations = res[k].userOrganisations;
   		   }		
   		
   		   $scope.usersSearch= usersArray; 
    		 
   		  $scope.namesearch="";
        $scope.familysearch="";
        $scope.emailsearch="";

	  // alert('done');
		
	   });
  };
    
  
//  UsersFactory.get($scope.headconfig,$scope.url).then(function(res){
//
// });

  
  $scope.doCustom=function() {
      $('#myselection').select2();
  };
  
  $scope.addUser = function () {
  
  	   var lsw=false;
  	   if ($scope.usernewForm.$valid == true)
  	  	{
  	  	    lsw=true;
  	  	}
  	   else
  	  	{
  	  	  if ($scope.usernewForm.$error.emailtaken == true)
  	  	  {
  	  	  	  $scope.usernewForm.$error.emailtaken = false;
  	  	  	   lsw=true;
  	  	  }	
  	  	} 
  	   //if ($scope.usernewForm.$valid)
  	  	if (lsw==true)
  	  	{

	  	  	 UsersFactory.insertUser($scope.username,$scope.password,$scope.url + "insertuser",$scope.newuser.firstname,$scope.newuser.lastname, $scope.newuser.email, $scope.newuser.roles, $scope.newuser.orgs, $scope.newuser.apps, $scope.newuser.accs).success(function(status){
	  	  		 if (status==true)
	  	  			{
	  	  			 $scope.newuser.firstname = "";
	  	  			 $scope.newuser.lastname = "";
	  	  			 $scope.newuser.email = "";
	  	  			 $scope.newuser.roles = "";
	  	  			 $scope.newuser.orgs = "";
	  	  			 $scope.newuser.apps = "";
	  	  			 $scope.newuser.accs = "";
	  	  			 
	  	  			 $scope.successTextAlert = "User created.";
	  	  			 $scope.showSuccessAlert = true;
	  	  			 
	  	  			 //alert('success');   
	  	  			}
	  	  		 else
	  	  			 {
	  	  			 //alert('failed');
	  	  			// $scope.newuser.$error.emailtaken = true;
	  	  			 $scope.usernewForm.$error.emailtaken = true;
	  	  			 //$scope.usernewForm.$valid = true;
	  	  			 }
			     	  
		       });
  	  	    
  	  	}
  	   else
  	  	{
  	  	 
  	  	} 
  	    
	
  };
  
  


}]);
