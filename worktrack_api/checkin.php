<?php

header('Content-Type: application/json');

include "koneksi.php";

$id_user = $_POST['id_user'] ?? '';

date_default_timezone_set('Asia/Jakarta');

$tanggal = date('Y-m-d');
$jam_masuk = date('H:i:s');

$cek = mysqli_query(
    $conn,
    "SELECT * FROM absensi
    WHERE id_user='$id_user'
    AND tanggal='$tanggal'"
);

if(mysqli_num_rows($cek) > 0){

    echo json_encode([
        "success" => false,
        "message" => "Anda sudah Check In hari ini"
    ]);

    exit;
}

$query = mysqli_query(
    $conn,
    "INSERT INTO absensi
    (id_user, tanggal, jam_masuk, status)
    VALUES
    ('$id_user', '$tanggal', '$jam_masuk', 'Hadir')"
);

if($query){

    echo json_encode([
        "success" => true,
        "message" => "Check In Berhasil",
        "jam_masuk" => $jam_masuk,
        "jam_keluar" => null
    ]);

}else{

    echo json_encode([
        "success" => false,
        "message" => "Check In Gagal"
    ]);

}
