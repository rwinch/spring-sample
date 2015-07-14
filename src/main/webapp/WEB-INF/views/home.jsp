<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>
<head>
		<title>Spring MVC Form Handling</title>
</head>
<body>
<h1>Hello <c:out value="${username}"/></h1>
<sec:authorize access="isAuthenticated()">
	<form:form method="post" action="./logout">
	 <input type="submit" value="Sign Out"/>
	</form:form>
</sec:authorize>
</body>
</html>
