package com.sipuan.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12758102"; // Ganti "localhost" atau "3306" jika berbeda
    private static final String USER = "sql12758102"; // Ganti dengan username database Anda
    private static final String PASSWORD = "qQT7e5WgTD"; // Ganti dengan password database Anda

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Koneksi ke database berhasil!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Koneksi ke database gagal!");
        }
    }
}
