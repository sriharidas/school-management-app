package com.bit.servlet;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/update-student-mark")
public class updateStudentMarkFIlter extends HttpFilter {
     
	FilterConfig config = null;
	
	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		RequestDispatcher rd = null;
		PrintWriter out = response.getWriter();
		
		ServletContext context = config.getServletContext();
		String driver = context.getInitParameter("driver");
		String db = context.getInitParameter("database");
		String username = context.getInitParameter("username");
		String password = context.getInitParameter("password");
		String query = "";

		if(session == null)
		{
			rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		else if(!session.getAttribute("role").equals("student"))
		{
			try {
				 Integer id = Integer.parseInt((String)session.getAttribute("id"));
				Class.forName(driver);
				Connection con = DriverManager.getConnection(db, username, password);
				PreparedStatement stmt = null;
				if(session.getAttribute("role").equals("faculty"))
				{query = "select student.stud_id, student.stud_fname from registered_courses as reg "
						+ "inner join student on reg.stud_id = student.stud_id "
						+ "where reg.faculty_id = ?";
				stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				
				}
				else if(session.getAttribute("role").equals("admin")){
					query = "select student.stud_id, student.stud_fname from registered_courses as reg "
							+ "inner join student on reg.stud_id = student.stud_id " + 
							"group by student.stud_id";
					stmt = con.prepareStatement(query);

				}
				ResultSet list = stmt.executeQuery();
				
			
				rd = request.getRequestDispatcher("header.jsp");
				rd.include(request, response);
				
				rd = request.getRequestDispatcher("nav.jsp");
				rd.include(request, response);
				
				out.println("<div class=\"main\">");
				
				rd = request.getRequestDispatcher("/editField.jsp");
				request.setAttribute("student_list", list);
				request.setAttribute("url", "update-student-mark");
				rd.include(request, response);
				if(session.getAttribute("role").equals("faculty"))
				{
					query = "SELECT student.stud_id, student.stud_fname, subject.sub_name, marks.marks FROM registered_courses as reg \r\n"
						+ "inner join subject on subject.sub_id = reg.sub_id \r\n"
						+ "inner join  marks  on marks.sub_id = reg.sub_id and marks.stud_id = reg.stud_id\r\n"
						+ "INNER JOIN student on student.stud_id = reg.stud_id \r\n"
						+ "inner join faculty on faculty.faculty_id = reg.faculty_id\r\n"
						+ "where faculty.faculty_id = ?";
					stmt = con.prepareStatement(query);
					stmt.setInt(1, id);
				}
				else if (session.getAttribute("role").equals("admin"))
				{
					query = "SELECT student.stud_id, student.stud_fname, subject.sub_name, marks.marks FROM registered_courses as reg \r\n"
							+ "inner join subject on subject.sub_id = reg.sub_id \r\n"
							+ "inner join  marks  on marks.sub_id = reg.sub_id and marks.stud_id = reg.stud_id\r\n"
							+ "INNER JOIN student on student.stud_id = reg.stud_id \r\n"
							+ "inner join faculty on faculty.faculty_id = reg.faculty_id\r\n" 
							+ "group by student.stud_id";
					stmt = con.prepareStatement(query);

				}
				
				ResultSet marks = stmt.executeQuery();
				rd= request.getRequestDispatcher("/table.jsp");
				request.setAttribute("header", "Student Marks");
				request.setAttribute("result", marks);
				rd.include(request, response);
				
				out.println("</div>");
				
				
				 chain.doFilter(request, response);
			}catch(Exception e)
			{
				rd = request.getRequestDispatcher("/error.jsp");
				request.setAttribute("message", e);
				rd.forward(request, response);
			}
		
		}
		else {
			rd = request.getRequestDispatcher("/nonAuthorized.jsp");
			rd.forward(request, response);
		}
	}


	

}
