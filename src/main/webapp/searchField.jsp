<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<% String url = (String)request.getAttribute("search_url"); %>
	<%! RequestDispatcher rd = null; %>
	<%	/* rd = request.getRequestDispatcher("header.jsp");
		rd.include(request, response);
		
		rd = request.getRequestDispatcher("nav.jsp");
		rd.include(request, response);*/
		
	%>
	<div>
	<form class="form-field" action =  ${url}  >
		<input placeholder="Enter the id"  name="search_id" />
		<input type="submit" value="search" />
	</form>
	</div>
	
</body>
</html>