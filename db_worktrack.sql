-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 05 Jun 2026 pada 08.25
-- Versi server: 10.4.32-MariaDB-log
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_worktrack`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `absensi`
--

CREATE TABLE `absensi` (
  `id_absensi` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `jam_masuk` time DEFAULT NULL,
  `jam_keluar` time DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `absensi`
--

INSERT INTO `absensi` (`id_absensi`, `id_user`, `tanggal`, `jam_masuk`, `jam_keluar`, `status`) VALUES
(1, 1, '2026-06-01', '18:32:26', '18:32:33', 'Hadir'),
(2, 1, '2026-06-01', '18:32:32', '18:32:33', 'Hadir'),
(3, 4, '2026-06-01', '19:52:45', '19:52:48', 'Hadir'),
(4, 4, '2026-06-02', '16:46:08', '16:46:13', 'Hadir'),
(5, 4, '2026-06-04', '14:48:05', '14:48:07', 'Hadir');

-- --------------------------------------------------------

--
-- Struktur dari tabel `cuti`
--

CREATE TABLE `cuti` (
  `id_cuti` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `tanggal_mulai` date NOT NULL,
  `tanggal_selesai` date NOT NULL,
  `alasan` text NOT NULL,
  `status` enum('Pending','Approved','Rejected') DEFAULT 'Pending',
  `tanggal_pengajuan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `cuti`
--

INSERT INTO `cuti` (`id_cuti`, `id_user`, `tanggal_mulai`, `tanggal_selesai`, `alasan`, `status`, `tanggal_pengajuan`) VALUES
(1, 4, '2026-06-02', '2026-06-03', 'Sakit', 'Rejected', '0000-00-00'),
(2, 4, '2026-06-05', '2026-06-06', 'nikahan anak', 'Approved', '2026-06-01'),
(3, 4, '2026-06-06', '2026-06-30', 'malas kerja, saya mudah lelah', 'Approved', '2026-06-04');

-- --------------------------------------------------------

--
-- Struktur dari tabel `reimbursement`
--

CREATE TABLE `reimbursement` (
  `id_reimbursement` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `nominal` decimal(12,2) NOT NULL,
  `bukti` varchar(255) DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  `status` enum('Pending','Approved','Rejected','Paid') DEFAULT 'Pending',
  `tanggal_pengajuan` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `reimbursement`
--

INSERT INTO `reimbursement` (`id_reimbursement`, `id_user`, `tanggal`, `nominal`, `bukti`, `keterangan`, `status`, `tanggal_pengajuan`) VALUES
(1, 4, '2026-06-01', 20000.00, 'uploads_reimbursement/1780328628_upload_1780328628627.jpg', 'print laporan', 'Paid', '2026-06-01'),
(2, 4, '2026-06-01', 100000.00, 'uploads_reimbursement/1780329472_upload_1780329472492.jpg', 'wfc', 'Paid', '2026-06-01'),
(3, 4, '2026-06-09', 50000.00, 'uploads_reimbursement/1780559483_upload_1780559484039.jpg', 'buat beli ayam geprek d okeh', 'Paid', '2026-06-04');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Karyawan','HR','Manager','Finance') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id_user`, `nama`, `email`, `password`, `role`, `created_at`) VALUES
(1, 'Adika', 'adika@worktrack.com', '123456', 'Manager', '2026-05-31 14:33:23'),
(2, 'Desta', 'desta@worktrack.com', '123456', 'HR', '2026-05-31 14:33:23'),
(3, 'Hapis', 'hapis@worktrack.com', '123456', 'Finance', '2026-05-31 14:33:23'),
(4, 'Karyawan', 'karyawan@worktrack.com', '123456', 'Karyawan', '2026-05-31 14:33:23');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `absensi`
--
ALTER TABLE `absensi`
  ADD PRIMARY KEY (`id_absensi`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `cuti`
--
ALTER TABLE `cuti`
  ADD PRIMARY KEY (`id_cuti`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `reimbursement`
--
ALTER TABLE `reimbursement`
  ADD PRIMARY KEY (`id_reimbursement`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `absensi`
--
ALTER TABLE `absensi`
  MODIFY `id_absensi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `cuti`
--
ALTER TABLE `cuti`
  MODIFY `id_cuti` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `reimbursement`
--
ALTER TABLE `reimbursement`
  MODIFY `id_reimbursement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `absensi`
--
ALTER TABLE `absensi`
  ADD CONSTRAINT `absensi_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `cuti`
--
ALTER TABLE `cuti`
  ADD CONSTRAINT `cuti_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `reimbursement`
--
ALTER TABLE `reimbursement`
  ADD CONSTRAINT `reimbursement_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
