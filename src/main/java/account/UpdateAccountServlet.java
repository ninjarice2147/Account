package account;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.DBUtil;

@WebServlet("/updateAccount")
public class UpdateAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int accId = Integer.parseInt(request.getParameter("accId"));
        String bank = request.getParameter("bank");
        String accName = request.getParameter("accName");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));

            try (Connection conn = DBUtil.getConnection()) {

                String sql = "UPDATE account SET bank = ?, acc_name = ?, amount = ? WHERE acc_id = ?";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, bank);
                    stmt.setString(2, accName);
                    stmt.setBigDecimal(3, amount);
                    stmt.setInt(4, accId);

                    stmt.executeUpdate();
                }
                response.sendRedirect(request.getContextPath() + "/accounts");
            }catch (Exception e) {
            throw new ServletException("更新帳戶資料失敗", e);
        }
    }
}