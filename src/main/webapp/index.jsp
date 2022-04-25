<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
	<head>
		<title>SchoolManagement System</title>
	</head>
	<body>
	<%! RequestDispatcher rd = null; %>
	<% 
			String id = (String)session.getAttribute("id");
			String role = (String)session.getAttribute("role");
			String email = (String)session.getAttribute("email");
			if( id == null || role == null)
			{	
				response.sendRedirect("login.jsp");
			}
			
	%>

	<%	rd = request.getRequestDispatcher("header.jsp");
		rd.include(request, response);
		
		rd = request.getRequestDispatcher("nav.jsp");
		rd.include(request, response);
	%>
	<div class="main">
		<p class="right">
		<h3 class="right">Welcome <%= email %></h3>
		<h4 class="right"><%= role %></h4>
		</p>
	
	</div>
		</body>
</html>