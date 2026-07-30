package payable;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addPayable")
public class AddPayableServlet extends HttpServlet{
	private static final long serialVersion=1L;
	private static final String DB_URL=
			"jdbc:mysql://localhost:3306/jsp_demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Taipei";
	private static final String DB_USER="jsp_user";
	private static final String DB_PASSWORD="Cookie1007";
	
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) 
	throws ServletException,IOException{
		request.getRequestDispatcher("/payable/addPayable.jsp").forward(request, response);
	}
			
	
}
