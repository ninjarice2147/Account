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

@WebServlet("/addAccount")
public class AddAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/jsp_demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Taipei";

    private static final String DB_USER = "jsp_user";
    private static final String DB_PASSWORD = "你的jsp_user密碼";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/addAccount.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String bank = request.getParameter("bank");
        String accName = request.getParameter("accName");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                String sql = "INSERT INTO account (bank, acc_name, amount) VALUES (?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, bank);
                    stmt.setString(2, accName);
                    stmt.setBigDecimal(3, amount);

                    stmt.executeUpdate();
                }
            }

            response.sendRedirect(request.getContextPath() + "/accounts");

        } catch (Exception e) {
            throw new ServletException("新增帳戶資料失敗", e);
        }
    }
}