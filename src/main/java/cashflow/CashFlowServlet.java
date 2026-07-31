package cashflow;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cashflow")
public class CashFlowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/jsp_demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Taipei";
    private static final String DB_USER = "jsp_user";
    private static final String DB_PASSWORD = "你的jsp_user密碼";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/cashflow/cashflow.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String startMonthText = request.getParameter("startMonth");
        String endMonthText = request.getParameter("endMonth");
        String fixedExpenseText = request.getParameter("fixedExpense");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            YearMonth startMonth = YearMonth.parse(startMonthText);
            YearMonth endMonth = YearMonth.parse(endMonthText);
            BigDecimal fixedExpense = new BigDecimal(fixedExpenseText);

            if (endMonth.isBefore(startMonth)) {
                request.setAttribute("error", "結束月份不能早於開始月份");
                request.getRequestDispatcher("/cashflow/cashflow.jsp")
                       .forward(request, response);
                return;
            }

            LocalDate startDate = startMonth.atDay(1);
            LocalDate endDateExclusive = endMonth.plusMonths(1).atDay(1);

            List<CashFlowRow> rows = new ArrayList<>();

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                BigDecimal beginningCash = getCurrentCash(conn);

                Map<String, BigDecimal> receivableMap =
                        getMonthlyTotal(conn, "receivable", startDate, endDateExclusive);

                Map<String, BigDecimal> payableMap =
                        getMonthlyTotal(conn, "payable", startDate, endDateExclusive);

                YearMonth currentMonth = startMonth;

                while (!currentMonth.isAfter(endMonth)) {
                    String monthKey = currentMonth.toString();

                    BigDecimal monthlyReceivable =
                            receivableMap.getOrDefault(monthKey, BigDecimal.ZERO);

                    BigDecimal monthlyPayable =
                            payableMap.getOrDefault(monthKey, BigDecimal.ZERO);

                    BigDecimal netCashFlow =
                            monthlyReceivable.subtract(monthlyPayable).subtract(fixedExpense);

                    BigDecimal endingCash =
                            beginningCash.add(netCashFlow);

                    BigDecimal minimumCash =
                            min(beginningCash, endingCash);

                    rows.add(new CashFlowRow(
                            monthKey,
                            beginningCash,
                            monthlyReceivable,
                            monthlyPayable,
                            fixedExpense,
                            netCashFlow,
                            endingCash,
                            minimumCash
                    ));

                    beginningCash = endingCash;
                    currentMonth = currentMonth.plusMonths(1);
                }
            }

            request.setAttribute("rows", rows);
            request.setAttribute("startMonth", startMonthText);
            request.setAttribute("endMonth", endMonthText);
            request.setAttribute("fixedExpense", fixedExpenseText);

            request.getRequestDispatcher("/cashflow/cashflow.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("產生現金流表失敗", e);
        }
    }

    private BigDecimal getCurrentCash(Connection conn) throws Exception {
        String sql = "SELECT COALESCE(SUM(amount), 0) AS total_cash FROM account";

        try (
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getBigDecimal("total_cash");
            }
        }

        return BigDecimal.ZERO;
    }

    private Map<String, BigDecimal> getMonthlyTotal(Connection conn,
                                                    String tableName,
                                                    LocalDate startDate,
                                                    LocalDate endDateExclusive) throws Exception {

        Map<String, BigDecimal> map = new HashMap<>();

        String sql =
                "SELECT DATE_FORMAT(expected_date, '%Y-%m') AS month_key, " +
                "       COALESCE(SUM(amount), 0) AS total_amount " +
                "FROM " + tableName + " " +
                "WHERE expected_date >= ? " +
                "  AND expected_date < ? " +
                "GROUP BY DATE_FORMAT(expected_date, '%Y-%m')";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDateExclusive));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String monthKey = rs.getString("month_key");
                    BigDecimal totalAmount = rs.getBigDecimal("total_amount");

                    map.put(monthKey, totalAmount);
                }
            }
        }

        return map;
    }

    private BigDecimal min(BigDecimal a, BigDecimal b) {
        if (a.compareTo(b) <= 0) {
            return a;
        } else {
            return b;
        }
    }
}