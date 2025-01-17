package com.sipuan.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sipuan.database.DatabaseConnection;
import com.sipuan.models.User;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyEvent;

public class CreateFormView extends JFrame {

    private JComboBox<String> categoryDropdown;
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField amountField;
    private JDateChooser dateChooser;
    private JButton submitButton;
    private JRadioButton pendapatanButton;
    private JRadioButton pengeluaranButton;
    private ButtonGroup transactionTypeGroup;

    public CreateFormView() {
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
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setLayout(new BorderLayout());

        // Sidebar (menggunakan sidebar yang sudah diberikan)
        JPanel sidebar = createSidebar();

        // Panel Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Jenis Transaksi
        JLabel transactionTypeLabel = new JLabel("Jenis Transaksi");
        transactionTypeLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(transactionTypeLabel, gbc);

        pendapatanButton = new JRadioButton("Pendapatan");
        pengeluaranButton = new JRadioButton("Pengeluaran");
        transactionTypeGroup = new ButtonGroup();
        transactionTypeGroup.add(pendapatanButton);
        transactionTypeGroup.add(pengeluaranButton);
        pendapatanButton.setFont(new Font("Poppins", Font.PLAIN, 20));
        pengeluaranButton.setFont(new Font("Poppins", Font.PLAIN, 20));

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(pendapatanButton);
        radioPanel.add(pengeluaranButton);

        gbc.gridx = 1;
        formPanel.add(radioPanel, gbc);

        // Pilih Kategori (hanya muncul setelah memilih jenis transaksi)
        JLabel categoryLabel = new JLabel("Pilih Kategori");
        categoryLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(categoryLabel, gbc);

        categoryDropdown = new JComboBox<>();
        categoryDropdown.setFont(new Font("Poppins", Font.PLAIN, 20));
        categoryDropdown.addItem("Pilih Kategori");  // Menambahkan teks default
        categoryDropdown.setEnabled(false);  // Menonaktifkan dropdown awalnya
        gbc.gridx = 1;
        formPanel.add(categoryDropdown, gbc);

        // Input Judul
        JLabel titleLabel = new JLabel("Judul");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(titleLabel, gbc);

        titleField = new JTextField();
        titleField.setFont(new Font("Poppins", Font.PLAIN, 20));
        titleField.setPreferredSize(new Dimension(200, 35)); // Ukuran konsisten
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);


        // Input Deskripsi
        JLabel descriptionLabel = new JLabel("Deskripsi");
        descriptionLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(descriptionLabel, gbc);

        descriptionField = new JTextField();
        descriptionField.setFont(new Font("Poppins", Font.PLAIN, 20));
        descriptionField.setPreferredSize(new Dimension(200, 35)); // Ukuran konsisten
        gbc.gridx = 1;
        formPanel.add(descriptionField, gbc);


        // Input Jumlah Uang dengan validasi angka
        JLabel amountLabel = new JLabel("Jumlah");
        amountLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(amountLabel, gbc);

