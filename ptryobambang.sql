-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 18, 2025 at 03:33 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ptryobambang`
--

-- --------------------------------------------------------

--
-- Table structure for table `idbayar`
--

CREATE TABLE `idbayar` (
  `id_bayar` int(11) NOT NULL,
  `id_pesanan` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `produk` varchar(100) NOT NULL,
  `jumlah_beli` int(11) NOT NULL,
  `total` decimal(15,2) NOT NULL,
  `metode_pembayaran` varchar(50) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `idbayar`
--

INSERT INTO `idbayar` (`id_bayar`, `id_pesanan`, `id_user`, `id_produk`, `tanggal`, `produk`, `jumlah_beli`, `total`, `metode_pembayaran`, `alamat`) VALUES
(1, 7, 211011, 1, '2025-12-08', 'Jasa Pengamanan', 1, 200000000.00, 'Transfer Bank', 'Jl. Kesadaran 1'),
(2, 8, 211011, 1, '2025-12-08', 'Jasa Pengamanan', 1, 200000000.00, 'Transfer Bank', 'Jl. Kesadaran 1'),
(4, 7, 211011, 1, '2025-12-08', 'Jasa Pengamanan', 1, 100000000.00, 'Transfer Bank', 'Jl. Kesadaran 1'),
(5, 8, 211011, 1, '2025-12-08', 'Jasa Pengamanan', 1, 100000000.00, 'Transfer Bank', 'Jl. Kesadaran 1'),
(7, 9, 211011, 1, '2025-12-09', 'Jasa Pengamanan', 5, 100000000.00, 'COD', 'Jl. Kesadaran 1 No. 151'),
(8, 10, 400877, 7, '2025-12-17', 'Jasa Administrasi', 14, 100000000.00, 'Transfer Bank', 'PT. ABC, Jl. Kasablank'),
(9, 12, 400877, 2, '2025-12-17', 'Jasa Pengemudi', 25, 100000000.00, 'Transfer Bank', 'PT. ABC, Jl. Kasablanka'),
(10, 11, 400877, 6, '2025-12-17', 'Jasa Sales General', 50, 100000000.00, 'Transfer Bank', 'PT. ABC, Jl. Kasablanka');

-- --------------------------------------------------------

--
-- Table structure for table `iddetail_pesanan`
--

CREATE TABLE `iddetail_pesanan` (
  `id_detail` int(11) NOT NULL,
  `id_pesanan` int(11) DEFAULT NULL,
  `id_produk` int(11) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `idpesanan`
--

CREATE TABLE `idpesanan` (
  `id_pesanan` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `tanggal` timestamp NOT NULL DEFAULT current_timestamp(),
  `total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `idpesanan`
--

INSERT INTO `idpesanan` (`id_pesanan`, `id_user`, `tanggal`, `total`) VALUES
(1, NULL, '2025-12-07 17:00:00', NULL),
(2, NULL, '2025-12-07 17:00:00', NULL),
(3, NULL, '2025-12-07 17:00:00', NULL),
(4, NULL, '2025-12-07 17:00:00', NULL),
(5, NULL, '2025-12-07 17:00:00', NULL),
(6, 211011, '2025-12-07 17:00:00', 100000000),
(7, 211011, '2025-12-07 17:00:00', 100000000),
(8, 211011, '2025-12-07 17:00:00', 100000000),
(9, 211011, '2025-12-08 17:00:00', 500000000),
(10, 400877, '2025-12-16 17:00:00', 1400000000),
(11, 400877, '2025-12-16 17:00:00', 5000000000),
(12, 400877, '2025-12-16 17:00:00', 2500000000);

-- --------------------------------------------------------

--
-- Table structure for table `idproduk`
--

CREATE TABLE `idproduk` (
  `id_produk` int(11) NOT NULL,
  `nama_produk` varchar(100) NOT NULL,
  `harga` double NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `idproduk`
--

INSERT INTO `idproduk` (`id_produk`, `nama_produk`, `harga`, `stok`) VALUES
(1, 'Jasa Pengamanan', 100000000, 95),
(2, 'Jasa Pengemudi', 100000000, 75),
(3, 'Jasa Cleaning Service', 100000000, 100),
(4, 'Jasa Operator', 100000000, 100),
(5, 'Jasa Back-Office', 100000000, 100),
(6, 'Jasa Sales General', 100000000, 50),
(7, 'Jasa Administrasi', 100000000, 86),
(8, 'Jasa Customer Service', 100000000, 100),
(9, 'Jasa Teknisi', 100000000, 100),
(10, 'Jasa Marketing', 100000000, 100);

-- --------------------------------------------------------

--
-- Table structure for table `iduser`
--

CREATE TABLE `iduser` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `iduser`
--

INSERT INTO `iduser` (`id_user`, `username`, `password`) VALUES
(211011, 'admin', '21232f297a57a5a743894a0e4a801fc3'),
(400877, 'istrasi', '8a018eab96a8be7d003810675e24110a');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `idbayar`
--
ALTER TABLE `idbayar`
  ADD PRIMARY KEY (`id_bayar`),
  ADD KEY `fk_bayar_pesanan` (`id_pesanan`),
  ADD KEY `fk_bayar_user` (`id_user`),
  ADD KEY `fk_bayar_produk` (`id_produk`);

--
-- Indexes for table `iddetail_pesanan`
--
ALTER TABLE `iddetail_pesanan`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `id_pesanan` (`id_pesanan`),
  ADD KEY `id_produk` (`id_produk`);

--
-- Indexes for table `idpesanan`
--
ALTER TABLE `idpesanan`
  ADD PRIMARY KEY (`id_pesanan`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `idproduk`
--
ALTER TABLE `idproduk`
  ADD PRIMARY KEY (`id_produk`);

--
-- Indexes for table `iduser`
--
ALTER TABLE `iduser`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `idbayar`
--
ALTER TABLE `idbayar`
  MODIFY `id_bayar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `iddetail_pesanan`
--
ALTER TABLE `iddetail_pesanan`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `idpesanan`
--
ALTER TABLE `idpesanan`
  MODIFY `id_pesanan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `idproduk`
--
ALTER TABLE `idproduk`
  MODIFY `id_produk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `iduser`
--
ALTER TABLE `iduser`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=400878;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `idbayar`
--
ALTER TABLE `idbayar`
  ADD CONSTRAINT `fk_bayar_pesanan` FOREIGN KEY (`id_pesanan`) REFERENCES `idpesanan` (`id_pesanan`),
  ADD CONSTRAINT `fk_bayar_produk` FOREIGN KEY (`id_produk`) REFERENCES `idproduk` (`id_produk`),
  ADD CONSTRAINT `fk_bayar_user` FOREIGN KEY (`id_user`) REFERENCES `iduser` (`id_user`);

--
-- Constraints for table `iddetail_pesanan`
--
ALTER TABLE `iddetail_pesanan`
  ADD CONSTRAINT `iddetail_pesanan_ibfk_1` FOREIGN KEY (`id_pesanan`) REFERENCES `idpesanan` (`id_pesanan`),
  ADD CONSTRAINT `iddetail_pesanan_ibfk_2` FOREIGN KEY (`id_produk`) REFERENCES `idproduk` (`id_produk`);

--
-- Constraints for table `idpesanan`
--
ALTER TABLE `idpesanan`
  ADD CONSTRAINT `idpesanan_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `iduser` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
