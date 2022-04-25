<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="style.css" />
</head>
	<body>
		<header>
			<div class="header-left">
				<h2>School Management System</h2>
			</div>
			
			<ul class="header-right">
				
				<% 
					if(session.getAttribute("id") == null)
					{
						out.println("<li><a href=\"login.jsp\">Login</a></li>");
					}
					else
					{
						out.println("<li><a href=\"logout\">Logout</a></li>");
					}
				%>
					
			</ul>
		</header>
	</body>

</html>    