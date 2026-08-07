package payable;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.DBUtil;

@WebServlet("/deletePayable")
public class DeletePayableServlet extends HttpServlet{
	private static final long serialVersionUID=1L;
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		int id =Integer.parseInt(request.getParameter("id"));
	
			try(Connection conn=DBUtil.getConnection()){
				String sql="DELETE FROM receivable WHERE id = ?";
				try(PreparedStatement stmt=conn.prepareStatement(sql)){
					stmt.setInt(1, id);
					stmt.executeUpdate();
				}
				response.sendRedirect(request.getContextPath()+"/payables");
			}catch (Exception e) {
			throw new ServletException("刪除失敗",e);
		}
		
		
	}
	
	
}
