<?php

header('Content-Type: application/json');

include "koneksi.php";

$id_user = $_POST['id_user'] ?? '';

date_default_timezone_set('Asia/Jakarta');

$tanggal = date('Y-m-d');
$jam_keluar = date('H:i:s');

$cek = mysqli_query(
    $conn,
    "SELECT * FROM absensi
    WHERE id_user='$id_user'
    AND tanggal='$tanggal'"
);

$data = mysqli_fetch_assoc($cek);

if(!$data){

    echo json_encode([
        "success" => false,
        "message" => "Silakan Check In terlebih dahulu"
    ]);

    exit;
}

if($data['jam_keluar'] != null){

    echo json_encode([
        "success" => false,
        "message" => "Anda sudah Check Out hari ini"
    ]);

    exit;
}

$query = mysqli_query(
    $conn,
    "UPDATE absensi
    SET jam_keluar='$jam_keluar'
    WHERE id_user='$id_user'
    AND tanggal='$tanggal'"
);

if($query){

    echo json_encode([
        "success" => true,
        "message" => "Check Out Berhasil",
        "jam_masuk" => $data['jam_masuk'],
        "jam_keluar" => $jam_keluar
    ]);

}else{

    echo json_encode([
        "success" => false,
        "message" => "Check Out Gagal"
    ]);

}