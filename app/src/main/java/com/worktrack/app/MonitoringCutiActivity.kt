package com.worktrack.app

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.MonitoringCutiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonitoringCutiActivity :
    AppCompatActivity() {

    private lateinit var layoutCuti: LinearLayout

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_monitoring_cuti
        )

        layoutCuti =
            findViewById(
                R.id.layoutCuti
            )

        loadData()
    }

    private fun loadData() {

        RetrofitClient.instance
            .getMonitoringCuti()
            .enqueue(
                object :
                    Callback<MonitoringCutiResponse> {

                    override fun onResponse(
                        call: Call<MonitoringCutiResponse>,
                        response: Response<MonitoringCutiResponse>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body()?.success == true
                        ) {

                            val data =
                                response.body()?.data
                                    ?: emptyList()

                            layoutCuti.removeAllViews()

                            for (item in data) {

                                val card =
                                    LinearLayout(
                                        this@MonitoringCutiActivity
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

                                card.layoutParams =
                                    params

                                card.setPadding(
                                    32,
                                    32,
                                    32,
                                    32
                                )

                                card.setBackgroundResource(
                                    R.drawable.card_background
                                )

                                card.elevation = 6f

                                val tvNama =
                                    TextView(this@MonitoringCutiActivity)

                                tvNama.text =
                                    "👤 ${item.nama}"

                                tvNama.textSize = 18f

                                val tvPeriode =
                                    TextView(this@MonitoringCutiActivity)

                                tvPeriode.text =
                                    "📅 ${item.tanggal_mulai} s/d ${item.tanggal_selesai}"

                                val tvAlasan =
                                    TextView(this@MonitoringCutiActivity)

                                tvAlasan.text =
                                    "📝 ${item.alasan}"

                                val tvStatus =
                                    TextView(this@MonitoringCutiActivity)

                                tvStatus.text =
                                    "Status : ${item.status}"

                                tvStatus.textSize = 16f

                                when (item.status) {

                                    "Approved" ->
                                        tvStatus.setTextColor(
                                            android.graphics.Color.parseColor(
                                                "#2E7D32"
                                            )
                                        )

                                    "Rejected" ->
                                        tvStatus.setTextColor(
                                            android.graphics.Color.parseColor(
                                                "#D32F2F"
                                            )
                                        )

                                    else ->
                                        tvStatus.setTextColor(
                                            android.graphics.Color.parseColor(
                                                "#F57C00"
                                            )
                                        )
                                }

                                card.addView(tvNama)
                                card.addView(tvPeriode)
                                card.addView(tvAlasan)
                                card.addView(tvStatus)

                                layoutCuti.addView(card)
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<MonitoringCutiResponse>,
                        t: Throwable
                    ) {
                    }
                }
            )
    }
}