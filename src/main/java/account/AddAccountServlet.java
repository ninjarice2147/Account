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

@WebServlet("/addAccount")
public class AddAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/account/addAccount.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String bank = request.getParameter("bank");
        String accName = request.getParameter("accName");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));

            try (Connection conn =DBUtil.getConnection()) {

                String sql = "INSERT INTO account (bank, acc_name, amount) VALUES (?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, bank);
                    stmt.setString(2, accName);
                    stmt.setBigDecimal(3, amount);

                    stmt.executeUpdate();
                }
            

            response.sendRedirect(request.getContextPath() + "/accounts");

        } catch (Exception e) {
            throw new ServletException("新增帳戶資料失敗", e);
        }
    }
}