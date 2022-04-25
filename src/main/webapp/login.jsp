<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="style.css" />
<title>Insert title here</title>
</head>
<body>

	<% 
		if(session.getAttribute("id") != null)
		{
			response.sendRedirect("index.jsp");
		}
	%>
	<div class="login-form-container">
	
	<form action="login"  method="post">
	<h3>Login</h3>
		<input type="email" name="email" placeholder="email address"/>
		<input type="password" name="password" placeholder="password"/>
		<input type="submit" />
	</form>
	</div>
	
</body>
</html>