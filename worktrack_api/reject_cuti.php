<?php

include "koneksi.php";

$id_cuti = $_POST['id_cuti'];

$query = mysqli_query(
    $conn,
    "UPDATE cuti
     SET status='Rejected'
     WHERE id_cuti='$id_cuti'"
);

echo json_encode([
    "success" => $query
]);