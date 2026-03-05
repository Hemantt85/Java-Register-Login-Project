package in.sp.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/loginForm")
public class Login extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter out=resp.getWriter();
		
		String myemail=req.getParameter("email1");
		String mypass=req.getParameter("pass1");
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/yt_demo", "root", "hemant@85");
			
			PreparedStatement ps=con.prepareStatement("select * from register where email=? and password=?");
			ps.setString(1, myemail);
			ps.setString(2, mypass);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				
				HttpSession session=req.getSession();
				session.setAttribute("session_name", rs.getString("name"));
				
				resp.setContentType("text/html");
				RequestDispatcher rd=req.getRequestDispatcher("/profile.jsp");
				rd.include(req, resp);
			}
			else
			{
				resp.setContentType("text/html");
				out.println("<h3 style='color:red'>Login Failed</h3>");
				
				RequestDispatcher rd=req.getRequestDispatcher("/login.jsp");
				rd.include(req, resp);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			resp.setContentType("text/html");
			out.println("<h3 style='color:red'>An error occurred. Please try again later.</h3>");
			
			RequestDispatcher rd=req.getRequestDispatcher("/login.jsp");
			rd.include(req, resp);
		}
		
	}

}
