package com.sipuan.views;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

//import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sipuan.database.DatabaseConnection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.pdf.*;
import com.sipuan.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportView extends JFrame {

    public ReportView() {
        // Set properties of the frame
        setTitle("SIPUAN - Laporan");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Set ikon aplikasi dengan ukuran sesuai
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/logo.png"));
            Image scaledIcon = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH); // Sesuaikan ukuran di sini
            setIconImage(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ikon aplikasi tidak ditemukan!");
        }

        // Sidebar
        JPanel sidebar = createSidebar();

        // Panel isi halaman
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridBagLayout()); // Menggunakan GridBagLayout untuk kontrol lebih baik

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Menambahkan jarak antar elemen
        gbc.gridx = 0; // Memastikan bahwa komponen berada di kolom pertama
        gbc.gridy = 0; // Memastikan bahwa komponen berada di baris pertama
        gbc.anchor = GridBagConstraints.CENTER; // Memastikan komponen diposisikan di tengah

        // Menambahkan ikon "laporan.png"
        ImageIcon reportIcon = new ImageIcon(getClass().getResource("/resources/cetaklaporan2.png"));
        reportIcon = new ImageIcon(reportIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)); // Ukuran ikon disesuaikan
        JLabel iconLabel = new JLabel(reportIcon);

        // Tombol "Cetak Laporan"
        JButton printButton = new JButton("Cetak Laporan");
        printButton.setBackground(new Color(0, 102, 255)); // Biru
        printButton.setForeground(Color.WHITE);
        printButton.setFont(new Font("Poppins", Font.BOLD, 20)); // Font tombol
        printButton.addActionListener(this::generateReport);

        // Menambahkan ikon dan tombol ke panel
        contentPanel.add(iconLabel, gbc); // Menambahkan ikon ke panel
        gbc.gridy++; // Geser ke baris berikutnya untuk tombol
        contentPanel.add(printButton, gbc); // Menambahkan tombol ke panel

        // Menambahkan sidebar dan isi halaman ke main panel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Menambahkan main panel ke frame
        add(mainPanel);

        setVisible(true);
    }

    
    private void generateReport(ActionEvent event) {
    try {
        // Tentukan jalur file di folder Dokumen
        String userHome = System.getProperty("user.home");
        String documentsPath = userHome + File.separator + "Documents";
        String filePath = documentsPath + File.separator + "Laporan_Keuangan_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";

        // Buat folder Dokumen jika belum ada
        File documentsDir = new File(documentsPath);
        if (!documentsDir.exists()) {
            documentsDir.mkdirs();
        }

        // Buat dokumen PDF
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(new File(filePath)));

        document.open();

        // Tambahkan konten laporan
        document.add(new Paragraph("Laporan Keuangan"));
        document.add(new Paragraph("Tanggal: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Ringkasan Pendapatan dan Pengeluaran Terbesar:"));
        document.add(new Paragraph("Pendapatan Terbanyak: " + getTopCategory("Pendapatan")));
        document.add(new Paragraph("Pengeluaran Terbanyak: " + getTopCategory("Pengeluaran")));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Data Semua Catatan:"));
        PdfPTable table = createDataTable();
        document.add(new Paragraph("\n"));
        document.add(table);

        document.close();

        JOptionPane.showMessageDialog(this, "Laporan berhasil disimpan di: " + filePath, "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat laporan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private String getTopCategory(String type) {
        String result = "Tidak ada data";
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT category, SUM(amount) as total FROM transactions WHERE transaction_type = ? GROUP BY category ORDER BY total DESC LIMIT 1;";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result = rs.getString("category") + " - Rp " + String.format("%,.0f", rs.getDouble("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private PdfPTable createDataTable() {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        try {
            table.setWidths(new float[]{2, 2, 2, 3, 2, 2});
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        PdfPCell headerCell;
        BaseColor headerColor = new BaseColor(0, 102, 182);

        String[] headers = {"Tanggal", "Kategori", "Judul", "Deskripsi", "Jumlah", "Tipe"};
        for (String header : headers) {
            headerCell = new PdfPCell(new Phrase(header, headerFont));
            headerCell.setBackgroundColor(headerColor);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(8);
            table.addCell(headerCell);
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT date, category, title, description, amount, transaction_type FROM transactions ORDER BY date DESC";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                table.addCell(new PdfPCell(new Phrase(rs.getString("date"))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("category"))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("title"))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("description"))));
                table.addCell(new PdfPCell(new Phrase("Rp " + String.format("%,.0f", rs.getDouble("amount")))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("transaction_type"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return table;
    }
    
    

// ======================================================================== SIDE BAR =========================================================================== 
    

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
        new ReportView();
    }
}
