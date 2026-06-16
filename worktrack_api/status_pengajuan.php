<?php

header('Content-Type: application/json');

include "koneksi.php";

$id_user = $_POST['id_user'] ?? '';

$jatah = 12;
$dipakai = 0;

$query = mysqli_query(
    $conn,
    "SELECT tanggal_mulai, tanggal_selesai
    FROM cuti
    WHERE id_user='$id_user'
    AND status='Approved'"
);

while($row = mysqli_fetch_assoc($query)){

    $mulai = new DateTime(
        $row['tanggal_mulai']
    );

    $selesai = new DateTime(
        $row['tanggal_selesai']
    );

    $jumlah_hari =
        $mulai->diff($selesai)->days + 1;

    $dipakai += $jumlah_hari;
}

$sisa = $jatah - $dipakai;

echo json_encode([
    "success" => true,
    "jatah" => $jatah,
    "dipakai" => $dipakai,
    "sisa" => $sisa
]);