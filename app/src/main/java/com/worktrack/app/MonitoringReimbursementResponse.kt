package com.worktrack.app.response

data class MonitoringReimbursementResponse(
    val success: Boolean,
    val data: List<MonitoringReimbursementItem>
)

data class MonitoringReimbursementItem(
    val id_reimbursement: String,
    val nama: String,
    val tanggal: String,
    val nominal: String,
    val keterangan: String,
    val status: String,
    val bukti: String
)