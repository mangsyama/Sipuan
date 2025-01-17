package com.sipuan.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sipuan.database.DatabaseConnection;
import com.sipuan.models.User;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class HomeView extends JFrame {

    private JLabel saldoLabel;
    private JLabel pendapatanLabel;
    private JLabel pengeluaranLabel;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterDropdown;

    public HomeView() {
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
        mainPanel.setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = createSidebar();
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 255)); // Warna biru untuk header
        headerPanel.setPreferredSize(new Dimension(getWidth(), 260));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Menambahkan jarak atas, bawah, kiri, kanan

        saldoLabel = new JLabel("Saldo Anda: Rp 0", SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Poppins", Font.BOLD, 40));
        saldoLabel.setForeground(Color.WHITE); // Warna teks putih
        saldoLabel.setPreferredSize(new Dimension(getWidth(), 200));

        // Panel untuk Pendapatan dan Pengeluaran
        JPanel summaryPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // GridLayout dengan jarak antar kolom 30px
        summaryPanel.setBackground(new Color(0, 102, 255));

        // Panel untuk Pendapatan
        JPanel pendapatanPanel = new JPanel();
        pendapatanPanel.setLayout(new BorderLayout());
        pendapatanLabel = new JLabel("Pendapatan: Rp 0", SwingConstants.CENTER);

        // Menambahkan ikon pada label Pendapatan dan scaling ikon
        ImageIcon pendapatanIcon = new ImageIcon(getClass().getResource("/resources/pendapatan.png"));
        Image pendapatanImage = pendapatanIcon.getImage();  // Mengambil gambar dari ImageIcon
        Image scaledPendapatanImage = pendapatanImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);  // Mengubah ukuran gambar
        pendapatanLabel.setIcon(new ImageIcon(scaledPendapatanImage));  // Mengatur gambar yang sudah diskalakan ke label
        pendapatanLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        pendapatanLabel.setForeground(Color.GREEN);
        pendapatanLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Menambahkan jarak atas, bawah, kiri, kanan
        pendapatanLabel.setIconTextGap(20);  // Menambahkan jarak antara ikon dan teks
        pendapatanPanel.add(pendapatanLabel, BorderLayout.CENTER);
        pendapatanPanel.setBackground(Color.WHITE);

        // Panel untuk Pengeluaran
        JPanel pengeluaranPanel = new JPanel();
        pengeluaranPanel.setLayout(new BorderLayout());
        pengeluaranLabel = new JLabel("Pengeluaran: Rp 0", SwingConstants.CENTER);

        // Menambahkan ikon pada label Pengeluaran dan scaling ikon
        ImageIcon pengeluaranIcon = new ImageIcon(getClass().getResource("/resources/pengeluaran.png"));
        Image pengeluaranImage = pengeluaranIcon.getImage();  // Mengambil gambar dari ImageIcon
        Image scaledPengeluaranImage = pengeluaranImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);  // Mengubah ukuran gambar
        pengeluaranLabel.setIcon(new ImageIcon(scaledPengeluaranImage));  // Mengatur gambar yang sudah diskalakan ke label
        pengeluaranLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        pengeluaranLabel.setForeground(Color.RED);
        pengeluaranLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Menambahkan jarak atas, bawah, kiri, kanan
        pengeluaranLabel.setIconTextGap(20);  // Menambahkan jarak antara ikon dan teks
        pengeluaranPanel.add(pengeluaranLabel, BorderLayout.CENTER);
        pengeluaranPanel.setBackground(Color.WHITE);

        summaryPanel.add(pendapatanPanel);
        summaryPanel.add(pengeluaranPanel);

        headerPanel.add(saldoLabel, BorderLayout.NORTH);
        headerPanel.add(summaryPanel, BorderLayout.SOUTH);


        // Tabel transaksi
        String[] columnNames = {"Tanggal", "Kategori", "Judul", "Deskripsi", "Jumlah", "Tipe", "Edit", "Hapus"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(tableModel);
        transactionTable.setRowHeight(40); // Lebar kolom tabel
        transactionTable.setFont(new Font("Poppins", Font.PLAIN, 14));
        
        // Mengatur teks tabel berada di tengah
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < transactionTable.getColumnModel().getColumnCount(); i++) {
            transactionTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(transactionTable);
        add(tableScrollPane, BorderLayout.CENTER);
        
        // Renderer dan Editor untuk tombol
        transactionTable.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        transactionTable.getColumn("Hapus").setCellRenderer(new ButtonRenderer());
        transactionTable.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), "edit"));
        transactionTable.getColumn("Hapus").setCellEditor(new ButtonEditor(new JCheckBox(), "hapus"));
        


        // Tambahkan ke mainPanel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        //mainPanel.add(summaryPanel, BorderLayout.CENTER);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Tambahkan sidebar dan mainPanel ke frame
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Properti frame
        setTitle("SIPUAN - Beranda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Load data awal
        loadTransactions();
        updateSummary();

        setTitle("SIPUAN - Beranda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
    

    private Font loadFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf")).deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }

    private void loadTransactions() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT date, category, title, description, amount, transaction_type FROM transactions ORDER BY date DESC";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            tableModel.setRowCount(0);
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getDate("date"),
                    resultSet.getString("category"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    "Rp. " + String.format("%,.0f", resultSet.getDouble("amount")).replace(",", "."),
                    resultSet.getString("transaction_type"),
                    "Edit",
                    "Hapus"
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data transaksi.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateSummary() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String pendapatanSql = "SELECT SUM(amount) FROM transactions WHERE transaction_type = 'Pendapatan'";
            String pengeluaranSql = "SELECT SUM(amount) FROM transactions WHERE transaction_type = 'Pengeluaran'";

            PreparedStatement pendapatanStatement = connection.prepareStatement(pendapatanSql);
            PreparedStatement pengeluaranStatement = connection.prepareStatement(pengeluaranSql);

            ResultSet pendapatanResult = pendapatanStatement.executeQuery();
            ResultSet pengeluaranResult = pengeluaranStatement.executeQuery();

            double pendapatan = pendapatanResult.next() ? pendapatanResult.getDouble(1) : 0;
            double pengeluaran = pengeluaranResult.next() ? pengeluaranResult.getDouble(1) : 0;

            double saldo = pendapatan - pengeluaran;

            saldoLabel.setText("Saldo Anda: Rp. " + String.format("%,.0f", saldo).replace(",", "."));
            pendapatanLabel.setText("Pendapatan: Rp. " + String.format("%,.0f", pendapatan).replace(",", "."));
            pengeluaranLabel.setText("Pengeluaran: Rp. " + String.format("%,.0f", pengeluaran).replace(",", "."));


        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat ringkasan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Poppins", Font.PLAIN, 14));  // Gunakan font Poppins
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? (column == 6 ? "Edit" : "Hapus") : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final String actionType;

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            this.actionType = actionType;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            String defaultText = column == 6 ? "Edit" : "Hapus";
            JButton button = new JButton(value == null ? defaultText : value.toString());
            button.setFont(new Font("Poppins", Font.PLAIN, 14));

            button.addActionListener(e -> {
                String title = (String) table.getValueAt(row, 2);
                String description = (String) table.getValueAt(row, 3);
                String amountString = ((String) table.getValueAt(row, 4)).replace("Rp. ", "").replace(".", "");
                int amount = (int) Double.parseDouble(amountString);

                if (actionType.equals("edit")) {
                    JTextField titleField = new JTextField(title);
                    titleField.setFont(new Font("Poppins", Font.PLAIN, 14));
                    JTextField descriptionField = new JTextField(description);
                    descriptionField.setFont(new Font("Poppins", Font.PLAIN, 14));
                    JTextField amountField = new JTextField(String.valueOf(amount));
                    amountField.setFont(new Font("Poppins", Font.PLAIN, 14));

                    Object[] fields = {
                        "Judul:", titleField,
                        "Deskripsi:", descriptionField,
                        "Jumlah:", amountField
                    };

                    int option = JOptionPane.showConfirmDialog(null, fields, "Edit Transaksi", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try (Connection connection = DatabaseConnection.getConnection()) {
                            String sql = "UPDATE transactions SET title = ?, description = ?, amount = ? WHERE title = ? AND description = ? AND amount = ?";
                            PreparedStatement statement = connection.prepareStatement(sql);
                            statement.setString(1, titleField.getText());
                            statement.setString(2, descriptionField.getText());
                            statement.setInt(3, Integer.parseInt(amountField.getText()));
                            statement.setString(4, title);
                            statement.setString(5, description);
                            statement.setInt(6, amount);
                            statement.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Transaksi berhasil diperbarui.");
                            loadTransactions();
                            updateSummary();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (actionType.equals("hapus")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Hapus transaksi?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try (Connection connection = DatabaseConnection.getConnection()) {
                            String sql = "DELETE FROM transactions WHERE title = ? AND description = ? AND amount = ?";
                            PreparedStatement statement = connection.prepareStatement(sql);
                            statement.setString(1, title);
                            statement.setString(2, description);
                            statement.setInt(3, amount);
                            statement.executeUpdate();
                            

                            JOptionPane.showMessageDialog(null, "Transaksi berhasil dihapus.");
                            loadTransactions();
                            updateSummary();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            return button;
        }
    }

    public static void main(String[] args) {
        new HomeView();
    }

}
