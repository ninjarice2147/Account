package account;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/jsp_demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Taipei";

    private static final String DB_USER = "jsp_user";
    private static final String DB_PASSWORD = "Cookie1007";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Account> accounts = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                String sql = "SELECT acc_id, bank, acc_name, amount, update_date FROM account ORDER BY acc_id";

                try (
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()
                ) {
                    while (rs.next()) {
                        int accId = rs.getInt("acc_id");
                        String bank = rs.getString("bank");
                        String accName = rs.getString("acc_name");
                        BigDecimal amount = rs.getBigDecimal("amount");
                        String updateDate = rs.getString("update_date");

                        accounts.add(new Account(accId, bank, accName, amount, updateDate));
                    }
                }
            }

            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("/account/account.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("讀取帳戶資料失敗", e);
        }
    }
}