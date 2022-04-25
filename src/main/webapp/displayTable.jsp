<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	
	<%! RequestDispatcher rd = null; %>
	<%	rd = request.getRequestDispatcher("header.jsp");
		rd.include(request, response);
		
		rd = request.getRequestDispatcher("nav.jsp");
		rd.include(request, response);
		
	%>
<div class="main">

	<%@ include file="table.jsp" %>
	
			</div>
</body>
</html>