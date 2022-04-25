<%@page import="java.sql.ResultSet"%>
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
	<%	/*rd = request.getRequestDispatcher("header.jsp");
		rd.include(request, response);
		
		rd = request.getRequestDispatcher("nav.jsp");
		rd.include(request, response);
		*/
	%>

	<form class="form-field" action = ${url}  >
		
		<select name="delete_id">
			<%
				ResultSet rs = (ResultSet)request.getAttribute("list");
				if(rs.next())
				{
					do{
						out.println("<option value = "+ rs.getInt(1) +">" + rs.getObject(1) + " - " + rs.getObject(2)+ "</option>");
					}while(rs.next());
				}
				else
				{
					out.println("<p class=\"table-msg\">No records found</p>");
				}
				
			%>
		</select>

		<input type="submit" value="delete" />
	</form>

	
</body>
</html>