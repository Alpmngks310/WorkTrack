<?php

include "koneksi.php";

$id_reimbursement =
    $_POST['id_reimbursement'];

$query = mysqli_query(
    $conn,
    "UPDATE reimbursement
     SET status='Approved'
     WHERE id_reimbursement='$id_reimbursement'"
);

echo json_encode([
    "success" => $query
]);