package com.worktrack.app.response

data class StatusPengajuanResponse(
    val success: Boolean,
    val jatah: Int,
    val dipakai: Int,
    val sisa: Int
)