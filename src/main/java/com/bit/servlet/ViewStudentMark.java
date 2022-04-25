package com.bit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/view-student-mark")
public class ViewStudentMark extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException
	{
		HttpSession session = request.getSession(false);
		if(session==null)
		{	

			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		ServletContext context = request.getServletContext();
		PrintWriter out = response.getWriter();
		String driver = context.getInitParameter("driver");
		String db = context.getInitParameter("database");
		String username = context.getInitParameter("username");
		String password = context.getInitParameter("password");
		String query = "";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(db, username, password);
			query = "SELECT student.stud_id, student.stud_fname, student.stud_lname, subject.sub_name, marks.marks "
					+ "  FROM marks INNER JOIN subject on marks.sub_id = subject.sub_id "
					+ "INNER JOIN student on student.stud_id = marks.stud_id "
					+ "where marks.stud_id = ?";
			
//			PreparedStatement stmt = con.prepareStatement(query);
//			stmt.setInt(1, Integer.parseInt((String)session.getAttribute("id")));
//			ResultSet rs = stmt.executeQuery();
			PreparedStatement stmt = null;
			if(session.getAttribute("role").equals("student"))
			{
				  
				stmt = con.prepareStatement(query);
				stmt.setInt(1, Integer.parseInt((String)session.getAttribute("id")));
				ResultSet rs = stmt.executeQuery();
				
				RequestDispatcher rd = request.getRequestDispatcher("/viewStudentMark.jsp");
				request.setAttribute("header", "Student Mark");
				request.setAttribute("result", rs);
				rd.forward(request, response);
			} 
			else if(session.getAttribute("role").equals("faculty") || session.getAttribute("role").equals("admin"))
			{	
				
				if(request.getParameter("search_id") != null) { 
					Integer search_id = Integer.parseInt((String)request.getParameter("search_id"));
					stmt = con.prepareStatement(query);
					stmt.setInt(1,search_id) ;
					ResultSet rs = stmt.executeQuery();
					//out.print("<div class=\"right-side\">");
					RequestDispatcher rd = request.getRequestDispatcher("/table.jsp");
					request.setAttribute("header", "Student Mark");
					request.setAttribute("result", rs);
					rd.include(request, response);
					out.println("</div>");
				}
			}
//			RequestDispatcher rd = request.getRequestDispatcher("/viewStudentMark.jsp");
//			request.setAttribute("result", rs);
//			rd.forward(request, response);
			
		}catch(Exception s)
		{
			request.setAttribute("message", "Exception : " + s);
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}
		
	}
}
