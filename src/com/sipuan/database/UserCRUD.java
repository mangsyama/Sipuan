package com.sipuan.database;

import com.sipuan.models.User;
import java.sql.*;

public class UserCRUD {

    private Connection conn;

    // Constructor untuk koneksi ke database
    public UserCRUD() throws SQLException {
        conn = DatabaseConnection.getConnection();
    }

    // Fungsi untuk registrasi user baru
    public boolean registerUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        }
    }

    // Fungsi untuk login user
    public User loginUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        }
        return null;
    }
}
