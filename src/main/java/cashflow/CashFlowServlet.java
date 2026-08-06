package cashflow;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cashflowf.CashFlowRow;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/cashflow")
public class CashFlowServlet extends HttpServlet{
	private static final long serialVersionUID=1L;
	private static String DB_URL=
		"jdbc:mysql://localhost:3306/jsp_demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Taipei";
	private static String DB_USER="jsp_user";
	private static String DB_PASSWORD="Cookie1007";
	//現金加總
	private BigDecimal getCurrentCash(Connection conn) throws Exception{
		String sql="SELECT COALESCE(SUM(amount), 0) AS total_cash FROM account";
		
		try(PreparedStatement stmt=conn.prepareStatement(sql);
				ResultSet rs=stmt.executeQuery()){
			if(rs.next()) {
				return rs.getBigDecimal("total_cash");
			}
			return BigDecimal.ZERO;
		}
	}
	//預計"月"加總(把對應的expected_date欄撈出來後加總)
	private Map<String,BigDecimal> getMonthlyTotal(Connection conn,String tableName,LocalDate startDate,LocalDate endDateExclusive)
	throws Exception{
		Map<String,BigDecimal> map=new HashMap<>();
		
		
		
		
		
	}
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) 
	throws ServletException,IOException{
		request.getRequestDispatcher("/cashflow/cashflow.jsp")
		.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) 
	throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		
		String starMonthText=request.getParameter("startMonth");
		String endMonthText=request.getParameter("endMonth");
		String fixedExpenseText=request.getParameter("fixedExpense");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			YearMonth starMonth=YearMonth.parse(starMonthText);
			YearMonth endMonth=YearMonth.parse(endMonthText);
			YearMonth fixedExpense=YearMonth.parse(fixedExpenseText);
			if(endMonth.isBefore(starMonth)) {
				request.setAttribute("error","結束月份不能早於開始月份");
				request.getRequestDispatcher("/cashflowf/cashflow.jsp").forward(request, response);
				return;
			}
			LocalDate starDate=starMonth.atDay(1);
			LocalDate endDate=endMonth.plusMonths(1).atDay(1);
			
			List<CashFlowRow> rows = new ArrayList<>();
			
			
			try(Connection conn=DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
				String sql="";
				
			}

			
		}catch (Exception e) {
			throw  new ServletException("現金流資料產生失敗",e);
		}
	}
	
}
