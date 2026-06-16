<?php

header('Content-Type: application/json');

include "koneksi.php";

date_default_timezone_set('Asia/Jakarta');

$id_user = $_POST['id_user'] ?? '';
$tanggal = $_POST['tanggal'] ?? '';
$nominal = $_POST['nominal'] ?? '';
$keterangan = $_POST['keterangan'] ?? '';

$tanggal_pengajuan = date('Y-m-d');

if(!isset($_FILES['bukti'])){

    echo json_encode([
        "success" => false,
        "message" => "Bukti tidak ditemukan"
    ]);

    exit;
}

$folder = "uploads_reimbursement/";

if(!file_exists($folder)){
    mkdir($folder);
}

$namaFile =
    time() . "_" .
    basename($_FILES['bukti']['name']);

$pathFile = $folder . $namaFile;

if(
    move_uploaded_file(
        $_FILES['bukti']['tmp_name'],
        $pathFile
    )
){

    $query = mysqli_query(
        $conn,
        "INSERT INTO reimbursement
        (
            id_user,
            tanggal,
            nominal,
            bukti,
            keterangan,
            status,
            tanggal_pengajuan
        )
        VALUES
        (
            '$id_user',
            '$tanggal',
            '$nominal',
            '$pathFile',
            '$keterangan',
            'Pending',
            '$tanggal_pengajuan'
        )"
    );

    if($query){

        echo json_encode([
            "success" => true,
            "message" => "Reimbursement berhasil diajukan"
        ]);

    }else{

        echo json_encode([
            "success" => false,
            "message" => "Gagal simpan database"
        ]);
    }

}else{

    echo json_encode([
        "success" => false,
        "message" => "Upload gagal"
    ]);
}