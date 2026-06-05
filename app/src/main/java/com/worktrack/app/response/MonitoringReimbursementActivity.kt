package com.worktrack.app

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.MonitoringReimbursementResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonitoringReimbursementActivity :
    AppCompatActivity() {

    private lateinit var layoutReimbursement: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_monitoring_reimbursement_response
        )

        layoutReimbursement =
            findViewById(
                R.id.layoutReimbursement
            )

        loadData()
    }

    private fun loadData() {

        RetrofitClient.instance
            .getMonitoringReimbursement()
            .enqueue(
                object :
                    Callback<MonitoringReimbursementResponse> {

                    override fun onResponse(
                        call: Call<MonitoringReimbursementResponse>,
                        response: Response<MonitoringReimbursementResponse>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body()?.success == true
                        ) {

                            layoutReimbursement.removeAllViews()

                            val data =
                                response.body()?.data
                                    ?: emptyList()

                            for (item in data) {

                                val card =
                                    LinearLayout(
                                        this@MonitoringReimbursementActivity
                                    )

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

                                val tvNama =
                                    TextView(this@MonitoringReimbursementActivity)

                                tvNama.text =
                                    "👤 ${item.nama}"

                                tvNama.textSize = 18f

                                val tvTanggal =
                                    TextView(this@MonitoringReimbursementActivity)

                                tvTanggal.text =
                                    "📅 ${item.tanggal}"

                                val tvNominal =
                                    TextView(this@MonitoringReimbursementActivity)

                                tvNominal.text =
                                    "💰 Rp ${item.nominal}"

                                val tvKeterangan =
                                    TextView(this@MonitoringReimbursementActivity)

                                tvKeterangan.text =
                                    "📝 ${item.keterangan}"

                                val tvStatus =
                                    TextView(this@MonitoringReimbursementActivity)

                                tvStatus.text =
                                    "Status : ${item.status}"

                                tvStatus.textSize = 16f

                                when (item.status) {

                                    "Pending" ->
                                        tvStatus.setTextColor(
                                            Color.parseColor("#F57C00")
                                        )

                                    "Approved" ->
                                        tvStatus.setTextColor(
                                            Color.parseColor("#2E7D32")
                                        )

                                    "Rejected" ->
                                        tvStatus.setTextColor(
                                            Color.parseColor("#D32F2F")
                                        )

                                    "Paid" ->
                                        tvStatus.setTextColor(
                                            Color.parseColor("#1565C0")
                                        )
                                }

                                val btnBukti =
                                    Button(
                                        this@MonitoringReimbursementActivity
                                    )

                                btnBukti.text =
                                    "Lihat Bukti"

                                btnBukti.setOnClickListener {

                                    val intent =
                                        Intent(
                                            this@MonitoringReimbursementActivity,
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
                                card.addView(tvTanggal)
                                card.addView(tvNominal)
                                card.addView(tvKeterangan)
                                card.addView(tvStatus)
                                card.addView(btnBukti)

                                layoutReimbursement.addView(card)
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<MonitoringReimbursementResponse>,
                        t: Throwable
                    ) {
                    }
                }
            )
    }
}