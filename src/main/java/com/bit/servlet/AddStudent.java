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

@WebServlet("/add-student")
public class AddStudent extends HttpServlet {

       
   
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
			if(request.getParameter("fname") != null)
			{
				String fname = (String)request.getParameter("fname");
			
				String lname = (String)request.getParameter("lname");
				Integer age = Integer.parseInt((String)request.getParameter("age"));
				String department = (String)request.getParameter("department");
				String gender = (String)request.getParameter("gender");
				String email = (String)request.getParameter("email");
				
				Class.forName(driver);
				Connection con = DriverManager.getConnection(db, username, password);
				query = "INSERT INTO student (stud_fname, stud_lname, age, department, gender, email)  VALUES (?,?,?,?,?,?)";
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, fname);
				stmt.setString(2, lname);
				stmt.setInt(3, age);
				stmt.setString(4, department);
				stmt.setString(5, gender);
				stmt.setString(6, email);
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
