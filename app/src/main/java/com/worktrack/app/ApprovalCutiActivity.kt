package com.worktrack.app

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.ApprovalCutiResponse
import com.worktrack.app.response.SimpleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApprovalCutiActivity : AppCompatActivity() {

    private lateinit var layoutPengajuan: LinearLayout
    private lateinit var layoutRiwayat: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approval_cuti)

        layoutPengajuan = findViewById(R.id.layoutPengajuan)
        layoutRiwayat = findViewById(R.id.layoutRiwayat)

        loadPengajuanCuti()
        loadRiwayatCuti()
    }

    private fun loadPengajuanCuti() {

        RetrofitClient.instance
            .getPengajuanCuti()
            .enqueue(object : Callback<ApprovalCutiResponse> {

                override fun onResponse(
                    call: Call<ApprovalCutiResponse>,
                    response: Response<ApprovalCutiResponse>
                ) {

                    if (response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        layoutPengajuan.removeAllViews()

                        val data = response.body()?.data ?: emptyList()

                        if (data.isEmpty()) {

                            val tvKosong = TextView(this@ApprovalCutiActivity)
                            tvKosong.text =
                                "Saat ini belum ada permintaan cuti yang perlu diproses"
                            tvKosong.textSize = 16f
                            tvKosong.setPadding(24, 24, 24, 24)

                            layoutPengajuan.addView(tvKosong)
                            return
                        }

                        for (item in data) {

                            val card = LinearLayout(this@ApprovalCutiActivity)

                            card.orientation =
                                LinearLayout.VERTICAL

                            val params =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )

                            params.setMargins(
                                0,
                                0,
                                0,
                                24
                            )

                            card.layoutParams = params

                            card.setPadding(
                                32,
                                32,
                                32,
                                32
                            )

                            card.setBackgroundResource(
                                R.drawable.card_background
                            )

                            card.elevation = 8f
                            val tvNama = TextView(this@ApprovalCutiActivity)
                            tvNama.text = "Karyawan : ${item.nama}"
                            tvNama.textSize = 18f

                            val tvTanggal = TextView(this@ApprovalCutiActivity)
                            tvTanggal.text =
                                "${item.tanggal_mulai} s/d ${item.tanggal_selesai}"

                            val tvAlasan = TextView(this@ApprovalCutiActivity)
                            tvAlasan.text = "Alasan : ${item.alasan}"

                            val btnApprove = Button(this@ApprovalCutiActivity)
                            btnApprove.text = "Approve"

                            btnApprove.setOnClickListener {

                                AlertDialog.Builder(this@ApprovalCutiActivity)
                                    .setTitle("Approve Cuti")
                                    .setMessage("Yakin ingin menyetujui pengajuan ini?")
                                    .setPositiveButton("Ya") { _, _ ->

                                        RetrofitClient.instance
                                            .approveCuti(item.id_cuti)
                                            .enqueue(object :
                                                Callback<SimpleResponse> {

                                                override fun onResponse(
                                                    call: Call<SimpleResponse>,
                                                    response: Response<SimpleResponse>
                                                ) {

                                                    Toast.makeText(
                                                        this@ApprovalCutiActivity,
                                                        "Cuti disetujui",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                    loadPengajuanCuti()
                                                    loadRiwayatCuti()
                                                }

                                                override fun onFailure(
                                                    call: Call<SimpleResponse>,
                                                    t: Throwable
                                                ) {
                                                }
                                            })
                                    }
                                    .setNegativeButton("Batal", null)
                                    .show()
                            }

                            val btnReject = Button(this@ApprovalCutiActivity)
                            btnReject.text = "Reject"

                            btnReject.setOnClickListener {

                                AlertDialog.Builder(this@ApprovalCutiActivity)
                                    .setTitle("Reject Cuti")
                                    .setMessage("Yakin ingin menolak pengajuan ini?")
                                    .setPositiveButton("Ya") { _, _ ->

                                        RetrofitClient.instance
                                            .rejectCuti(item.id_cuti)
                                            .enqueue(object :
                                                Callback<SimpleResponse> {

                                                override fun onResponse(
                                                    call: Call<SimpleResponse>,
                                                    response: Response<SimpleResponse>
                                                ) {

                                                    Toast.makeText(
                                                        this@ApprovalCutiActivity,
                                                        "Cuti ditolak",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                    loadPengajuanCuti()
                                                    loadRiwayatCuti()
                                                }

                                                override fun onFailure(
                                                    call: Call<SimpleResponse>,
                                                    t: Throwable
                                                ) {
                                                }
                                            })
                                    }
                                    .setNegativeButton("Batal", null)
                                    .show()
                            }

                            card.addView(tvNama)
                            card.addView(tvTanggal)
                            card.addView(tvAlasan)
                            card.addView(btnApprove)
                            card.addView(btnReject)

                            layoutPengajuan.addView(card)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ApprovalCutiResponse>,
                    t: Throwable
                ) {
                }
            })
    }

    private fun loadRiwayatCuti() {

        RetrofitClient.instance
            .getRiwayatApprovalCuti()
            .enqueue(object : Callback<ApprovalCutiResponse> {

                override fun onResponse(
                    call: Call<ApprovalCutiResponse>,
                    response: Response<ApprovalCutiResponse>
                ) {

                    if (response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        layoutRiwayat.removeAllViews()

                        val data = response.body()?.data ?: emptyList()

                        for (item in data) {

                            val card = LinearLayout(this@ApprovalCutiActivity)

                            card.orientation =
                                LinearLayout.VERTICAL

                            val params =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )

                            params.setMargins(
                                0,
                                0,
                                0,
                                24
                            )

                            card.layoutParams = params

                            card.setPadding(
                                32,
                                32,
                                32,
                                32
                            )

                            card.setBackgroundResource(
                                R.drawable.card_background
                            )

                            card.elevation = 8f
                            val tvNama = TextView(this@ApprovalCutiActivity)
                            tvNama.text = "Karyawan : ${item.nama}"

                            val tvTanggal = TextView(this@ApprovalCutiActivity)
                            tvTanggal.text =
                                "${item.tanggal_mulai} s/d ${item.tanggal_selesai}"

                            val tvAlasan =
                                TextView(this@ApprovalCutiActivity)

                            tvAlasan.text =
                                "Alasan : ${item.alasan}"

                            tvAlasan.textSize = 14f

                            val tvStatus =
                                TextView(this@ApprovalCutiActivity)

                            tvStatus.text =
                                "Status : ${item.status}"

                            tvStatus.textSize = 16f
                            if (item.status == "Approved") {
                                tvStatus.setTextColor(
                                    Color.parseColor("#4CAF50")
                                )
                            } else {
                                tvStatus.setTextColor(
                                    Color.parseColor("#F44336")
                                )
                            }

                            card.addView(tvNama)
                            card.addView(tvTanggal)
                            card.addView(tvAlasan)
                            card.addView(tvStatus)

                            layoutRiwayat.addView(card)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ApprovalCutiResponse>,
                    t: Throwable
                ) {
                }
            })
    }
}