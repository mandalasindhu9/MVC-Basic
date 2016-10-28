<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<body>
    <h1>Accounts List</h1>
<div class="span-12 last">	
		<form:form modelAttribute="account" action="${account}" method="post">
				<legend>Account Fields</legend>

			<c:choose>
				<c:when test="${not empty account}">
        				<c:forEach items="${account}" var="account"> 
					<p>ID: <c:out value="${account.id}"/> URL: <a href="/mvc-basic/account/<c:out value="${account.id}" />" >Link to ${account.name}</a></p>
					 </c:forEach> 
    				</c:when>
				<c:otherwise>
					 No data here. Lets create an account.
				</c:otherwise>
			</c:choose>



		</form:form>
					<br/>	
						
				<a href="/mvc-basic/account/"> <button type=submit>Create Form</button></a>
	</div>
</body>
</html>
