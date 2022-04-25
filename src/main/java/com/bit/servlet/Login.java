package com.bit.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		PrintWriter out = response.getWriter();
		ServletContext context = request.getServletContext();
		
		String driver = context.getInitParameter("driver");
		String db = context.getInitParameter("database");
		String username = context.getInitParameter("username");
		String password = context.getInitParameter("password");
		String query = "";
		String inp_email = request.getParameter("email");
		String inp_password = request.getParameter("password");

		
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(db, username, password);
			query = "SELECT id, email, CAST(AES_DECRYPT(password, \"a\") AS CHAR) AS password, role FROM accounts where email = ?";  
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, inp_email);
			ResultSet rs = stmt.executeQuery();

			if(rs.next())
			{
				if(rs.getString(3).equals(inp_password)) {
				 out.println("Welcome " + request.getParameter("email"));
					
					HttpSession session =  request.getSession();
					session.setAttribute("id", rs.getString("id"));
					session.setAttribute("email", rs.getString("email"));
					session.setAttribute("role", rs.getString("role"));
		
					
					RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
					rd.forward(request, response);
				}
				else
				{
					out.print("Incorrect password");
				}
			}
			else {

				out.println("Account not found");
			}
				
		}catch(Exception c)
		{
			request.setAttribute("message", "Exception : " + c);
			RequestDispatcher rd = request.getRequestDispatcher(request.getContextPath()+"/error.jsp");
			rd.forward(request, response);
		}
	}
}
