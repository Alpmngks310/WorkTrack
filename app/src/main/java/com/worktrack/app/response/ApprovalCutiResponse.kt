package com.worktrack.app.response

data class ApprovalCutiResponse(
    val success: Boolean,
    val data: List<ApprovalCutiItem>
)

data class ApprovalCutiItem(
    val id_cuti: String,
    val id_user: String,
    val nama: String,
    val tanggal_mulai: String,
    val tanggal_selesai: String,
    val alasan: String,
    val status: String,
    val tanggal_pengajuan: String
)