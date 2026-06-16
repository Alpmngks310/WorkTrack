
<?php

header('Content-Type: application/json');

include "koneksi.php";

$id_user = $_POST['id_user'] ?? '';

$query = mysqli_query(
    $conn,
    "SELECT *
    FROM absensi
    WHERE id_user='$id_user'
    ORDER BY tanggal DESC"
);

$data = [];

while($row = mysqli_fetch_assoc($query)){
    $data[] = $row;
}

echo json_encode([
    "success" => true,
    "data" => $data
]);