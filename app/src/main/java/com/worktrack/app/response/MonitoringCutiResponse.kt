package com.worktrack.app.response

data class MonitoringCutiResponse(
    val success: Boolean,
    val data: List<MonitoringCutiItem>
)

data class MonitoringCutiItem(
    val id_cuti: String,
    val nama: String,
    val tanggal_mulai: String,
    val tanggal_selesai: String,
    val alasan: String,
    val status: String
)