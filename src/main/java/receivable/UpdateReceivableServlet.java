package receivable;

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

@WebServlet("/updateReceivable")
public class UpdateReceivableServlet extends HttpServlet {
	private static final long serialVersionUID=1L;
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		int id=Integer.parseInt(request.getParameter("id"));
		String customerName =request.getParameter("customerName");
		String title=request.getParameter("title");
		BigDecimal amount=new BigDecimal(request.getParameter("amount"));
		String expectedDate=request.getParameter("expectedDate");
		String status=request.getParameter("status");
		String note=request.getParameter("note");
		
			try(Connection conn=DBUtil.getConnection()){
				String sql =
					"UPDATE receivable SET customer_name = ?, title = ?, amount = ?, expected_date = ?, status = ?, note = ? WHERE id = ?";
				try(PreparedStatement stmt=conn.prepareStatement(sql)){
					stmt.setString(1,customerName);
					stmt.setString(2,title);
					stmt.setBigDecimal(3, amount);
					stmt.setString(4, expectedDate);
					stmt.setString(5,status);
					stmt.setString(6,note);
					stmt.setInt(7, id);
					stmt.executeUpdate();
				}
				response.sendRedirect(request.getContextPath()+"/receivables");
			}
			catch (Exception e) {
			throw new ServletException("新增錯誤",e);
		}
		
		
	}
	
}
