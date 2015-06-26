<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="resources/my.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Register</title>
</head>
<script>
function reset(){
//document.getElementById("email").innerHTML = "";
}
</script>


<body>

<div id="center_div">

<P>  ${msg} </P>

	<sf:form method ="POST"  id="myForm" modelAttribute="dummyuserData" >
	<%-- <sf:errors path="*" element="div" /> --%>
	      <table>
	      
	          <tr>
	               <th><sf:label path="email">Email </sf:label></th>
                 <td><sf:input path="email" /></td> 
                 <td><sf:errors path="email" /></td>		          
	          </tr>
	          
	          <tr>
	               <th><sf:label path="firstname">First Name </sf:label></th>
                 <td><sf:input path="firstname" /></td> 
                 <td><sf:errors path="firstname" /></td>		          
	          </tr>	  
	          
	          <tr>
	               <th><sf:label path="lastname">Last Name </sf:label></th>
                 <td><sf:input path="lastname" /></td> 
                 <td><sf:errors path="lastname" /></td>		          
	          </tr>		                  
	          
	           <tr>		            									
						      <th><sf:label path="userOrgs">Organisations </sf:label></th>
					 		    <td><sf:select path="userOrgs" items="${userOrgs}"
										multiple="true" /></td>
									<td><sf:errors path="userOrgs" /></td>								 								
	           </tr>
	           
	           <tr>		            									
						      <th><sf:label path="userApps">Applications </sf:label></th>
					 		    <td><sf:select path="userApps" items="${userApps}"
										multiple="true" /></td>
									<td><sf:errors path="userApps" /></td>								 								
	           </tr>	 
	           
	           <tr>		            									
						      <th><sf:label path="userRoles">Roles </sf:label></th>
					 		    <td><sf:select path="userRoles" items="${userRoles}"
										multiple="true" /></td>
									<td><sf:errors path="userRoles" /></td>								 								
	           </tr>		                     
		          
	          <tr>		
	              <th></th>
	              <td><input  type="submit" value="submit" />
	              <input type="reset" value="Reset" /></td>
	            <!-- <td><input type="button" value="Reset" onclick="reset()"/></td>   -->
	          </tr>
	          <tr>		
	              <th></th>
	              <td></td>
	          </tr>
	          
	          <tr>		
	              <th></th>
	              <td><a href="/workbenchauth/"> home</a></td>
	          </tr>
	          
	      </table>
	</sf:form>
	
	</div>
</body>
</html>
