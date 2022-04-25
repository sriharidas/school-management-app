package com.bit.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/update-student-mark")
public class UpdateStudentMarks extends HttpServlet {
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			HttpSession session = request.getSession(false);
			PrintWriter out = response.getWriter();
			RequestDispatcher rd = null;
			if(session==null)
			{	
				 rd = request.getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
			}
			ServletContext context = request.getServletContext();
			String driver = context.getInitParameter("driver");
			String db = context.getInitParameter("database");
			String username = context.getInitParameter("username");
			String password = context.getInitParameter("password");
			String query = "";
			
			if(session.getAttribute("role").equals("student"))
			{
				request.setAttribute("message", "Sorry! your're not authorized to visit this page");
				rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}
	
			try {
				
				if(request.getParameter("update_id") != null && request.getParameter("update_mark") != null)
				{
				Integer faculty_id = Integer.parseInt((String)session.getAttribute("id"));
				Integer sub_id = 0;
				Integer stud_id = Integer.parseInt((String)request.getParameter("update_id"));
				Integer mark = Integer.parseInt((String)request.getParameter("update_mark"));
				out.println(stud_id);
		
				Class.forName(driver);
				Connection con = DriverManager.getConnection(db, username, password);
				PreparedStatement stmt = null;
				if(session.getAttribute("role").equals("faculty"))
				{
					query = "select reg.sub_id from faculty \r\n"
						+ "inner join registered_courses as reg on faculty.faculty_id = reg.faculty_id  \r\n"
						+ "where faculty.faculty_id = ? \r\n"
						+ "group by faculty.faculty_id";
				
	
				stmt = con.prepareStatement(query);
				stmt.setInt(1, faculty_id);
				}else if(session.getAttribute("role").equals("admin"))
				{
					query = "select reg.sub_id from faculty \r\n"
							+ "inner join registered_courses as reg on faculty.faculty_id = reg.faculty_id  \r\n"
							+ "group by faculty.faculty_id";
					
		
					stmt = con.prepareStatement(query);
				}
				ResultSet rs = stmt.executeQuery();
				rs.next();
				sub_id = rs.getInt(1);
				
				query = "UPDATE marks set marks = ? where stud_id = ? and sub_id = ?";
				stmt = con.prepareStatement(query);
				stmt.setInt(1, mark);
				stmt.setInt(2, stud_id);
				stmt.setInt(3, sub_id);
				
				Integer rows_affected = stmt.executeUpdate();
				out.print("<p class=\"right-side msg\"> "+rows_affected+" rows affected.</p>");
				out.println("</div>");
				}
			}
			catch(Exception e)
			{
				request.setAttribute("message", "Exception : " + e);
				rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}
		
		}
}
