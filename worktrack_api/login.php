<?php

header('Content-Type: application/json');

include "koneksi.php";

$email = $_POST['email'] ?? '';
$password = $_POST['password'] ?? '';

$query = mysqli_query(
    $conn,
    "SELECT * FROM users
    WHERE email='$email'
    AND password='$password'"
);

if (mysqli_num_rows($query) > 0) {

    $data = mysqli_fetch_assoc($query);

    echo json_encode([
        "success" => true,
        "message" => "Login berhasil",
        "data" => $data
    ]);

} else {

    echo json_encode([
        "success" => false,
        "message" => "Email atau Password Salah"
    ]);

}
