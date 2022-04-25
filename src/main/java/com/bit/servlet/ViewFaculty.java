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

@WebServlet("/view-faculty")
public class ViewFaculty extends HttpServlet {
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
			query = "SELECT * FROM faculty where faculty_id = ?";  
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, Integer.parseInt((String)session.getAttribute("id")));
			ResultSet rs = stmt.executeQuery();
			
			RequestDispatcher rd = request.getRequestDispatcher("viewFaculty.jsp");
			request.setAttribute("header", "Faculty");
			request.setAttribute("result", rs);
			rd.forward(request, response);
			
		}catch(Exception s)
		{
			request.setAttribute("message", "Exception : " + s);
			RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
			rd.forward(request, response);
		}
		
	}
}
