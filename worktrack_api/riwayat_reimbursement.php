<?php

header('Content-Type: application/json');

include "koneksi.php";

$id_user = $_POST['id_user'] ?? '';

$query = mysqli_query(
    $conn,
    "SELECT *
    FROM reimbursement
    WHERE id_user='$id_user'
    ORDER BY id_reimbursement DESC"
);

$data = [];

while($row = mysqli_fetch_assoc($query)){
    $data[] = $row;
}

echo json_encode([
    "success" => true,
    "data" => $data
]);