package account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/jsp_demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Taipei";

    private static final String DB_USER = "jsp_user";
    private static final String DB_PASSWORD = "Cookie1007";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int accId = Integer.parseInt(request.getParameter("accId"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                String sql = "DELETE FROM account WHERE acc_id = ?";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, accId);

                    stmt.executeUpdate();
                }
            }

            response.sendRedirect(request.getContextPath() + "/accounts");

        } catch (Exception e) {
            throw new ServletException("刪除帳戶資料失敗", e);
        }
    }
}