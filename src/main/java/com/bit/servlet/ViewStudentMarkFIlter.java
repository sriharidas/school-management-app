package com.bit.servlet;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/view-student-mark")
public class ViewStudentMarkFIlter extends HttpFilter {
       
	public void destroy() {
	}


	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		RequestDispatcher rd = null;
		PrintWriter out = response.getWriter();
		if(session == null)
		{
			rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		else if(session.getAttribute("role").equals("student"))
		{
			chain.doFilter(request, response);
		}
		else
		{
			rd = request.getRequestDispatcher("header.jsp");
			rd.include(request, response);
			
			rd = request.getRequestDispatcher("nav.jsp");
			rd.include(request, response);
			
			out.println("<div class=\"main\" >");
			rd = request.getRequestDispatcher("/searchField.jsp");
			request.setAttribute("search_url", "view-student-mark");
			rd.include(request, response);
			
			chain.doFilter(request, response);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
