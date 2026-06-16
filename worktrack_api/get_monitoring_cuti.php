<?php

header('Content-Type: application/json');

include "koneksi.php";

$query = mysqli_query(
    $conn,
    "SELECT
        c.*,
        u.nama
     FROM cuti c
     JOIN users u
        ON c.id_user = u.id_user
     ORDER BY c.id_cuti DESC"
);

$data = array();

while($row = mysqli_fetch_assoc($query)){

    $data[] = $row;
}

echo json_encode([
    "success" => true,
    "data" => $data
]);

?>