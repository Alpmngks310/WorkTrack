package com.worktrack.app.response

data class RiwayatCutiResponse(
    val success: Boolean,
    val data: List<RiwayatCutiItem>
)

data class RiwayatCutiItem(
    val id_cuti: String,
    val tanggal_mulai: String,
    val tanggal_selesai: String,
    val alasan: String,
    val status: String,
    val tanggal_pengajuan: String
)