<%@page import="java.util.*,com.java.jdbc.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<title>Students</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<body>
<div id="wrapper"> 
<div id = "header">
<h2>Welcome to University</h2> 
</div>
</div>
<div id="container"> 
<div id = "content">

<input type="button" value="Add Submit" onclick="window.location.href='add-student-form.jsp';return false" class="add-student-button"/>

<table>
 <tr>
 <th>First Name</th>
 <th>Last Name</th>
 <th>email</th>
 <th>Action</th>
 <th>Action</th>
 </tr>
 <c:forEach var="s" items="${student_list}">
	<tr>
	
	<c:url var="tempLink" value="StudentControllerServlet">
	<c:param name="command" value="LOAD" />
	<c:param name="studentId" value="${s.id}" />
	</c:url>
	<c:url var="tempLink2" value="StudentControllerServlet">
	<c:param name="command" value="DELETE" />
	<c:param name="studentId" value="${s.id}" />
	</c:url>
	
	<td> ${ s.firstName}  </td>
	<td>${ s.lastName}</td>
	<td>${ s.email}</td>
	<td><a href="${tempLink}">Update</a></td>
	<td><a href="${tempLink2}">Delete</a></td>
	
	</tr>
</c:forEach> 
</table>
</div>
</div>


</body>
</html>