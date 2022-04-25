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

@WebServlet("/view-student")
public class ViewStudent extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException
	{
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
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
			query = "SELECT * FROM student where stud_id = ?";
			if(session.getAttribute("role").equals("student"))
			{
				RequestDispatcher rd = null;

				  
				stmt = con.prepareStatement(query);
				stmt.setInt(1, Integer.parseInt((String)session.getAttribute("id")));
				ResultSet rs = stmt.executeQuery();
				
				rd = request.getRequestDispatcher("header.jsp");
				rd.include(request, response);
				
				rd = request.getRequestDispatcher("nav.jsp");
				rd.include(request, response);
				out.print("<div class=\"main\">");
				 rd = request.getRequestDispatcher("/table.jsp");
				request.setAttribute("header", "Student");
				request.setAttribute("result", rs);
				rd.include(request, response);
				out.print("</div>");
			} else if(session.getAttribute("role").equals("faculty") || session.getAttribute("role").equals("admin"))
			{	
				
				if(request.getParameter("search_id") != null) { 
					Integer search_id = Integer.parseInt((String)request.getParameter("search_id"));
					stmt = con.prepareStatement(query);
					stmt.setInt(1,search_id);
					ResultSet rs = stmt.executeQuery();
				//	out.print("<div class=\"right-side top\">");
					RequestDispatcher rd = request.getRequestDispatcher("/table.jsp");
					request.setAttribute("header", "Student");
					request.setAttribute("result", rs);
					rd.include(request, response);
					out.print("</div>");
					
				}
			}
			
			
			
		}catch(Exception s)
		{
			request.setAttribute("message", "Exception : " + s);
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}
		
	}
}
