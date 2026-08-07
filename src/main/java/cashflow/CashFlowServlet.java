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
    private static final String DB_PASSWORD = "Cookie1007";

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
            //檢查結束沒早於開始
            if (endMonth.isBefore(startMonth)) {
                request.setAttribute("error", "結束月份不能早於開始月份");
                request.getRequestDispatcher("/cashflow/cashflow.jsp")
                       .forward(request, response);
                return;
            }
            //開始結束日期
            LocalDate startDate = startMonth.atDay(1);
            LocalDate endDateExclusive = endMonth.plusMonths(1).atDay(1);
            
            List<CashFlowRow> rows = new ArrayList<>();

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                BigDecimal beginningCash = getCurrentCash(conn);
                //各預計加總
                Map<String, BigDecimal> monthlyReceivableMap =
                        getMonthlyTotal(conn, "receivable", startDate, endDateExclusive);

                Map<String, BigDecimal> monthlyPayableMap =
                        getMonthlyTotal(conn, "payable", startDate, endDateExclusive);

                Map<LocalDate, BigDecimal> dailyReceivableMap =
                        getDailyTotal(conn, "receivable", startDate, endDateExclusive);

                Map<LocalDate, BigDecimal> dailyPayableMap =
                        getDailyTotal(conn, "payable", startDate, endDateExclusive);
                
                YearMonth currentMonth = startMonth;
                //計算最小金額 應付 應收 現金流 月尾金額
                while (!currentMonth.isAfter(endMonth)) {
                    String monthKey = currentMonth.toString();
                    //加總每"月"應收
                    BigDecimal monthlyReceivable =
                            monthlyReceivableMap.getOrDefault(monthKey, BigDecimal.ZERO);
                    //加總每"月"應付
                    BigDecimal monthlyPayable =
                            monthlyPayableMap.getOrDefault(monthKey, BigDecimal.ZERO);
                    //加總每"日"應收
                    BigDecimal netCashFlow =
                            monthlyReceivable.subtract(monthlyPayable).subtract(fixedExpense);
                    //加總每"日"應付
                    BigDecimal endingCash =
                            beginningCash.add(netCashFlow);
                    //獲得月中最小金額
                    BigDecimal minimumCash =
                            calculateMonthlyMinimumCash(
                                    beginningCash,
                                    currentMonth,
                                    dailyReceivableMap,
                                    dailyPayableMap,
                                    fixedExpense
                            );
                    //存入rows
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
            //傳去jsp
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
    //現金加總
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
    //預計"月"加總(把對應的expected_date欄撈出來後加總)
    private Map<String, BigDecimal> getMonthlyTotal(Connection conn,
                                                    String tableName,
                                                    LocalDate startDate,
                                                    LocalDate endDateExclusive) throws Exception {

        Map<String, BigDecimal> map = new HashMap<>();
        
        String sql =
        		//變成月後開始加總
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
    //預計"日"加總(把對應的expected_date欄撈出來後加總)
    private Map<LocalDate, BigDecimal> getDailyTotal(Connection conn,
                                                     String tableName,
                                                     LocalDate startDate,
                                                     LocalDate endDateExclusive) throws Exception {

        Map<LocalDate, BigDecimal> map = new HashMap<>();

        String sql =
        		//直接加總
                "SELECT expected_date AS date_key, " +
                "       COALESCE(SUM(amount), 0) AS total_amount " +
                "FROM " + tableName + " " +
                "WHERE expected_date >= ? " +
                "  AND expected_date < ? " +
                "GROUP BY expected_date";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDateExclusive));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate dateKey = rs.getDate("date_key").toLocalDate();
                    BigDecimal totalAmount = rs.getBigDecimal("total_amount");

                    map.put(dateKey, totalAmount);
                }
            }
        }

        return map;
    }
    //計算月中最小金額(每天計算+應收-應付)
    private BigDecimal calculateMonthlyMinimumCash(BigDecimal beginningCash,
                                                   YearMonth currentMonth,
                                                   Map<LocalDate, BigDecimal> dailyReceivableMap,
                                                   Map<LocalDate, BigDecimal> dailyPayableMap,
                                                   BigDecimal fixedExpense) {

        LocalDate day = currentMonth.atDay(1);
        LocalDate nextMonthFirstDay = currentMonth.plusMonths(1).atDay(1);

        BigDecimal cash = beginningCash;
        BigDecimal minimumCash = beginningCash;

        boolean fixedExpensePaid = false;

        while (day.isBefore(nextMonthFirstDay)) {

            BigDecimal dailyReceivable =
                    dailyReceivableMap.getOrDefault(day, BigDecimal.ZERO);

            BigDecimal dailyPayable =
                    dailyPayableMap.getOrDefault(day, BigDecimal.ZERO);

            cash = cash.add(dailyReceivable);
            cash = cash.subtract(dailyPayable);

            if (!fixedExpensePaid) {
                cash = cash.subtract(fixedExpense);
                fixedExpensePaid = true;
            }

            if (cash.compareTo(minimumCash) < 0) {
                minimumCash = cash;
            }

            day = day.plusDays(1);
        }

        return minimumCash;
    }
}