package com.bit.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/view-subject")

public class ViewAllStudentSubjects extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		HttpSession session = request.getSession(false);
		if(session == null)
		{	

			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		
		String role = (String) session.getAttribute("role");
		String id = (String) session.getAttribute("id");
		ServletContext context = request.getServletContext();
		try {
			Class.forName(context.getInitParameter("driver"));
			String query = "";
			Connection con = DriverManager.getConnection(context.getInitParameter("database"), context.getInitParameter("username"), context.getInitParameter("password"));
			PreparedStatement stmt = null;
			if(role.equals("student"))
			{
				query = "select sub.sub_id, sub.sub_name, st.stud_id, st.stud_fname \r\n"
						+ "from registered_courses as reg\r\n"
						+ "inner join student as st on st.stud_id = reg.stud_id\r\n"
						+ "inner join subject as sub on sub.sub_id = reg.sub_id\r\n"
						+ "where st.stud_id = ?";
				stmt= con.prepareStatement(query);
				stmt.setInt(1, Integer.parseInt(id));
			}
			else
			{
				query = "select sub.sub_id, sub.sub_name, st.stud_id, st.stud_fname, fac.faculty_id, fac.faculty_fname \r\n"
						+ "from registered_courses as reg\r\n"
						+ "inner join student as st on st.stud_id = reg.stud_id\r\n"
						+ "inner join subject as sub on sub.sub_id = reg.sub_id\r\n"
						+ "inner join faculty as fac on fac.faculty_id = reg.faculty_id\r\n"
						+ "where fac.faculty_id = ?";
				stmt= con.prepareStatement(query);
				stmt.setInt(1, Integer.parseInt(id));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			RequestDispatcher rd = request.getRequestDispatcher("/displayTable.jsp");
			request.setAttribute("header", "All Student Subjects");
			request.setAttribute("result", rs);
			rd.include(request, response);
		}
		catch(Exception e)
		{
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			request.setAttribute("message", e.getMessage());
			rd.forward(request, response);
		}
	}
}
