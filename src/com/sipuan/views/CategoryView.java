package com.sipuan.views;

import com.sipuan.database.CategoryDAO;
import com.sipuan.models.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.TableColumn;

public class CategoryView extends JFrame {
    private JPanel mainPanel, sidebarPanel, contentPanel, formPanel, expensePanel, incomePanel;
    private JTable expenseTable, incomeTable;
    private JTextField categoryField;
    private JComboBox<String> typeComboBox;
    private DefaultTableModel expenseTableModel, incomeTableModel;

    public CategoryView() {
        setTitle("SIPUAN - Kategori");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set global font size
        setUIFont(new javax.swing.plaf.FontUIResource("Poppins", Font.PLAIN, 16));
        
        // Set ikon aplikasi dengan ukuran sesuai
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/logo.png"));
            Image scaledIcon = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH); // Sesuaikan ukuran di sini
            setIconImage(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ikon aplikasi tidak ditemukan!");
        }

        // Layout utama
        mainPanel = new JPanel(new BorderLayout());
        sidebarPanel = createSidebar();
        contentPanel = new JPanel(new BorderLayout());

        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Form tambah kategori
        formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20)); // Mengatur jarak antar komponen di form dan antara atas/bawah

        // Label Nama Kategori
        JLabel categoryLabel = new JLabel("Tambah Kategori:");
        categoryLabel.setFont(new Font("Poppins", Font.BOLD, 24)); // Mengatur ukuran font
        categoryLabel.setForeground(Color.WHITE); // Mengatur warna teks menjadi putih
        categoryField = new JTextField(15);
        categoryField.setFont(new Font("Poppins", Font.PLAIN, 20)); // Mengatur ukuran font
        //categoryField.setForeground(Color.WHITE); // Mengatur warna teks menjadi putih

        // Menambahkan jarak antara Nama dan Jenis
        JLabel typeLabel = new JLabel("Jenis:");
        typeLabel.setFont(new Font("Poppins", Font.BOLD, 24)); // Mengatur ukuran font
        typeLabel.setForeground(Color.WHITE); // Mengatur warna teks menjadi putih

        typeComboBox = new JComboBox<>(new String[]{"Pengeluaran", "Pendapatan"});
        typeComboBox.setFont(new Font("Poppins", Font.PLAIN, 20)); // Mengatur ukuran font
        //typeComboBox.setForeground(Color.WHITE); // Mengatur warna teks menjadi putih

        // Tombol Tambah Kategori
        JButton addButton = new JButton("Submit");
        addButton.setFont(new Font("Poppins", Font.BOLD, 20)); // Mengatur ukuran font
        //addButton.setBackground(Color.WHITE); // Mengatur warna latar belakang tombol menjadi hijau
        addButton.setForeground(Color.BLUE); // Mengatur warna teks tombol menjadi putih
        addButton.setFocusPainted(false); // Menghapus border ketika tombol difokuskan (optional)
        addButton.setBorderPainted(false); // Menghapus border tombol (optional)

        formPanel.setBackground(new Color(0, 102, 255)); // Mengatur background formPanel menjadi biru
        //formPanel.setPreferredSize(new Dimension(getWidth(), 200)); // Menambahkan tinggi form jika diperlukan

        // Menambahkan komponen ke form
        formPanel.add(categoryLabel);
        formPanel.add(categoryField);
        formPanel.add(Box.createVerticalStrut(20)); // Menambahkan jarak vertikal antara Nama dan Jenis
        formPanel.add(typeLabel);
        formPanel.add(typeComboBox);
        formPanel.add(addButton);

        // Panel tabel kategori pengeluaran
        expensePanel = new JPanel(new BorderLayout());
        expenseTableModel = new DefaultTableModel(new Object[]{"Nama Kategori", "Edit", "Hapus"}, 0);
        expenseTable = new JTable(expenseTableModel);
        expenseTable.setRowHeight(30); // Set row height
        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);
        expensePanel.add(new JLabel("Kategori Pengeluaran"), BorderLayout.NORTH);
        expensePanel.add(expenseScrollPane, BorderLayout.CENTER);
        expensePanel.setBackground(Color.WHITE); // Contoh: Putih

        // Membuat JLabel untuk judul dan menambahkan padding di sekitar teks
        JLabel expenseLabel = new JLabel("Kategori Pengeluaran");
        expenseLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Menyelaraskan teks ke tengah
        expenseLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));  // Menambahkan padding di atas dan bawah
        expenseLabel.setFont(new Font("Poppins", Font.PLAIN, 24));

        expensePanel.add(expenseLabel, BorderLayout.NORTH);
        expensePanel.add(expenseScrollPane, BorderLayout.CENTER);
        expensePanel.setBackground(Color.WHITE); // Contoh: Putih

        // Panel tabel kategori pendapatan
        incomePanel = new JPanel(new BorderLayout());
        incomeTableModel = new DefaultTableModel(new Object[]{"Nama Kategori", "Edit", "Hapus"}, 0);
        incomeTable = new JTable(incomeTableModel);
        incomeTable.setRowHeight(30); // Set row height
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        incomePanel.add(new JLabel("Kategori Pendapatan"), BorderLayout.NORTH);
        incomePanel.add(incomeScrollPane, BorderLayout.CENTER);
        incomePanel.setBackground(Color.WHITE); // Contoh: Putih

        // Membuat JLabel untuk judul dan menambahkan padding di sekitar teks
        JLabel incomeLabel = new JLabel("Kategori Pendapatan");
        incomeLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Menyelaraskan teks ke tengah
        incomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));  // Menambahkan padding di atas dan bawah
        incomeLabel.setFont(new Font("Poppins", Font.PLAIN, 24));

        incomePanel.add(incomeLabel, BorderLayout.NORTH);
        incomePanel.add(incomeScrollPane, BorderLayout.CENTER);
        incomePanel.setBackground(Color.WHITE); // Contoh: Putih


        // Tambahkan jarak antara panel pendapatan dan pengeluaran
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.X_AXIS)); // Mengatur agar keduanya tersusun secara horizontal
        tablesPanel.add(incomePanel); // Menambahkan panel pendapatan terlebih dahulu

        // Menambahkan jarak antar panel (misalnya 10 piksel)
        tablesPanel.add(Box.createHorizontalStrut(20));  // Jarak horizontal antara kedua panel
        tablesPanel.setBackground(Color.WHITE); // Contoh: Putih

        // Menambahkan padding di kiri dan kanan panel utama
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20)); // Menambahkan border kosong (padding) di kiri-kanan (20px) dan atas-bawah (10px)

        tablesPanel.add(expensePanel); // Menambahkan panel pengeluaran setelah pendapatan


        // Tambahkan panel tabel dan form ke konten utama
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(tablesPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Custom renderer untuk tombol Edit dan Hapus
        expenseTable.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        expenseTable.getColumn("Hapus").setCellRenderer(new ButtonRenderer());
        incomeTable.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        incomeTable.getColumn("Hapus").setCellRenderer(new ButtonRenderer());

        // Custom editor untuk tombol Edit dan Hapus
        expenseTable.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), "edit", "pengeluaran"));
        expenseTable.getColumn("Hapus").setCellEditor(new ButtonEditor(new JCheckBox(), "hapus", "pengeluaran"));
        incomeTable.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), "edit", "pendapatan"));
        incomeTable.getColumn("Hapus").setCellEditor(new ButtonEditor(new JCheckBox(), "hapus", "pendapatan"));

        // Load data kategori dari database
        loadCategories();
        

        // Event untuk tombol tambah kategori
        addButton.addActionListener(e -> {
            String namaKategori = categoryField.getText().trim();
            String jenis = typeComboBox.getSelectedItem().toString().toLowerCase();

            if (!namaKategori.isEmpty()) {
                CategoryDAO categoryDAO = new CategoryDAO();
                if (categoryDAO.addCategory(namaKategori, jenis)) {
                    loadCategories(); // Perbarui data kategori
                    categoryField.setText(""); // Kosongkan field
                    JOptionPane.showMessageDialog(this, "Kategori berhasil ditambahkan.");
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambah kategori.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nama kategori tidak boleh kosong.");
            }
        });
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

    void loadCategories() {
        CategoryDAO categoryDAO = new CategoryDAO();

        // Kosongkan tabel sebelum diisi ulang
        expenseTableModel.setRowCount(0);
        incomeTableModel.setRowCount(0);

        // Load data kategori pengeluaran
        for (String[] row : categoryDAO.getCategories("pengeluaran")) {
            expenseTableModel.addRow(new Object[]{row[1], "Edit", "Hapus"});
        }

        // Load data kategori pendapatan
        for (String[] row : categoryDAO.getCategories("pendapatan")) {
            incomeTableModel.addRow(new Object[]{row[1], "Edit", "Hapus"});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CategoryView().setVisible(true);
        });
    }

    // Renderer untuk tombol
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor untuk tombol
    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private String actionType;
        private String categoryType;

        public ButtonEditor(JCheckBox checkBox, String actionType, String categoryType) {
            super(checkBox);
            this.actionType = actionType;
            this.categoryType = categoryType;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            JButton button = new JButton(label);

            button.addActionListener(e -> {
                String namaKategori = (String) table.getValueAt(row, 0);
                CategoryView categoryView = (CategoryView) SwingUtilities.getWindowAncestor(table);

                if (actionType.equals("edit")) {
                    // Handle edit logic
                    String newName = JOptionPane.showInputDialog(null, "Edit Kategori:", namaKategori);
                    if (newName != null && !newName.trim().isEmpty()) {
                        new CategoryDAO().updateCategory(namaKategori, newName, categoryType);
                        JOptionPane.showMessageDialog(null, "Kategori berhasil diperbarui.");
                        categoryView.loadCategories(); // Memperbarui data kategori setelah edit
                    }
                } else if (actionType.equals("hapus")) {
                    // Handle delete logic
                    int confirm = JOptionPane.showConfirmDialog(null, "Hapus kategori?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        new CategoryDAO().deleteCategory(namaKategori, categoryType);
                        JOptionPane.showMessageDialog(null, "Kategori berhasil dihapus.");
                        categoryView.loadCategories(); // Memperbarui data kategori setelah hapus
                    }
                }
            });

            return button;
        }
    }

    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}

