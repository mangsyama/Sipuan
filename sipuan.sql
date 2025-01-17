-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 17, 2025 at 02:58 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sipuan`
--

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `id` int(11) NOT NULL,
  `nama_kategori` varchar(100) NOT NULL,
  `jenis` enum('pengeluaran','pendapatan') NOT NULL,
  `tanggal_dibuat` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id`, `nama_kategori`, `jenis`, `tanggal_dibuat`) VALUES
(23, 'Gaji', 'pendapatan', '2024-12-11 06:13:00'),
(24, 'Bonus', 'pendapatan', '2024-12-11 06:13:00'),
(25, 'Penghasilan Usaha', 'pendapatan', '2024-12-11 06:13:00'),
(26, 'Investasi', 'pendapatan', '2024-12-11 06:13:00'),
(27, 'Penjualan Barang', 'pendapatan', '2024-12-11 06:13:00'),
(28, 'Dividen Saham', 'pendapatan', '2024-12-11 06:13:00'),
(29, 'Sewa Properti', 'pendapatan', '2024-12-11 06:13:00'),
(31, 'Pemasukan Lainnya', 'pendapatan', '2024-12-11 06:13:00'),
(32, 'Pendapatan Sampingan', 'pendapatan', '2024-12-11 06:13:00'),
(33, 'Makanan', 'pengeluaran', '2024-12-11 06:13:00'),
(34, 'Transportasi', 'pengeluaran', '2024-12-11 06:13:00'),
(35, 'Sewa Rumah', 'pengeluaran', '2024-12-11 06:13:00'),
(36, 'Tagihan Listrik', 'pengeluaran', '2024-12-11 06:13:00'),
(37, 'Kesehatan', 'pengeluaran', '2024-12-11 06:13:00'),
(38, 'Pendidikan', 'pengeluaran', '2024-12-11 06:13:00'),
(39, 'Rekreasi', 'pengeluaran', '2024-12-11 06:13:00'),
(40, 'Pakaian', 'pengeluaran', '2024-12-11 06:13:00'),
(41, 'Perawatan Kendaraan', 'pengeluaran', '2024-12-11 06:13:00'),
(42, 'Belanja Online', 'pengeluaran', '2024-12-11 06:13:00');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `category` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `amount` double NOT NULL,
  `date` date NOT NULL,
  `transaction_type` enum('Pendapatan','Pengeluaran') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `category`, `title`, `description`, `amount`, `date`, `transaction_type`) VALUES
(52, 'Gaji', 'WEDDING', 'Wedding bali 2025', 5000000, '2025-01-02', 'Pendapatan'),
(53, 'Makanan', 'Sarapan', 'Membeli sarapan pagi', 25000, '2025-01-01', 'Pengeluaran'),
(55, 'Gaji', 'Gaji Mingguan', 'Gaji mingguan Januari', 1250000, '2025-01-02', 'Pendapatan'),
(56, 'Makanan', 'Makan Siang', 'Makan siang di warung', 35000, '2025-01-03', 'Pengeluaran'),
(57, 'Sewa Rumah', 'Sewa Bulan Januari', 'Pembayaran sewa rumah bulan Januari', 1500000, '2025-01-05', 'Pengeluaran'),
(58, 'Tagihan Listrik', 'Bayar Listrik', 'Pembayaran listrik bulan Desember', 200000, '2025-01-10', 'Pengeluaran'),
(59, 'Bonus', 'Bonus Tahunan', 'Bonus akhir tahun dari perusahaan', 4555555, '2025-01-15', 'Pendapatan'),
(60, 'Rekreasi', 'Liburan', 'Liburan ke Bali', 200000, '2025-01-20', 'Pengeluaran'),
(61, 'Kesehatan', 'Check-Up', 'Check-up kesehatan rutin', 250000, '2025-01-08', 'Pengeluaran'),
(62, 'Pendidikan', 'Buku Sekolah', 'Membeli buku pelajaran anak', 150000, '2025-01-12', 'Pengeluaran'),
(64, 'Investasi', 'Investasi Saham', 'Investasi pada saham perusahaan', 100000, '2025-01-25', 'Pengeluaran'),
(66, 'Belanja Online', 'Belanja di Marketplace', 'Membeli keperluan sehari-hari secara online', 500000, '2025-01-02', 'Pengeluaran'),
(67, 'Sewa Properti', 'Sewa Kios', 'Pendapatan dari sewa kios', 2000000, '2025-01-06', 'Pendapatan'),
(68, 'Makanan', 'Makan Malam', 'Makan malam di restoran', 75000, '2025-01-07', 'Pengeluaran'),
(69, 'Transportasi', 'Isi Bensin', 'Pengisian bensin motor', 50000, '2025-01-08', 'Pengeluaran'),
(70, 'Kesehatan', 'Obat', 'Membeli obat di apotek', 100000, '2025-01-09', 'Pengeluaran'),
(71, 'Bonus', 'Kerjo', 'bonus kerjo', 100000, '2025-01-31', 'Pendapatan'),
(72, 'Pendapatan Sampingan', 'Trading', 'Profit', 5000000, '2025-01-01', 'Pendapatan'),
(73, 'Pemasukan Lainnya', 'Jual', 'saham', 2000000, '2025-01-06', 'Pendapatan');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `created_at`) VALUES
(1, 'admin', '123456', 'admin@gmail.com', '2024-12-01 03:00:24'),
(2, 'admin', 'admin', 'admin', '2024-12-01 03:25:02'),
(3, 'syama', 'syama', 'syama', '2024-12-01 04:11:08'),
(4, 'mang', 'mang', 'mang', '2024-12-01 04:31:51'),
(5, 'komang', 'komang', 'komang@gmail.com', '2024-12-22 01:21:27'),
(6, 'mamang', 'mamang', 'mamang@gmail.com', '2024-12-22 01:30:31');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
