<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>
<head>
  <title>Home</title>
</head>
<body>
  <h1>Hello ${username}</h1>
  <a href="${pageContext['request'].contextPath}/spring_security_login">Login</a>
  <a href="${pageContext['request'].contextPath}/j_spring_security_logout">Logout</a>
  <a href="${pageContext['request'].contextPath}/writeSession">Write</a>
  <a href="${pageContext['request'].contextPath}/readSession">readSession</a>
</body>
</html>
