package payable;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.DBUtil;

@WebServlet("/addPayable")
public class AddPayableServlet extends HttpServlet{
	private static final long serialVersionUID=1L;
	
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) 
	throws ServletException,IOException{
		request.getRequestDispatcher("/payable/addPayable.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		
		String vendorName=request.getParameter("vendorName");
		String title=request.getParameter("title");
		BigDecimal amount=new BigDecimal(request.getParameter("amount"));
		String expectedDate=request.getParameter("expectedDate");
		String status=request.getParameter("status");
		String note=request.getParameter("note");
		
		
			try(Connection conn=DBUtil.getConnection()){
				String sql="INSERT INTO payable " +
			             "(vendor_name, title, amount, expected_date, status, note) " +
			             "VALUES (?, ?, ?, ?, ?, ?)";
				try(PreparedStatement stmt=conn.prepareStatement(sql)){
					stmt.setString(1,vendorName);
					stmt.setString(2,title);
					stmt.setBigDecimal(3, amount);
					stmt.setString(4, expectedDate);
					stmt.setString(5,status);
					stmt.setString(6,note);
					stmt.executeUpdate();
				}
				response.sendRedirect(request.getContextPath()+"/payables");
			}catch (Exception e) {
			throw new ServletException("新增失敗",e);
		}
		
		
	}
	
	
}
