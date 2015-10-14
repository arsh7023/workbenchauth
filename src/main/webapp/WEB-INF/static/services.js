
var myModule = angular.module('auth.services', []);
myModule.service('UsersFactory', function($http) {

     return {
//       get: function(username,password,url) {
//      	 
//      	 //alert($scope.url);
//     			var config = {
//     					'X-AURIN-USER-ID' : username,
//     					'X-AURIN-PASSWORD' : password,
//     					'Content_Type' : 'application/json',
//     					}; 
//     		  //var vurl =  "http://localhost/workbenchauth/getUsers";
//          vurl = url + "getUsers";
//           var promise = $http({method: 'GET', url: vurl, headers: config})
//                .success(function(result) {
//             			console.log(result);
//                  return result;
//             		}); 
//
//           return promise;
//       }
//     ,
     updateRoles: function(username,password,url,user_id,roles) {
  			var config = {
  					'X-AURIN-USER-ID' : username,
   					'X-AURIN-PASSWORD' : password,
  					'user_id' : user_id,
  					'role_id' : roles,
  					'Content_Type' : 'application/json',
  					}; 
  			
  		 // var vurl =  "http://localhost/workbenchauth/putRoles";
  		  var vurl = url;

        var promise = $http({method: 'PUT', url: vurl, headers: config})
        .success(function (status, headers, config)
        		{
          			console.log(status);
               return status;
          		}); 

        //return promise;
  		  
       }	
     
     ,
     updateOrgs: function(username,password,url,user_id,orgs) {
  			var config = {
  					'X-AURIN-USER-ID' : username,
   					'X-AURIN-PASSWORD' : password,
  					'user_id' : user_id,
  					'org_id' : orgs,
  					'Content_Type' : 'application/json',
  					}; 
  			
  		  var vurl = url;

        var promise = $http({method: 'PUT', url: vurl, headers: config})
        .success(function (status, headers, config)
        		{
          			console.log(status);
               return status;
          		}); 
          
         }	
     
     ,
     updateApps: function(username,password,url,user_id,apps) {
  			var config = {
  					'X-AURIN-USER-ID' : username,
   					'X-AURIN-PASSWORD' : password,
  					'user_id' : user_id,
  					'app_id' : apps,
  					'Content_Type' : 'application/json',
  					}; 
  			
  		  var vurl = url;

        var promise = $http({method: 'PUT', url: vurl, headers: config})
        .success(function (status, headers, config)
        		{
          			console.log(status);
               return status;
          		}); 
            
         }   
     
     ,
     updateAccs: function(username,password,url,user_id,accss) {
  			var config = {
  					'X-AURIN-USER-ID' : username,
   					'X-AURIN-PASSWORD' : password,
  					'user_id' : user_id,
  					'acclvl_id' : accss,
  					'Content_Type' : 'application/json',
  					}; 
  			
  		  var vurl = url;

        var promise = $http({method: 'PUT', url: vurl, headers: config})
        .success(function (status, headers, config)
        		{
          			console.log(status);
               return status;
          		}); 
            
         } 
     
     ,
     updateUserDetails: function(username,password,url,user_id,firstname,lastname,email) {
  			var config = {
  					'X-AURIN-USER-ID' : username,
   					'X-AURIN-PASSWORD' : password,
  					'user_id' : user_id,
  					'firstname' : firstname,
  					'lastname' : lastname,
  					'email' : email,
  					'Content_Type' : 'application/json',
  					}; 
  			
  		  var vurl = url;

        var promise = $http({method: 'PUT', url: vurl, headers: config})
        .success(function (status, headers, config)
        		{
          			console.log(status);
               return status;
          		}); 
            
         }     
     
     ,
     resetPass: function(username,password,url,email) {
  			var config = {
  					'X-AURIN-USER-ID' : username,
   					'X-AURIN-PASSWORD' : password,
  					'email' : email,
  					'Content_Type' : 'application/json',
  					}; 
  			
  		  var vurl = url;

        var promise = $http({method: 'POST', url: vurl, headers: config})
        .success(function (status, headers, config)
        		{
          		 console.log(status);
               return status;
          		}); 
            
         }   
     
     ,
     deleteUser: function(username,password,url,user_id) {
  			var config = {
  					'X-AURIN-USER-ID' : username,
   					'X-AURIN-PASSWORD' : password,
  					'user_id' : user_id,
  					'Content_Type' : 'application/json',
  					}; 
  			
  		  var vurl = url;

        var promise = $http({method: 'POST', url: vurl, headers: config})
        .success(function (status, headers, config)
        		{
          		 console.log(status);
               return status;
          		}); 
            
         }   
     
     ,
     searchUser: function(username,password,url,namesearch,family,email) {

    	  	 
  			var config = {
  					'X-AURIN-USER-ID' : username,
   					'X-AURIN-PASSWORD' : password,
//  					'name1' : namesearch,
//  					'family1' : family,
//  					'email1' : email,
//   					"Content-Type" : "application/json",
//  					"Accept" : "application/json",
//  					data: '',
  					'Content-Type': 'application/x-www-form-urlencoded'  					
  					}; 
  			
  			 var jsonData = {
   					'name1' : namesearch,
   					'family1' : family,
  					'mail1' : email,

         };
  			 
  			 jsonData = namesearch + "," + family + "," + email;
  			
  		  var vurl = url;
  		  
  		  //vurl = "https://localhost/workbenchauth/searchUser/";

        var promise = $http({method: 'POST', url: vurl, data:jsonData, headers: config})
        .success(function (result)
        		{
          			
               return result;
          		}); 
            return promise;
         }
     ,
     
     insertUser: function(username,password,url,name,family,email,roles,orgs,apps,accs) {
  			var config = {
  					'X-AURIN-USER-ID' : username,
   					'X-AURIN-PASSWORD' : password,
  					'name' : name,
  					'family' : family,
  					'email' : email,
  					'roles' : roles,
  					'orgs' : orgs,
  					'apps' : apps,
  					'accs' : accs,
  					'Content_Type' : 'application/json',
  					}; 
  			
  		  var vurl = url;

        var promise = $http({method: 'POST', url: vurl, headers: config})
        .success(function (status, headers, config)
        		{
          		 //console.log(status);
        	      //alert(status);
               return status;
          		}); 
            return promise;
            
         }  
   
     ,
     
     insertRole: function(username,password,url,role) {
 			var config = {
 					'X-AURIN-USER-ID' : username,
  				'X-AURIN-PASSWORD' : password,
 					'role' : role,
 					'Content_Type' : 'application/json',
 					}; 
 			
 		  var vurl = url;

       var promise = $http({method: 'POST', url: vurl, headers: config})
       .success(function (status, headers, config)
       		{
              return status;
         		}); 
           return promise;
           
        }  
     
     ,
     
     insertOrg: function(username,password,url,org) {
 			var config = {
 					'X-AURIN-USER-ID' : username,
  				'X-AURIN-PASSWORD' : password,
 					'org' : org,
 					'Content_Type' : 'application/json',
 					}; 
 			
 		  var vurl = url;

       var promise = $http({method: 'POST', url: vurl, headers: config})
       .success(function (status, headers, config)
       		{
              return status;
         		}); 
           return promise;
           
        }    
     
     ,
     
     insertApp: function(username,password,url,app) {
 			var config = {
 					'X-AURIN-USER-ID' : username,
  				'X-AURIN-PASSWORD' : password,
 					'app' : app,
 					'Content_Type' : 'application/json',
 					}; 
 			
 		  var vurl = url;

       var promise = $http({method: 'POST', url: vurl, headers: config})
       .success(function (status, headers, config)
       		{
              return status;
         		}); 
           return promise;
           
        }    
     
     ,
     
     insertAcc: function(username,password,url,acc) {
 			var config = {
 					'X-AURIN-USER-ID' : username,
  				'X-AURIN-PASSWORD' : password,
 					'acc' : acc,
 					'Content_Type' : 'application/json',
 					}; 
 			
 		  var vurl = url;

       var promise = $http({method: 'POST', url: vurl, headers: config})
       .success(function (status, headers, config)
       		{
              return status;
         		}); 
           return promise;
           
        }      
  
    ,
     
     
     changePassword: function(url,uuid,oldpass,newpass) {
 			var config = {
 					'uuid' : uuid,
 					'oldpassword' : oldpass,
 					'newpassword' : newpass,
 					'Content_Type' : 'application/json',
 					}; 
 			
 		   var vurl = url;

       var promise = $http({method: 'POST', url: vurl, headers: config})
       .success(function (status, headers, config)
       		{
         		 //console.log(status);
       	      //alert(status);
              return status;
         		}); 
           return promise;
           
        }  
        
    };
   

});


//  
