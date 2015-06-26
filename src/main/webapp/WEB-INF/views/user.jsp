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
<title>User details</title>
</head>
<script>
function reset(){
//document.getElementById("email").innerHTML = "";
}
</script>


<body>


<P>  ${msg} </P>

	<sf:form method ="POST"  id="myForm2" modelAttribute="dummyuserData2" >
	
<div id="center_div" style="margin-left:auto;	margin-right:auto;	margin-top:1px;   width:900px;">


	<%-- <sf:errors path="*" element="div" /> --%>
	      <table>
	      
	          <tr>
	               <th><sf:label path="email">Email </sf:label></th>
                 <td><sf:input path="email" value="${onedummyuserdata2.email}" /></td> 
                 <td><sf:errors path="email" /></td>		          
	          </tr>
	          
	          <tr>
	               <th><sf:label path="firstname">First Name </sf:label></th>
                 <td><sf:input path="firstname" value="${onedummyuserdata2.firstname}"/></td> 
                 <td><sf:errors path="firstname" /></td>		          
	          </tr>	  
	          
	          <tr>
	               <th><sf:label path="lastname">Last Name </sf:label></th>
                 <td><sf:input path="lastname" value="${onedummyuserdata2.lastname}"/></td> 
                 <td><sf:errors path="lastname" /></td>		          
	          </tr>		                  
	          
	           <tr>		            									
						      <th><sf:label path="userOrgsNew">Organisations </sf:label></th>
												        
								     <td><select name='flselectorg' id='flselectorg' multiple="true" >
								    	<c:forEach  var="myOrg" items="${userOrgsNew}">
								    	  <option value="${myOrg.org_id}" <c:out value="${myOrg.selected}"/> >${myOrg.orgname}</option> 
								         </c:forEach> 
								        </select>
								      </td>     
								     <td><sf:errors path="userOrgsNew" /></td>			 								
	           </tr>
	           
	           <tr>		            									
						      <th><sf:label path="userAppsNew">Applications </sf:label></th>
												        
								     <td><select name='flselectapp' id='flselectapp' multiple="true" >
								    	<c:forEach  var="myApp" items="${userAppsNew}">
								    	  <option value="${myApp.app_id}" <c:out value="${myApp.selected}"/> >${myApp.appname}</option> 
								         </c:forEach> 
								        </select>
								      </td>     
								     <td><sf:errors path="userAppsNew" /></td>			 								
	           </tr>	           
	           
	           <tr>		            									
						      <th><sf:label path="userRolesNew">Roles </sf:label></th>
												        
								     <td><select name='flselectrole' id='flselectrole' multiple="true" >
								    	<c:forEach  var="myRole" items="${userRolesNew}">
								    	  <option value="${myRole.role_id}" <c:out value="${myRole.selected}"/> >${myRole.rolename}</option> 
								         </c:forEach> 
								        </select>
								      </td>     
								     <td><sf:errors path="userRolesNew" /></td>			 								
	           </tr>	           

	          <!--<tr>		
	              <th></th>
	              <td><input  type="submit" value="submit" />
	              <input  type="submit" value="delete" /></td>
	          
	          </tr> --> 
	          <tr>		
	              <th></th>
	              <td></td>
	          </tr>
	          
	          <tr>		
	              <th></th>
	             <td><a href="/workbenchauth/users"> users</a></td>
	          </tr>
	          
	          <tr>		
	              <th></th>
	              <td><a href="/workbenchauth/"> home</a></td>
	              
	          </tr>
	          

	      </table>
	      	</div>
	</sf:form>

</body>
</html>
