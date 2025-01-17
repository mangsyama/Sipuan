package com.sipuan.views;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sipuan.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.table.TableModel;
import java.util.*;
import java.util.stream.Collectors;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.DateComponentFormatter;


// Import tambahan
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import org.jfree.ui.RectangleInsets;

public class AnalysisView extends JFrame {

    private DefaultTableModel incomeTableModel;
    private DefaultTableModel expenseTableModel;

    public AnalysisView() {
        setTitle("SIPUAN - Analisis");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sidebar
        JPanel sidebar = createSidebar();
        
        // Set ikon aplikasi dengan ukuran sesuai
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/logo.png"));
            Image scaledIcon = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH); // Sesuaikan ukuran di sini
            setIconImage(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ikon aplikasi tidak ditemukan!");
        }

        // Konten utama
        JPanel mainContent = new JPanel(new BorderLayout());

        // Header dengan Pie Chart
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15)); // Menambahkan jarak kiri dan kanan
        JFreeChart pieChart = createPieChart();
        ChartPanel chartPanel = new ChartPanel(pieChart) {
            @Override
            public Dimension getPreferredSize() {
                int size = Math.min(getWidth(), getHeight());
                return new Dimension(800, 500);
            }
        };
        chartPanel.setBackground(Color.WHITE); // Memungkinkan pengaturan background dinamis
        headerPanel.add(chartPanel, BorderLayout.CENTER);

        // Tabel untuk Pendapatan dan Pengeluaran
        JPanel tablePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20)); // Menambahkan jarak di kiri dan kanan
        tablePanel.setBackground(Color.WHITE);

        incomeTableModel = new DefaultTableModel(new Object[]{"Kategori", "Jumlah"}, 0);
        JTable incomeTable = new JTable(incomeTableModel);
        incomeTable.setFont(new Font("Poppins", Font.PLAIN, 16));
        incomeTable.setRowHeight(30);
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        JPanel incomePanel = new JPanel(new BorderLayout());
        incomePanel.setBackground(Color.WHITE);
        JLabel incomeLabel = new JLabel("Pendapatan Terbanyak", SwingConstants.CENTER);
        incomeLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        incomePanel.add(incomeLabel, BorderLayout.NORTH);
        incomePanel.add(incomeScrollPane, BorderLayout.CENTER);

        expenseTableModel = new DefaultTableModel(new Object[]{"Kategori", "Jumlah"}, 0);
        JTable expenseTable = new JTable(expenseTableModel);
        expenseTable.setFont(new Font("Poppins", Font.PLAIN, 16));
        expenseTable.setRowHeight(30);
        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);
        JPanel expensePanel = new JPanel(new BorderLayout());
        expensePanel.setBackground(Color.WHITE);
        JLabel expenseLabel = new JLabel("Pengeluaran Terbanyak", SwingConstants.CENTER);
        expenseLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        expensePanel.add(expenseLabel, BorderLayout.NORTH);
        expensePanel.add(expenseScrollPane, BorderLayout.CENTER);

        tablePanel.add(incomePanel);
        tablePanel.add(expensePanel);

        // Tambahkan header dan tabel ke konten utama
        mainContent.add(headerPanel, BorderLayout.NORTH);
        mainContent.add(tablePanel, BorderLayout.CENTER);

        // Gabungkan sidebar dan konten utama menggunakan JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainContent);
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation(300); // Lebar sidebar
        splitPane.setOneTouchExpandable(false);

        // Menghilangkan border pada JSplitPane
        splitPane.setBorder(null);
    
        add(splitPane);

        loadData();

        setVisible(true);
    }

    private JFreeChart createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String incomeQuery = "SELECT SUM(amount) FROM transactions WHERE transaction_type = 'Pendapatan';";
            String expenseQuery = "SELECT SUM(amount) FROM transactions WHERE transaction_type = 'Pengeluaran';";

            PreparedStatement incomeStmt = connection.prepareStatement(incomeQuery);
            PreparedStatement expenseStmt = connection.prepareStatement(expenseQuery);

            ResultSet incomeRs = incomeStmt.executeQuery();
            ResultSet expenseRs = expenseStmt.executeQuery();

            double income = incomeRs.next() ? incomeRs.getDouble(1) : 0;
            double expense = expenseRs.next() ? expenseRs.getDouble(1) : 0;

            dataset.setValue("Pendapatan", income);
            dataset.setValue("Pengeluaran", expense);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // JFreeChart chart = ChartFactory.createPieChart("Perbandingan Pendapatan dan Pengeluaran", dataset, true, true, false); BACKUP PAKE TEXT JUDUL
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, true, false);
        chart.getTitle().setFont(new Font("Poppins", Font.BOLD, 24)); // Ganti dengan font yang diinginkan
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Pendapatan", new Color(76, 175, 80)); // Hijau
        plot.setSectionPaint("Pengeluaran", new Color(244, 67, 54)); // Merah
        plot.setBackgroundPaint(Color.WHITE);
        plot.setLabelFont(new Font("Poppins", Font.PLAIN, 18));
        plot.setInsets(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        return chart;
    }

    private void loadData() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String incomeQuery = "SELECT category, SUM(amount) as total FROM transactions WHERE transaction_type = 'Pendapatan' GROUP BY category ORDER BY total DESC;";
            String expenseQuery = "SELECT category, SUM(amount) as total FROM transactions WHERE transaction_type = 'Pengeluaran' GROUP BY category ORDER BY total DESC;";

            PreparedStatement incomeStmt = connection.prepareStatement(incomeQuery);
            PreparedStatement expenseStmt = connection.prepareStatement(expenseQuery);

            ResultSet incomeRs = incomeStmt.executeQuery();
            ResultSet expenseRs = expenseStmt.executeQuery();

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

            incomeTableModel.setRowCount(0);
            while (incomeRs.next()) {
                incomeTableModel.addRow(new Object[]{incomeRs.getString("category"), "Rp. " + currencyFormat.format(incomeRs.getDouble("total")).replace(",00", "").replace("Rp", "").trim()});
            }

            expenseTableModel.setRowCount(0);
            while (expenseRs.next()) {
                expenseTableModel.addRow(new Object[]{expenseRs.getString("category"), "Rp. " + currencyFormat.format(expenseRs.getDouble("total")).replace(",00", "").replace("Rp", "").trim()});
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        new AnalysisView();
    }
}
