package com.worktrack.app.response

data class StatusAbsensiResponse(
    val success: Boolean,
    val jam_masuk: String?,
    val jam_keluar: String?,
    val status: String?,
    val message: String?
)