        amountField = new JTextField();
        amountField.setFont(new Font("Poppins", Font.PLAIN, 20));
        amountField.setPreferredSize(new Dimension(200, 35)); // Ukuran konsisten
        amountField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == '.')) {
                    evt.consume();  // Mengabaikan input selain angka
                }
            }
        });
        gbc.gridx = 1;
        formPanel.add(amountField, gbc);

        // Input Tanggal
        JLabel dateLabel = new JLabel("Tanggal");
        dateLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(dateLabel, gbc);

        dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Poppins", Font.PLAIN, 20));
        // Mengatur ukuran preferred size agar sesuai dengan JTextField
        dateChooser.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        formPanel.add(dateChooser, gbc);


        // Tombol Submit
        submitButton = new JButton("Buat");
        submitButton.setFont(new Font("Poppins", Font.BOLD, 20));
        submitButton.setBackground(new Color(0, 102, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(submitButton, gbc);

        // Aksi tombol submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });

        // Tambahkan panel ke mainPanel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Tambahkan mainPanel ke frame
        add(mainPanel);

        // Properti frame
        setTitle("SIPUAN - Tambah Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Load kategori untuk Pendapatan pertama kali
        loadCategories("Pendapatan");

        // Menangani perubahan jenis transaksi
        pendapatanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryDropdown.setEnabled(true);  // Menampilkan dropdown setelah memilih jenis transaksi
                loadCategories("Pendapatan");
            }
        });
        pengeluaranButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryDropdown.setEnabled(true);  // Menampilkan dropdown setelah memilih jenis transaksi
                loadCategories("Pengeluaran");
            }
        });
    }

    private void loadCategories(String jenis) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query untuk mengambil kategori berdasarkan jenis
            String sql = "SELECT nama_kategori FROM kategori WHERE jenis = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, jenis);  // Menetapkan jenis kategori (Pendapatan atau Pengeluaran)

            ResultSet resultSet = statement.executeQuery();

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Pilih Kategori");  // Menambahkan teks default untuk dropdown
            while (resultSet.next()) {
                model.addElement(resultSet.getString("nama_kategori"));
            }
            categoryDropdown.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat kategori.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitForm() {
        String category = (String) categoryDropdown.getSelectedItem();
        String title = titleField.getText();
        String description = descriptionField.getText();
        String amount = amountField.getText();
        java.util.Date selectedDate = dateChooser.getDate();

        // Mendapatkan jenis transaksi
        String transactionType = pendapatanButton.isSelected() ? "Pendapatan" : "Pengeluaran";

        if (category.equals("Pilih Kategori") || title.isEmpty() || description.isEmpty() || amount.isEmpty() || selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Harap isi semua kolom!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO transactions (category, title, description, amount, date, transaction_type) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setDouble(4, Double.parseDouble(amount));
            statement.setDate(5, new java.sql.Date(selectedDate.getTime()));
            statement.setString(6, transactionType);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Reset form
            titleField.setText("");
            descriptionField.setText("");
            amountField.setText("");
            dateChooser.setDate(null);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menambahkan data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, getHeight())); // Lebar sidebar ditingkatkan
        sidebar.setBackground(new Color(240, 240, 240));
        sidebar.setLayout(new GridBagLayout()); // Menggunakan GridBagLayout untuk kontrol lebih baik

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10); // Memberikan padding antar elemen
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0; // Set posisi kolom ke 0

        // Menambahkan teks "SIPUAN" di bagian atas sidebar
        JLabel titleLabel = new JLabel("SIPUAN");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 30)); // Menetapkan font
        titleLabel.setForeground(new Color(0, 102, 255)); // Warna biru
        gbc.gridy = 0; // Set posisi baris ke 0
        sidebar.add(titleLabel, gbc); // Menambahkan titleLabel di posisi pertama

        gbc.gridy++; // Geser ke baris berikutnya untuk tombol

        // Memuat gambar ikon untuk tombol dan menyesuaikan ukurannya
        ImageIcon homeIcon = new ImageIcon(getClass().getResource("/resources/home.png"));
        homeIcon = new ImageIcon(homeIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

        ImageIcon analysisIcon = new ImageIcon(getClass().getResource("/resources/analisis.png"));
        analysisIcon = new ImageIcon(analysisIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

        ImageIcon categoryIcon = new ImageIcon(getClass().getResource("/resources/kategori.png"));
        categoryIcon = new ImageIcon(categoryIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

        ImageIcon addIcon = new ImageIcon(getClass().getResource("/resources/tambah.png"));
        addIcon = new ImageIcon(addIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

        ImageIcon reportIcon = new ImageIcon(getClass().getResource("/resources/laporan.png"));
        reportIcon = new ImageIcon(reportIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

        // Tombol navigasi dengan ikon
        JButton homeButton = createSidebarButton("     Beranda");
        homeButton.setIcon(homeIcon); // Menambahkan ikon pada tombol Beranda

        JButton analysisButton = createSidebarButton("     Analisis");
        analysisButton.setIcon(analysisIcon); // Menambahkan ikon pada tombol Analisis

        JButton categoryButton = createSidebarButton("      Kategori");
        categoryButton.setIcon(categoryIcon); // Menambahkan ikon pada tombol Kategori

        JButton addButton = createSidebarButton("     Tambah");
        addButton.setIcon(addIcon); // Menambahkan ikon pada tombol Tambah

        JButton reportButton = createSidebarButton("     Laporan");
        reportButton.setIcon(reportIcon); // Menambahkan ikon pada tombol Laporan

        // ActionListener untuk tombol Beranda
        homeButton.addActionListener(e -> {
            HomeView homeView = new HomeView();
            homeView.setVisible(true);
            this.dispose(); // Menutup tampilan yang sedang aktif
        });

        // ActionListener untuk tombol Analisis
        analysisButton.addActionListener(e -> {
            AnalysisView analysisView = new AnalysisView();
            analysisView.setVisible(true);
            this.dispose(); // Menutup tampilan yang sedang aktif
        });

        // ActionListener untuk tombol Kategori
        categoryButton.addActionListener(e -> {
            CategoryView categoryView = new CategoryView();
            categoryView.setVisible(true);
            this.dispose(); // Menutup tampilan yang sedang aktif
        });

        // ActionListener untuk tombol Tambah
        addButton.addActionListener(e -> {
            CreateFormView createFormView = new CreateFormView();
            createFormView.setVisible(true);
            this.dispose(); // Menutup tampilan yang sedang aktif
        });

        // ActionListener untuk tombol Laporan
        reportButton.addActionListener(e -> {
            ReportView reportView = new ReportView();
            reportView.setVisible(true);
            this.dispose(); // Menutup tampilan yang sedang aktif
        });

        // Menambahkan tombol navigasi ke sidebar
        sidebar.add(homeButton, gbc);
        gbc.gridy++;
        sidebar.add(analysisButton, gbc);
        gbc.gridy++;
        sidebar.add(categoryButton, gbc);
        gbc.gridy++;
        sidebar.add(addButton, gbc);
        gbc.gridy++;
        sidebar.add(reportButton, gbc); // Menambahkan tombol Laporan

        return sidebar;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Poppins", Font.PLAIN, 20));  // Gunakan font Poppins
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(220, 65)); // Lebar tombol disesuaikan
        button.setBackground(Color.WHITE);
        return button;
    }

    public static void main(String[] args) {
        new CreateFormView();
    }
}
