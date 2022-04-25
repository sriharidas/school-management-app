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

@WebServlet("/view-students-marks")
public class ViewAllStudentsMarks extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException
	{
		HttpSession session = request.getSession(false);
		if(session==null)
		{	

			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		ServletContext context = request.getServletContext();
		String driver = context.getInitParameter("driver");
		String db = context.getInitParameter("database");
		String username = context.getInitParameter("username");
		String password = context.getInitParameter("password");
		String query = "";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(db, username, password);
			PreparedStatement stmt = null;
			if(session.getAttribute("role").equals("student"))
			{
				query = "SELECT sub.sub_id, sub.sub_name, st.stud_id,  st.stud_fname, st.stud_lname,mar.marks from registered_courses  as reg\r\n"
						+ "left join marks as mar\r\n"
						+ "on reg.stud_id = mar.stud_id \r\n"
						+ "and reg.sub_id = mar.sub_id\r\n"
						+ "inner join subject  as sub on reg.sub_id = sub.sub_id\r\n"
						+ "inner join student as st on reg.stud_id = st.stud_id"; 
				stmt = con.prepareStatement(query);
			}
				
			else if(session.getAttribute("role").equals( "faculty")) {
				query ="SELECT sub.sub_id, sub.sub_name, st.stud_id,  st.stud_fname, st.stud_lname,mar.marks, fac.faculty_fname from registered_courses  as reg\r\n" 
						+ "left join marks as mar\r\n"
						+ "on reg.stud_id = mar.stud_id \r\n"
						+ "and reg.sub_id = mar.sub_id\r\n"
						+ "inner join subject  as sub on reg.sub_id = sub.sub_id\r\n"
						+ "inner join student as st on reg.stud_id = st.stud_id\r\n"
						+ "inner join faculty as fac on reg.faculty_id = fac.faculty_id \r\n"
						+"where fac.faculty_id = ?";
				stmt = con.prepareStatement(query);
				stmt.setInt(1, Integer.parseInt((String)session.getAttribute("id")));
			}
			else if(session.getAttribute("role").equals("admin")){
				query ="SELECT sub.sub_id, sub.sub_name, st.stud_id,  st.stud_fname, st.stud_lname,mar.marks, fac.faculty_fname from registered_courses  as reg\r\n" 
						+ "left join marks as mar\r\n"
						+ "on reg.stud_id = mar.stud_id \r\n"
						+ "and reg.sub_id = mar.sub_id\r\n"
						+ "inner join subject  as sub on reg.sub_id = sub.sub_id\r\n"
						+ "inner join student as st on reg.stud_id = st.stud_id\r\n"
						+ "inner join faculty as fac on reg.faculty_id = fac.faculty_id \r\n";
				stmt = con.prepareStatement(query);
			}
			
			ResultSet rs = stmt.executeQuery();
			
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("/viewAllStudentsMarks.jsp");
			request.setAttribute("header", "All Students Marks");
			request.setAttribute("result", rs);
			rd.include(request, response);
//			rd= request.getRequestDispatcher("profile.jsp");
//			rd.include(request, response);
		}catch(Exception s)
		{
			request.setAttribute("message", "Exception : " + s);
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}
		
	}
}
