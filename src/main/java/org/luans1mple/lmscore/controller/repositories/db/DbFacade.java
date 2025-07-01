package org.luans1mple.lmscore.controller.repositories.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbFacade {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=lms_cpl;encrypt=true;trustServerCertificate=true";
        return DriverManager.getConnection(url, "sa", "123");
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Kết nối thành công tới SQL Server!");
        } catch (SQLException e) {
            System.out.println("❌ Kết nối thất bại:");
            e.printStackTrace();
        }
    }
}
