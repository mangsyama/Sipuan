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
(75, 'Gaji', 'Kerja', 'Bulanan', 10000000, '2025-01-01', 'Pendapatan'),
(76, 'Makanan', 'Makanan', 'Biaya pokok bulanan', 5000000, '2025-01-01', 'Pengeluaran');

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
(2, 'admin', 'admin', 'admin', '2024-12-01 03:25:02');

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
