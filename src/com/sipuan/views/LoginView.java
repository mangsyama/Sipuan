package com.sipuan.views;

import javax.swing.*;
import java.awt.*;
import com.sipuan.database.UserCRUD;
import com.sipuan.models.User;
import java.sql.SQLException;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel registerLabel;

    public LoginView() {
        // Set fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set layout
        setLayout(new BorderLayout());
        
        // Set ikon aplikasi dengan ukuran sesuai
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/logo.png"));
            Image scaledIcon = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH); // Sesuaikan ukuran di sini
            setIconImage(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ikon aplikasi tidak ditemukan!");
        }

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE); // Warna latar belakang aplikasi
        mainPanel.setLayout(new GridBagLayout());

        // Panel form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(240, 240, 240)); // Warna latar belakang form
        formPanel.setPreferredSize(new Dimension(600, 500)); // Ukuran diperbesar untuk skala lebih besar
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Border form

        // GridBagConstraints untuk form
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Judul
        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 32)); // Font lebih besar
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Input Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        usernameField = new JTextField();
        usernameField.setFont(new Font("Poppins", Font.PLAIN, 20));
        usernameField.setPreferredSize(new Dimension(300, 40));

        // Input Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 20));
        passwordField.setPreferredSize(new Dimension(300, 40));

        // Tombol Login
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Poppins", Font.BOLD, 20));
        loginButton.setBackground(new Color(0, 102, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 50));

        // Label Register
        registerLabel = new JLabel("Belum memiliki akun? Register!");
        registerLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        registerLabel.setForeground(new Color(0, 102, 255));
        registerLabel.setHorizontalAlignment(JLabel.CENTER);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Tambahkan komponen ke formPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);

        gbc.gridy++;
        formPanel.add(registerLabel, gbc);

        // Tambahkan formPanel ke mainPanel
        mainPanel.add(formPanel);

        // Tambahkan mainPanel ke frame
        add(mainPanel, BorderLayout.CENTER);

        // Aksi tombol login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Harap isi semua kolom!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    UserCRUD userCRUD = new UserCRUD();
                    User user = userCRUD.loginUser(username, password); // Simpan hasil login ke variabel User

                    if (user != null) { // Jika login berhasil
                        JOptionPane.showMessageDialog(this, "Login berhasil!");
                        dispose(); // Tutup halaman login
                        
                        HomeView homeView = new HomeView(); // Buka halaman beranda dengan parameter User
                        homeView.setVisible(true); // Pastikan HomeView ditampilkan
                    } else {
                        JOptionPane.showMessageDialog(this, "Username atau password salah!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengakses database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Aksi label register
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new RegisterView(); // Ganti dengan view register
            }
        });

        // Properti frame
        setTitle("SIPUAN - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Fungsi untuk memuat font Poppins
    private Font loadFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf")).deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Poppins", Font.PLAIN, (int) size);
        }
    }

    public static void main(String[] args) {
        new LoginView();
    }
}
