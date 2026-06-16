<?php

header('Content-Type: application/json');

include "koneksi.php";

$query = mysqli_query(
    $conn,
    "SELECT
        a.*,
        u.nama
     FROM absensi a
     JOIN users u
     ON a.id_user = u.id_user
     ORDER BY a.id_absensi DESC"
);

$data = [];

while($row = mysqli_fetch_assoc($query)){
    $data[] = $row;
}

echo json_encode([
    "success" => true,
    "data" => $data
]);