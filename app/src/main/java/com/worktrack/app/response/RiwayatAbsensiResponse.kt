package com.worktrack.app.response

data class RiwayatAbsensiResponse(
    val success: Boolean,
    val data: List<RiwayatItem>
)

data class RiwayatItem(
    val id_absensi: String,
    val id_user: String,
    val tanggal: String,
    val jam_masuk: String?,
    val jam_keluar: String?,
    val status: String
)
