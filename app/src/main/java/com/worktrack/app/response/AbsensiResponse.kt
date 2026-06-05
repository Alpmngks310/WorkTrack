package com.worktrack.app.response

data class AbsensiResponse(
    val success: Boolean,
    val message: String,
    val jam_masuk: String?,
    val jam_keluar: String?
)
