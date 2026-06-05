package com.worktrack.app.response

data class RiwayatReimbursementResponse(
    val success: Boolean,
    val data: List<RiwayatReimbursementItem>
)

data class RiwayatReimbursementItem(
    val id_reimbursement: String,
    val tanggal: String,
    val nominal: String,
    val keterangan: String,
    val status: String,
    val bukti: String
)