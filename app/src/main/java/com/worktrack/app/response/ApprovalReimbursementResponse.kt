package com.worktrack.app.response

data class ApprovalReimbursementResponse(
    val success: Boolean,
    val data: List<ApprovalReimbursementItem>
)

data class ApprovalReimbursementItem(
    val id_reimbursement: String,
    val id_user: String,
    val nama: String,
    val tanggal: String,
    val nominal: String,
    val bukti: String,
    val keterangan: String,
    val status: String,
    val tanggal_pengajuan: String
)