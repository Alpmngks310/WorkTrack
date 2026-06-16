<?php

header('Content-Type: application/json');

include "koneksi.php";

$id_reimbursement =
    $_POST['id_reimbursement'];

$query = mysqli_query(
    $conn,
    "UPDATE reimbursement
     SET status='Paid'
     WHERE id_reimbursement='$id_reimbursement'"
);

if($query){

    echo json_encode([
        "success" => true,
        "message" => "Pembayaran berhasil"
    ]);

}else{

    echo json_encode([
        "success" => false,
        "message" => "Pembayaran gagal"
    ]);

}

?>