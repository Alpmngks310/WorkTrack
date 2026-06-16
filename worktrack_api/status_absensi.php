<?php

header('Content-Type: application/json');

include "koneksi.php";

$id_user = $_POST['id_user'] ?? '';

date_default_timezone_set('Asia/Jakarta');

$tanggal = date('Y-m-d');

$query = mysqli_query(
    $conn,
    "SELECT * FROM absensi
    WHERE id_user='$id_user'
    AND tanggal='$tanggal'
    LIMIT 1"
);

if(mysqli_num_rows($query) > 0){

    $data = mysqli_fetch_assoc($query);

    echo json_encode([
        "success" => true,
        "jam_masuk" => $data['jam_masuk'],
        "jam_keluar" => $data['jam_keluar'],
        "status" => $data['status']
    ]);

}else{

    echo json_encode([
        "success" => false,
        "message" => "Belum ada absensi hari ini"
    ]);

}