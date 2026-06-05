package com.worktrack.app.response

data class MonitoringAbsensiResponse(
    val success: Boolean,
    val data: List<MonitoringAbsensiItem>
)

data class MonitoringAbsensiItem(
    val id_absensi: String,
    val id_user: String,
    val nama: String,
    val tanggal: String,
    val jam_masuk: String,
    val jam_keluar: String,
    val status: String
)