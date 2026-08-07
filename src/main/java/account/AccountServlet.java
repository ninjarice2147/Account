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
import util.DBUtil;

@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Account> accounts = new ArrayList<>();

            try (Connection conn =DBUtil.getConnection()) {

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
            

            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("/account/account.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("讀取帳戶資料失敗", e);
        }
    }
}