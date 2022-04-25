<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.sql.ResultSet, java.sql.ResultSetMetaData" %>
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
			else{
				try{
					ResultSet rs = (ResultSet)request.getAttribute("result");
					ResultSetMetaData meta = rs.getMetaData();
					out.print("<h2 class=\"table-title\">");
					out.print(request.getAttribute("header"));
					out.print("</h2>");
					if(rs.next())
					{	
						
						out.println("<table>");
						out.println("<tr>");
						for(int i=1; i<=meta.getColumnCount(); i++)
						{
							out.println("<th>" + meta.getColumnName(i)+"</th>");
						}
						out.println("</tr>");
						
						do
						{
							
							out.println("<tr>");
							out.println("<form ><input type=\"text\"  value= \""
									+ rs.getObject("stud_id")+"\"/><input type= \"submit\" value=\"edit\" /></form>");
							for(int i=1; i<=meta.getColumnCount(); i++)
							{
								out.println("<td>" + rs.getObject(i)+"</td>");
							}
							
							out.println("</tr>");
							

							
						}while(rs.next());
						out.println("</table>");
					}
					else
						out.println("<p class=\"table-msg\">No records found</p>");
				}
				catch(Exception e)
				{
					RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
					request.setAttribute("message", e.getMessage());
					rd.include(request, response);
				}
			}
			
		%>
</body>
</html>