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

@WebServlet("/remove-faculty")
public class RemoveFaculty extends HttpServlet {

 
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		RequestDispatcher rd = null;
		PrintWriter out = response.getWriter();
		
		ServletContext context = request.getServletContext();
		String driver = context.getInitParameter("driver");
		String db = context.getInitParameter("database");
		String username = context.getInitParameter("username");
		String password = context.getInitParameter("password");
		String query = "";
		
		try
		{
			if(request.getParameter("delete_id") != null)
			{
				Integer id = Integer.parseInt((String)request.getParameter("delete_id"));
				
				Class.forName(driver);
				Connection con = DriverManager.getConnection(db, username, password);
				query = "DELETE FROM faculty where faculty_id = ?";
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				Integer rows_affected = stmt.executeUpdate();
				out.print("<p class=\"right-side msg\"> "+rows_affected+" rows affected.</p>");
				out.println("</div>");
			}
		}
		catch(Exception e)
		{
			rd = request.getRequestDispatcher("/error.jsp");
			request.setAttribute("message", e);
			rd.forward(request, response);
		}
	}


}
