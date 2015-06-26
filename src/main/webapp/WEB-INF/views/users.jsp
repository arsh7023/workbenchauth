<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
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
	<h1 >List of Users </h1>
	
	      <ol>
	      
	       <c:forEach var="userData" items="${alldummyuserdata}"> 
	       
	          <%--  <c:out value="${userData.email}"/> || FirstName: <c:out value="${userData.firstname}"/> ||  LastName: <c:out value="${userData.lastname}"/> --%>
	            <a href=<c:out value="/workbenchauth/${userData.user_id}/user"/>><c:out value="${userData.firstname}"/> <c:out value="${userData.lastname}"/></a>
	           <br/>
	       </c:forEach>
	      
	      </ol>

       <a href="/workbenchauth/"> home</a>
	          
	      
	</sf:form>
	
	</div>
</body>
</html>
