<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<p>
		<p>Your session id is <%= request.getSession().getId() %> </p>

		<a
			href="${pageContext['request'].contextPath}/j_spring_security_logout">Logout</a>
</body>
</html>
