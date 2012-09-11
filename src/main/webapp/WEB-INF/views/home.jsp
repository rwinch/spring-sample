<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>
<head>
  <title>Home</title>
</head>
<body>
<h1>Hello ${username}</h1>
<sec:authorize access="isAuthenticated()">
  <a href="${pageContext['request'].contextPath}/j_spring_security_logout">Logout</a>
</sec:authorize>
</body>
</html>
