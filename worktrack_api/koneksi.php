<?php

$conn = mysqli_connect(
    "localhost",
    "root",
    "",
    "db_worktrack"
);

if (!$conn) {
    die("Koneksi Gagal");
}