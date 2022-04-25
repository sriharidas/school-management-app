package com.bit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/view-student-report")
public class viewStudentReport extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(false);
		RequestDispatcher rd = null;
		PrintWriter out = response.getWriter();
		if(session == null)
		{
			rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		ServletContext context = request.getServletContext();
		String driver = context.getInitParameter("driver");
		String db = context.getInitParameter("database");
		String username = context.getInitParameter("username");
		String password = context.getInitParameter("password");
		
		String role = (String) session.getAttribute("role");
		Integer id = Integer.parseInt((String)session.getAttribute("id"));
		String query = "" ;
		
		rd = request.getRequestDispatcher("/header.jsp");
		rd.include(request, response);
		rd = request.getRequestDispatcher("/nav.jsp");
		rd.include(request, response);
		
		List<String> queries = new ArrayList<>();
		List<String> headers = new ArrayList<>();
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(db, username, password);
			PreparedStatement stmt = null;
			out.println("<div class=\"main\">");
			if(role.equals("student"))
			{
				headers.add("Student Marks");
				query = "SELECT * FROM marks INNER JOIN subject on marks.sub_id = subject.sub_id "
						+ "INNER JOIN student on student.stud_id = marks.stud_id "
						+ "where marks.stud_id = ?";
				queries.add(query);
				
				headers.add("Student Average Mark");
				query = "select st.stud_fname,  avg(m.marks) from registered_courses as reg\r\n"
						+ "inner join student as st on st.stud_id = reg.stud_id\r\n"
						+ "inner join marks as m on m.sub_id = reg.sub_id and m.stud_id = reg.stud_id \r\n"
						+ "where st.stud_id = ? \r\n"
						+ "group by st.stud_id";
				queries.add(query);
				
				headers.add("Student Backlog(s)");
				query = "select st.stud_fname,  count(m.marks) from registered_courses as reg\r\n"
						+ "inner join student as st on st.stud_id = reg.stud_id\r\n"
						+ "inner join marks as m on m.sub_id = reg.sub_id and m.stud_id = reg.stud_id \r\n"
						+ "where st.stud_id = ? and  m.marks < 45 \r\n"
						+ "group by st.stud_id";
				queries.add(query);
				
				for(int i=0; i<queries.size(); i++ )
				{
					stmt = con.prepareStatement(queries.get(i));
					stmt.setInt(1, id);
					ResultSet res = stmt.executeQuery();
					rd = request.getRequestDispatcher("/table.jsp");
					request.setAttribute("result", res);
					request.setAttribute("header", headers.get(i));
					rd.include(request, response);
				}
			}
			else if(role.equals("faculty"))
			{
				headers.add("Total Students");
				query = "SELECT count(student.stud_id) as Students from  registered_courses as reg inner join\r\n"
						+ "marks on marks.sub_id = reg.sub_id and marks.stud_id = reg.stud_id\r\n"
						+ "INNER JOIN subject on marks.sub_id = subject.sub_id \r\n"
						+ "INNER JOIN student on student.stud_id = marks.stud_id \r\n"
						+ "inner join faculty on faculty.faculty_id = reg.faculty_id\r\n"
						+ "where faculty.faculty_id = ?\r\n"
						+ "group by faculty.faculty_id";
				
				queries.add(query);
				headers.add("Student Marks");
				query = "SELECT student.stud_id, student.stud_fname, subject.sub_id ,subject.sub_name,  marks.marks FROM registered_courses as reg inner join\r\n"
						+ "marks on marks.sub_id = reg.sub_id and marks.stud_id = reg.stud_id\r\n"
						+ "INNER JOIN subject on marks.sub_id = subject.sub_id \r\n"
						+ "INNER JOIN student on student.stud_id = marks.stud_id \r\n"
						+ "inner join faculty on faculty.faculty_id = reg.faculty_id\r\n"
						+ "where faculty.faculty_id = ?\r\n"
						+ "group by student.stud_id";
				
				queries.add(query);
				
				headers.add("Student Average Mark");
				query ="select st.stud_fname,  avg(m.marks), f.faculty_fname  from registered_courses as reg\r\n"
						+ "inner join student as st on st.stud_id = reg.stud_id\r\n"
						+ "inner join marks as m on m.sub_id = reg.sub_id and m.stud_id = reg.stud_id \r\n"
						+ "inner join faculty as f on f.faculty_id = reg.faculty_id\r\n"
						+ "where f.faculty_id = ?\r\n"
						+ "group by st.stud_id;";
				queries.add(query);
				
				headers.add("Student Backlog(s)");
				query = "select st.stud_fname,  count(m.marks), f.faculty_fname from registered_courses as reg\r\n"
						+ "inner join student as st on st.stud_id = reg.stud_id\r\n"
						+ "inner join marks as m on m.sub_id = reg.sub_id and m.stud_id = reg.stud_id \r\n"
						+ "inner join faculty as f on f.faculty_id = reg.faculty_id\r\n"
						+ "where  marks<45 and f.faculty_id = ?\r\n"
						+ "group by st.stud_id;";
				queries.add(query);
				
				for(int i=0; i<queries.size(); i++ )
				{
					stmt = con.prepareStatement(queries.get(i));
					stmt.setInt(1, id);
					ResultSet res = stmt.executeQuery();
					rd = request.getRequestDispatcher("/table.jsp");
					request.setAttribute("result", res);
					request.setAttribute("header", headers.get(i));
					rd.include(request, response);
				}
			}			
			out.println("</div>");
		}
		catch(Exception e)
		{
			rd = request.getRequestDispatcher("/error.jsp");
			request.setAttribute("message", e.getMessage());
			rd.forward(request, response);
		}
	}

}
