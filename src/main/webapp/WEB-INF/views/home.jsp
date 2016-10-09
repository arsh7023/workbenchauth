<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html style="position: relative;min-height: 100%;">

<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src= "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-route.js"></script>

<head>
<link href="resources/my.css" rel="stylesheet" type="text/css" />
	<title>Home</title>
</head>
<body>

		<form  ng-init="init('${url}')">
		  <div id="header">		
		  	<h1>
					User Management
				</h1>
	
       	<a href='${url}'> Home</a><br>
				<a href='${url}usernew'> Register</a><br>
				<!-- <a href='${url}userlist'> Users</a><br> -->
				<a href='${url}search'> Search user</a><br>
				<a href='${url}additem'> Add item</a><br>
			</div>
	
			<div id="menu-div">		

			</div>
			</form>


<div id="center_div">

</div>
</body>
</html>
