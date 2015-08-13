<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>Log In</title>
</head>
<body>
	<h1>Log In</h1>
	<p>Your session id is <%= request.getSession().getId() %> </p>
	<form action="./j_spring_security_check" method="post">
		<p>
			<label for="j_username"> Username</label> <input type="text"
				name="j_username" id="j_username" value="user" />
		</p>
		<p>
			<label for="j_password"> Password</label> <input type="password"
				name="j_password" id="j_password" value="password" />
		</p>
		<input type="submit" value="Log In"/>
	</form>
</body>
</html>
