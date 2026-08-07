package receivable;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.DBUtil;

@WebServlet("/receivables")
public class ReceivableServlet extends HttpServlet{
	private static final long serialVersionUID=1L;
	
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		
		List<Receivable> receivables=new ArrayList<>();
		
			try(Connection conn=DBUtil.getConnection()){
				
				String sql = 
	"SELECT id, customer_name, title, amount, expected_date, status, note, created_at, updated_at FROM receivable ORDER BY id";
				try(PreparedStatement stmt=conn.prepareStatement(sql);
					ResultSet rs=stmt.executeQuery()){
					while(rs.next()) {
						int id=rs.getInt("id");
						String customerName=rs.getString("customer_name");
						String title=rs.getString("title");
						BigDecimal amount=rs.getBigDecimal("amount");
						String expectedDate=rs.getString("expected_date");
						String status=rs.getString("status");
						String note=rs.getString("note");
						String createdAt=rs.getString("created_at");
						String updatedAt=rs.getString("updated_at");
						receivables.add(new Receivable(id, customerName, title, amount, expectedDate, status, note, createdAt, updatedAt));
					}
					request.setAttribute("receivables",receivables);
					request.getRequestDispatcher("/receivable/receivable.jsp").forward(request, response);
					
				}
				
			}
			catch (Exception e) {
			throw new ServletException("讀取帳戶失敗",e);
		}
		
	}
}
