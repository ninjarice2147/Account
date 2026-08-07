package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/jsp_demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Taipei";

    private static final String DB_USER = "jsp_user";

    private static final String DB_PASSWORD = System.getenv("JSP_DB_PASSWORD");

    public static Connection getConnection() throws Exception {
        if (DB_PASSWORD == null || DB_PASSWORD.isBlank()) {
            throw new Exception("環境變數 JSP_DB_PASSWORD 尚未設定");
        }

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}