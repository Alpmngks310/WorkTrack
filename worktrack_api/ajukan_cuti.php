<?php

header('Content-Type: application/json');

include "koneksi.php";

$id_user = $_POST['id_user'] ?? '';
$tanggal_mulai = $_POST['tanggal_mulai'] ?? '';
$tanggal_selesai = $_POST['tanggal_selesai'] ?? '';
$alasan = $_POST['alasan'] ?? '';

if(
    empty($id_user) ||
    empty($tanggal_mulai) ||
    empty($tanggal_selesai) ||
    empty($alasan)
){

    echo json_encode([
        "success" => false,
        "message" => "Semua data wajib diisi"
    ]);

    exit;
}

date_default_timezone_set('Asia/Jakarta');

$tanggal_pengajuan = date('Y-m-d');

$query = mysqli_query(
    $conn,
    "INSERT INTO cuti
    (
        id_user,
        tanggal_mulai,
        tanggal_selesai,
        alasan,
        status,
        tanggal_pengajuan
    )
    VALUES
    (
        '$id_user',
        '$tanggal_mulai',
        '$tanggal_selesai',
        '$alasan',
        'Pending',
        '$tanggal_pengajuan'
    )"
);

if($query){

    echo json_encode([
        "success" => true,
        "message" => "Pengajuan cuti berhasil"
    ]);

}else{

    echo json_encode([
        "success" => false,
        "message" => "Pengajuan cuti gagal"
    ]);

}