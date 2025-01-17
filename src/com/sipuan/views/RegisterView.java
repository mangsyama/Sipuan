package com.sipuan.views;

import javax.swing.*;
import java.awt.*;
import com.sipuan.database.UserCRUD;
import com.sipuan.models.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegisterView extends JFrame {

    private JTextField usernameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton;
    private JLabel loginLabel;

    public RegisterView() {
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
        formPanel.setPreferredSize(new Dimension(600, 500));
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Border form
        

        // GridBagConstraints untuk form
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Judul
        JLabel titleLabel = new JLabel("REGISTER");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Input Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        usernameField = new JTextField();
        usernameField.setFont(new Font("Poppins", Font.PLAIN, 20));
        usernameField.setPreferredSize(new Dimension(300, 40));

        // Input Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        emailField = new JTextField();
        emailField.setFont(new Font("Poppins", Font.PLAIN, 20));
        emailField.setPreferredSize(new Dimension(300, 40));

        // Input Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 20));
        passwordField.setPreferredSize(new Dimension(300, 40));

        // Input Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Konfirmasi Password");
        confirmPasswordLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Poppins", Font.PLAIN, 20));
        confirmPasswordField.setPreferredSize(new Dimension(300, 40));

        // Tombol Register
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Poppins", Font.BOLD, 20));
        registerButton.setBackground(new Color(0, 102, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(200, 50));

        // Label Login
        loginLabel = new JLabel("Sudah memiliki akun? Login!");
        loginLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        loginLabel.setForeground(new Color(0, 102, 255));
        loginLabel.setHorizontalAlignment(JLabel.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        formPanel.add(registerButton, gbc);

        gbc.gridy++;
        formPanel.add(loginLabel, gbc);

        // Tambahkan formPanel ke mainPanel
        mainPanel.add(formPanel);

        // Tambahkan mainPanel ke frame
        add(mainPanel, BorderLayout.CENTER);

        // Aksi tombol register
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Harap isi semua kolom!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Password dan konfirmasi password tidak sama!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Membuat objek User dan memanggil method registerUser
                User user = new User(username, email, password);
                UserCRUD userCRUD = null;
                try {
                    userCRUD = new UserCRUD();
                } catch (SQLException ex) {
                    Logger.getLogger(RegisterView.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (userCRUD.registerUser(user)) {
                        JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan login.");
                        dispose();
                        new LoginView();
                    } else {
                        JOptionPane.showMessageDialog(this, "Gagal registrasi! Username atau email mungkin sudah digunakan.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan pada database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Aksi label login
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new LoginView();
            }
        });

        // Properti frame
        setTitle("SIPUAN - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Fungsi untuk memuat font Poppins
    private Font loadFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf")).deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }

    public static void main(String[] args) {
        new RegisterView();
    }
}
