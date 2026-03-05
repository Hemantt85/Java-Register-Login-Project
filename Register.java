package in.sp.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;


import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/regForm")
public class Register extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter out=resp.getWriter();
		
		String myname=req.getParameter("name1");
		String myemail=req.getParameter("email1");
		String mypass=req.getParameter("pass1");
		String mygender=req.getParameter("gender1");
		String mycity=req.getParameter("city1");
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/yt_demo", "root", "hemant@85");
			
			PreparedStatement ps=con.prepareStatement("insert into register (name,email,password,gender,city) values (?, ?, ?, ?, ?)");
			ps.setString(1, myname);
			ps.setString(2, myemail);
			ps.setString(3, mypass);
			ps.setString(4, mygender);
			ps.setString(5, mycity);
			
			int count=ps.executeUpdate();
			if(count>0)
			{
				resp.setContentType("text/html");
				out.println("<h3 style='color:green'>Registration Successfull</h3>");
				
				RequestDispatcher rd=req.getRequestDispatcher("/register.jsp");
				rd.include(req, resp);
			}
			else
			{
				resp.setContentType("text/html");
				out.println("<h3 style='color:red'>Registration Failed</h3>");
				
				RequestDispatcher rd=req.getRequestDispatcher("/register.jsp");
				rd.include(req, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			resp.setContentType("text/html");
			out.println("<h3 style='color:red'>Exception Occured: "+e.getMessage()+"</h3>");
			
			RequestDispatcher rd=req.getRequestDispatcher("/register.jsp");
			rd.include(req, resp);
		}
	}

}