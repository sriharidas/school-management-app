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

@WebFilter("/remove-faculty")
public class RemoveFacultyFIlter extends HttpFilter {
     
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
				query = "select faculty_id, faculty_fname from faculty";
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet list = stmt.executeQuery();
				
				rd = request.getRequestDispatcher("header.jsp");
				rd.include(request, response);
				
				rd = request.getRequestDispatcher("nav.jsp");
				rd.include(request, response);
				
				out.println("<div class=\"main\">");
				
				rd = request.getRequestDispatcher("/deleteField.jsp");
				request.setAttribute("list", list);
				request.setAttribute("url", "remove-faculty");
				rd.include(request, response);
				
				query = "SELECT * FROM faculty";
				stmt = con.prepareStatement(query);
				ResultSet fac = stmt.executeQuery();
				rd= request.getRequestDispatcher("/table.jsp");
				request.setAttribute("header", "Faculties");
				request.setAttribute("result", fac);
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
