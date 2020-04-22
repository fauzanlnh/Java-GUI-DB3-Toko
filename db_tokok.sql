-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 14 Feb 2018 pada 08.49
-- Versi Server: 10.1.29-MariaDB
-- PHP Version: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_tokok`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_barang`
--

CREATE TABLE `t_barang` (
  `kode_barang` varchar(10) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `kode_kategori` varchar(10) NOT NULL,
  `harga_jual` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_barang`
--

INSERT INTO `t_barang` (`kode_barang`, `nama_barang`, `kode_kategori`, `harga_jual`) VALUES
('BRG0001', 'Logitech K102', 'Keyboard', 360000),
('BRG0002', 'Logitech G502', 'Mouse', 770000),
('BRG0003', 'Logitech G102', 'Mouse', 280000),
('BRG0004', 'Logitech G300s', 'Mouse', 240000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_bmasuk`
--

CREATE TABLE `t_bmasuk` (
  `kode_barang` varchar(100) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `kode_kategori` varchar(10) NOT NULL,
  `jumlah` int(50) DEFAULT NULL,
  `harga` int(20) DEFAULT NULL,
  `tgl_masuk` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_bmasuk`
--

INSERT INTO `t_bmasuk` (`kode_barang`, `nama_barang`, `kode_kategori`, `jumlah`, `harga`, `tgl_masuk`) VALUES
('BRG0001', 'Logitech K102', 'Keyboard', 20, 350000, '2018-01-28'),
('BRG0001', 'Logitech K102', 'Keyboard', 10, 345000, '2018-02-04'),
('BRG0002', 'Logitech G502', 'Mouse', 4, 750000, '2018-02-04'),
('BRG0003', 'Logitech G102', 'Mouse', 50, 250000, '2018-02-04'),
('BRG0004', 'Logitech G300s', 'Mouse', 20, 220000, '2018-02-04');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_kategori`
--

CREATE TABLE `t_kategori` (
  `kode_kategori` varchar(10) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_login`
--

CREATE TABLE `t_login` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `bagian` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_login`
--

INSERT INTO `t_login` (`username`, `password`, `bagian`) VALUES
('admin', 'fauzan', 'Admin'),
('admin3', 'admin3', 'User'),
('ghiar121', 'debuz123', 'User'),
('user', 'user', 'User');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_pelanggan`
--

CREATE TABLE `t_pelanggan` (
  `kd_pelanggan` varchar(10) NOT NULL,
  `nama_pelanggan` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `no_telp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_pelanggan`
--

INSERT INTO `t_pelanggan` (`kd_pelanggan`, `nama_pelanggan`, `alamat`, `no_telp`) VALUES
('PLG0001', 'Djordie', 'Jl.Buah Batu', '085222034858'),
('PLG0002', 'Fauzan', 'Jl,Manglid', '08080');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_persediaan`
--

CREATE TABLE `t_persediaan` (
  `kode_barang` varchar(10) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `kode_kategori` varchar(10) NOT NULL,
  `jumlah_tersedia` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_persediaan`
--

INSERT INTO `t_persediaan` (`kode_barang`, `nama_barang`, `kode_kategori`, `jumlah_tersedia`) VALUES
('BRG0001', 'Logitech K102', 'Keyboard', 4),
('BRG0002', 'Logitech G502', 'Mouse', 2),
('BRG0003', 'Logitech G102', 'Mouse', 15),
('BRG0004', 'Logitech G300s', 'Mouse', 18);

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_transaksi`
--

CREATE TABLE `t_transaksi` (
  `kd_transaksi` varchar(10) NOT NULL,
  `tanggal` date NOT NULL,
  `nama_pelanggan` varchar(10) NOT NULL,
  `kode_barang` varchar(10) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `keluar` int(10) NOT NULL,
  `kd_pelanggan` varchar(10) DEFAULT NULL,
  `harga_satuan` int(11) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `total_pembelian` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_transaksi`
--

INSERT INTO `t_transaksi` (`kd_transaksi`, `tanggal`, `nama_pelanggan`, `kode_barang`, `nama_barang`, `keluar`, `kd_pelanggan`, `harga_satuan`, `total_harga`, `total_pembelian`) VALUES
('BN290001', '2018-02-13', 'Djordie', 'BRG0001', 'Logitech K102', 2, 'PLG0001', 360000, 720000, 1200000),
('BN290001', '2018-02-13', 'Djordie', 'BRG0004', 'Logitech G300s', 2, 'PLG0001', 240000, 480000, 1200000),
('BN290002', '2018-02-13', 'Djordie', 'BRG0001', 'Logitech K102', 2, 'PLG0001', 360000, 720000, 2120000),
('BN290002', '2018-02-13', 'Djordie', 'BRG0003', 'Logitech G102', 5, 'PLG0001', 280000, 1400000, 2120000),
('BN290003', '2018-02-04', 'Djordie', 'BRG0001', 'Logitech K102', 10, 'PLG0001', 360000, 3600000, 3600000),
('BN290004', '2018-02-05', 'Djordie', 'BRG0003', 'Logitech G102', 30, 'PLG0001', 280000, 8400000, 8400000),
('BN290005', '2018-02-06', 'Djordie', 'BRG0001', 'Logitech K102', 4, 'PLG0001', 360000, 1440000, 1440000),
('BN290006', '2018-02-06', 'Fauzan', 'BRG0002', 'Logitech G502', 2, 'PLG0002', 770000, 1540000, 1540000),
('BN290007', '2018-02-06', 'Fauzan', 'BRG0001', 'Logitech K102', 3, 'PLG0002', 360000, 1080000, 1080000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `t_barang`
--
ALTER TABLE `t_barang`
  ADD PRIMARY KEY (`kode_barang`);

--
-- Indexes for table `t_bmasuk`
--
ALTER TABLE `t_bmasuk`
  ADD KEY `kode_barang` (`kode_barang`);

--
-- Indexes for table `t_kategori`
--
ALTER TABLE `t_kategori`
  ADD PRIMARY KEY (`kode_kategori`);

--
-- Indexes for table `t_login`
--
ALTER TABLE `t_login`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `t_pelanggan`
--
ALTER TABLE `t_pelanggan`
  ADD PRIMARY KEY (`kd_pelanggan`);

--
-- Indexes for table `t_persediaan`
--
ALTER TABLE `t_persediaan`
  ADD KEY `kode_barang` (`kode_barang`);

--
-- Indexes for table `t_transaksi`
--
ALTER TABLE `t_transaksi`
  ADD KEY `kode_barang` (`kode_barang`),
  ADD KEY `kd_pelanggan` (`kd_pelanggan`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `t_barang`
--
ALTER TABLE `t_barang`
  ADD CONSTRAINT `t_barang_ibfk_1` FOREIGN KEY (`kode_barang`) REFERENCES `t_persediaan` (`kode_barang`);

--
-- Ketidakleluasaan untuk tabel `t_bmasuk`
--
ALTER TABLE `t_bmasuk`
  ADD CONSTRAINT `t_bmasuk_ibfk_1` FOREIGN KEY (`kode_barang`) REFERENCES `t_barang` (`kode_barang`),
  ADD CONSTRAINT `t_bmasuk_ibfk_2` FOREIGN KEY (`kode_barang`) REFERENCES `t_persediaan` (`kode_barang`);

--
-- Ketidakleluasaan untuk tabel `t_kategori`
--
ALTER TABLE `t_kategori`
  ADD CONSTRAINT `t_kategori_ibfk_1` FOREIGN KEY (`kode_kategori`) REFERENCES `t_barang` (`kode_barang`);

--
-- Ketidakleluasaan untuk tabel `t_transaksi`
--
ALTER TABLE `t_transaksi`
  ADD CONSTRAINT `t_transaksi_ibfk_1` FOREIGN KEY (`kode_barang`) REFERENCES `t_barang` (`kode_barang`),
  ADD CONSTRAINT `t_transaksi_ibfk_2` FOREIGN KEY (`kd_pelanggan`) REFERENCES `t_pelanggan` (`kd_pelanggan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
