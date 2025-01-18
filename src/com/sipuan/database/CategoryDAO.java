package com.sipuan.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/sipuan";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public CategoryDAO() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Menambahkan kategori baru
    public boolean addCategory(String namaKategori, String jenis) {
        String sql = "INSERT INTO kategori (nama_kategori, jenis) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, namaKategori);
            stmt.setString(2, jenis);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mengambil kategori berdasarkan jenis (pengeluaran/pendapatan)
    public List<String[]> getCategories(String jenis) {
        List<String[]> categories = new ArrayList<>();
        String sql = "SELECT * FROM kategori WHERE jenis = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, jenis);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String[] category = new String[2];
                category[0] = String.valueOf(rs.getInt("id"));
                category[1] = rs.getString("nama_kategori");
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Mengupdate kategori berdasarkan nama
    public boolean updateCategory(String oldName, String newName, String jenis) {
        String sql = "UPDATE kategori SET nama_kategori = ? WHERE nama_kategori = ? AND jenis = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, oldName);
            stmt.setString(3, jenis);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Menghapus kategori berdasarkan nama
    public boolean deleteCategory(String namaKategori, String jenis) {
        String sql = "DELETE FROM kategori WHERE nama_kategori = ? AND jenis = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, namaKategori);
            stmt.setString(2, jenis);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
