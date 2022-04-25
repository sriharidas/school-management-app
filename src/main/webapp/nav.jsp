<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div class="menu">
	
	<h3>Menu</h3>

	<ul >
		<% 
			String role = (String)session.getAttribute("role");
				if(role!=null)
				{
					if(role.equals("student"))
					{
						out.println("<li><a href = \"view-student\">view personal Info</a> </li>");
						out.println("<li><a href = \"view-student-mark\">view marks</a> </li>");
						out.println("<li><a href = \"view-subject\">view subjects</a> </li>");
						out.println("<li><a href = \"view-student-report\">view student report</a> </li>");
					}
					else if(role.equals("faculty"))
					{
						out.println("<li><a href = \"view-faculty\">view personal Info</a> </li>");
						out.println("<li><a href = \"view-student\">view student</a> </li>");
						out.println("<li><a href = \"view-students\">view students </a></li>");
						out.println("<li><a href = \"view-students-marks\">view students marks </a> </li>");
						out.println("<li><a href = \"view-student-mark\">view student mark</a> </li>");
						out.println("<li><a href = \"view-student-report\">view faculty report</a> </li>");
						out.println("<li><a href = \"update-student-mark\">update student mark</a> </li>");
						//out.println("<li><a href = \"add-student\">add student</a> </li>");
						//out.println("<li><a href = \"add-faculty\">add faculty</a> </li>");
						//out.println("<li><a href = \"remove-student\">remove student</a> </li>");
						//out.println("<li><a href = \"remove-faculty\">remove faculty</a> </li>");
					}
					else
					{
						out.println("<li><a href = \"view-student\">view student</a> </li>");
						out.println("<li><a href = \"view-students\">view students </a></li>");
						out.println("<li><a href = \"view-students-marks\">view students marks </a> </li>");
						out.println("<li><a href = \"view-student-mark\">view student mark</a> </li>");
						out.println("<li><a href = \"update-student-mark\">update student mark</a> </li>");
						out.println("<li><a href = \"add-student\">add student</a> </li>");
						out.println("<li><a href = \"add-faculty\">add faculty</a> </li>");
						out.println("<li><a href = \"remove-student\">remove student</a> </li>");
						out.println("<li><a href = \"remove-faculty\">remove faculty</a> </li>");
					}
				}
			%>
	</ul>
		</div>
</body>
</html>