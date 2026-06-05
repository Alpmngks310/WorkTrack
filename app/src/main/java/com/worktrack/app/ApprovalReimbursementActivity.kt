package com.worktrack.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.ApprovalReimbursementResponse
import com.worktrack.app.response.SimpleResponse

class ApprovalReimbursementActivity : AppCompatActivity() {

    private lateinit var layoutPengajuan: LinearLayout
    private lateinit var layoutRiwayat: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_approval_reimbursement
        )

        layoutPengajuan =
            findViewById(R.id.layoutPengajuan)

        layoutRiwayat =
            findViewById(R.id.layoutRiwayat)

        loadPengajuanReimbursement()
        loadRiwayatReimbursement()
    }

    private fun loadPengajuanReimbursement() {

        RetrofitClient.instance
            .getPengajuanReimbursement()
            .enqueue(
                object :
                    Callback<ApprovalReimbursementResponse> {

                    override fun onResponse(
                        call: Call<ApprovalReimbursementResponse>,
                        response: Response<ApprovalReimbursementResponse>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body()?.success == true
                        ) {

                            layoutPengajuan.removeAllViews()

                            val data =
                                response.body()?.data
                                    ?: emptyList()

                            if (data.isEmpty()) {

                                val tvKosong =
                                    TextView(
                                        this@ApprovalReimbursementActivity
                                    )

                                tvKosong.text =
                                    "Saat ini belum ada reimbursement yang perlu diproses"

                                tvKosong.textSize = 16f

                                layoutPengajuan.addView(tvKosong)

                                return
                            }

                            for (item in data) {

                                val card =
                                    LinearLayout(
                                        this@ApprovalReimbursementActivity
                                    )

                                card.orientation =
                                    LinearLayout.VERTICAL

                                card.setPadding(
                                    32,
                                    32,
                                    32,
                                    32
                                )

                                card.setBackgroundResource(
                                    R.drawable.card_background
                                )

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

                                card.layoutParams =
                                    params

                                val tvNama =
                                    TextView(this@ApprovalReimbursementActivity)

                                tvNama.text =
                                    "Karyawan : ${item.nama}"

                                tvNama.textSize = 18f

                                val tvNominal =
                                    TextView(this@ApprovalReimbursementActivity)

                                tvNominal.text =
                                    "Nominal : Rp ${item.nominal}"

                                val tvKet =
                                    TextView(this@ApprovalReimbursementActivity)

                                tvKet.text =
                                    "Keterangan : ${item.keterangan}"

                                val btnBukti =
                                    Button(this@ApprovalReimbursementActivity)

                                btnBukti.text =
                                    "Lihat Bukti"

                                btnBukti.setOnClickListener {

                                    val intent =
                                        Intent(
                                            this@ApprovalReimbursementActivity,
                                            BuktiReimbursementActivity::class.java
                                        )

                                    intent.putExtra(
                                        "url_bukti",
                                        RetrofitClient.BASE_URL +
                                                item.bukti
                                    )

                                    startActivity(intent)
                                }

                                val btnApprove =
                                    Button(this@ApprovalReimbursementActivity)

                                btnApprove.text =
                                    "Approve"

                                btnApprove.setOnClickListener {

                                    approveData(
                                        item.id_reimbursement
                                    )
                                }

                                val btnReject =
                                    Button(this@ApprovalReimbursementActivity)

                                btnReject.text =
                                    "Reject"

                                btnReject.setOnClickListener {

                                    rejectData(
                                        item.id_reimbursement
                                    )
                                }

                                card.addView(tvNama)
                                card.addView(tvNominal)
                                card.addView(tvKet)
                                card.addView(btnBukti)
                                card.addView(btnApprove)
                                card.addView(btnReject)

                                layoutPengajuan.addView(card)
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<ApprovalReimbursementResponse>,
                        t: Throwable
                    ) {
                    }
                }
            )
    }

    private fun approveData(
        idReimbursement: String
    ) {

        RetrofitClient.instance
            .approveReimbursement(
                idReimbursement
            )
            .enqueue(
                object : Callback<SimpleResponse> {

                    override fun onResponse(
                        call: Call<SimpleResponse>,
                        response: Response<SimpleResponse>
                    ) {

                        Toast.makeText(
                            this@ApprovalReimbursementActivity,
                            "Reimbursement disetujui",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadPengajuanReimbursement()
                        loadRiwayatReimbursement()
                    }

                    override fun onFailure(
                        call: Call<SimpleResponse>,
                        t: Throwable
                    ) {
                    }
                }
            )
    }

    private fun rejectData(
        idReimbursement: String
    ) {

        RetrofitClient.instance
            .rejectReimbursement(
                idReimbursement
            )
            .enqueue(
                object : Callback<SimpleResponse> {

                    override fun onResponse(
                        call: Call<SimpleResponse>,
                        response: Response<SimpleResponse>
                    ) {

                        Toast.makeText(
                            this@ApprovalReimbursementActivity,
                            "Reimbursement ditolak",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadPengajuanReimbursement()
                        loadRiwayatReimbursement()
                    }

                    override fun onFailure(
                        call: Call<SimpleResponse>,
                        t: Throwable
                    ) {
                    }
                }
            )
    }

    private fun loadRiwayatReimbursement() {

        RetrofitClient.instance
            .getRiwayatApprovalReimbursement()
            .enqueue(
                object :
                    Callback<ApprovalReimbursementResponse> {

                    override fun onResponse(
                        call: Call<ApprovalReimbursementResponse>,
                        response: Response<ApprovalReimbursementResponse>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body()?.success == true
                        ) {

                            layoutRiwayat.removeAllViews()

                            val data =
                                response.body()?.data
                                    ?: emptyList()

                            if (data.isEmpty()) {

                                val tvKosong =
                                    TextView(
                                        this@ApprovalReimbursementActivity
                                    )

                                tvKosong.text =
                                    "Belum ada riwayat reimbursement"

                                tvKosong.textSize = 16f

                                layoutRiwayat.addView(tvKosong)

                                return
                            }

                            for (item in data) {

                                val card =
                                    LinearLayout(
                                        this@ApprovalReimbursementActivity
                                    )

                                card.orientation =
                                    LinearLayout.VERTICAL

                                card.setPadding(
                                    32,
                                    32,
                                    32,
                                    32
                                )

                                card.setBackgroundResource(
                                    R.drawable.card_background
                                )

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

                                card.layoutParams =
                                    params

                                val tvNama =
                                    TextView(
                                        this@ApprovalReimbursementActivity
                                    )

                                tvNama.text =
                                    "Karyawan : ${item.nama}"

                                tvNama.textSize = 17f

                                val tvNominal =
                                    TextView(
                                        this@ApprovalReimbursementActivity
                                    )

                                tvNominal.text =
                                    "Nominal : Rp ${item.nominal}"

                                val tvKeterangan =
                                    TextView(
                                        this@ApprovalReimbursementActivity
                                    )

                                tvKeterangan.text =
                                    "Keterangan : ${item.keterangan}"

                                val tvStatus =
                                    TextView(
                                        this@ApprovalReimbursementActivity
                                    )

                                tvStatus.text =
                                    "Status : ${item.status}"

                                tvStatus.textSize = 16f

                                when (item.status) {

                                    "Approved" ->
                                        tvStatus.setTextColor(
                                            android.graphics.Color.parseColor(
                                                "#4CAF50"
                                            )
                                        )

                                    "Rejected" ->
                                        tvStatus.setTextColor(
                                            android.graphics.Color.parseColor(
                                                "#F44336"
                                            )
                                        )

                                    "Paid" ->
                                        tvStatus.setTextColor(
                                            android.graphics.Color.parseColor(
                                                "#2196F3"
                                            )
                                        )
                                }

                                val btnBukti =
                                    Button(
                                        this@ApprovalReimbursementActivity
                                    )

                                btnBukti.text =
                                    "Lihat Bukti"

                                btnBukti.setOnClickListener {

                                    val intent =
                                        Intent(
                                            this@ApprovalReimbursementActivity,
                                            BuktiReimbursementActivity::class.java
                                        )

                                    intent.putExtra(
                                        "url_bukti",
                                        RetrofitClient.BASE_URL +
                                                item.bukti
                                    )

                                    startActivity(intent)
                                }

                                card.addView(tvNama)
                                card.addView(tvNominal)
                                card.addView(tvKeterangan)
                                card.addView(tvStatus)
                                card.addView(btnBukti)

                                layoutRiwayat.addView(card)
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<ApprovalReimbursementResponse>,
                        t: Throwable
                    ) {
                    }
                }
            )
    }
}