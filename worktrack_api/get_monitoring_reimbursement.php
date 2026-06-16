<?php

header('Content-Type: application/json');

include "koneksi.php";

$query = mysqli_query(
    $conn,
    "SELECT
        r.*,
        u.nama
     FROM reimbursement r
     JOIN users u
        ON r.id_user = u.id_user
     ORDER BY r.id_reimbursement DESC"
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