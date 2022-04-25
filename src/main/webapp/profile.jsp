<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	String id = (String)session.getAttribute("id");
	String role = (String)session.getAttribute("role");
	if( id == null || role == null)
	{
		RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
		rd.forward(request, response);
	}
	else
	{
		out.println(session.getAttribute("id") + "'s profile");
	}
	%>
	<% 
	/*	RequestDispatcher rd1 = request.getRequestDispatcher("/view-student");
		 rd1.include(request, response);
		  rd1 = request.getRequestDispatcher("/view-student-mark");
		 rd1.include(request, response);*/
	%>
</body>
</html>