package payable;

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

@WebServlet("/payables")
public class PayableServlet extends HttpServlet {
	private static final long serialVersionUID=1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
		List<Payable> payables=new ArrayList<>();
		
			try(Connection conn=DBUtil.getConnection()){
				String sql="SELECT id, vendor_name, title, amount, expected_date, status, note, created_at, updated_at FROM payable ORDER BY id";
				try(PreparedStatement stmt =conn.prepareStatement(sql);
						ResultSet rs=stmt.executeQuery()){
					while(rs.next()) {
						int id=rs.getInt("id");
						String vendorName=rs.getString("vendor_name");
						String title=rs.getString("title");
						BigDecimal amount=rs.getBigDecimal("amount");
						String expectedDate=rs.getString("expected_date");
						String status=rs.getString("status");
						String note=rs.getString("note");
						String createdAt=rs.getString("created_at");
						String updatedAt=rs.getString("updated_at");
						payables.add(new Payable(id, vendorName, title, amount, expectedDate, status, note, createdAt, updatedAt));
					}
					request.setAttribute("payables", payables);
					request.getRequestDispatcher("payable/payable.jsp").forward(request, response);
				}
			}
		catch (Exception e) {
			throw new ServletException("資料讀取失敗",e);
		}
		
		
		
	}
}
