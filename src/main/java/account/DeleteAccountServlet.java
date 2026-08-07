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
import util.DBUtil;

@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int accId = Integer.parseInt(request.getParameter("accId"));

            try (Connection conn =DBUtil.getConnection()) {

                String sql = "DELETE FROM account WHERE acc_id = ?";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, accId);

                    stmt.executeUpdate();
                }
                response.sendRedirect(request.getContextPath() + "/accounts");
            }catch (Exception e) {
            throw new ServletException("刪除帳戶資料失敗", e);
        }
    }
}