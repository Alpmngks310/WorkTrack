package com.worktrack.app

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.MonitoringAbsensiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonitoringAbsensiActivity : AppCompatActivity() {

    private lateinit var layoutAbsensi: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_monitoring_absensi
        )

        layoutAbsensi =
            findViewById(
                R.id.layoutAbsensi
            )

        loadData()
    }

    private fun loadData() {

        RetrofitClient.instance
            .getMonitoringAbsensi()
            .enqueue(
                object : Callback<MonitoringAbsensiResponse> {

                    override fun onResponse(
                        call: Call<MonitoringAbsensiResponse>,
                        response: Response<MonitoringAbsensiResponse>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body()?.success == true
                        ) {

                            layoutAbsensi.removeAllViews()

                            val data =
                                response.body()?.data
                                    ?: emptyList()

                            for (item in data) {

                                val card =
                                    LinearLayout(
                                        this@MonitoringAbsensiActivity
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

                                card.setBackgroundColor(
                                    android.graphics.Color.WHITE
                                )

                                card.elevation = 8f

                                val tvNama =
                                    TextView(
                                        this@MonitoringAbsensiActivity
                                    )

                                tvNama.text =
                                    "Nama : ${item.nama}"

                                tvNama.textSize = 18f

                                val tvTanggal =
                                    TextView(
                                        this@MonitoringAbsensiActivity
                                    )

                                tvTanggal.text =
                                    "Tanggal : ${item.tanggal}"

                                val tvMasuk =
                                    TextView(
                                        this@MonitoringAbsensiActivity
                                    )

                                tvMasuk.text =
                                    "Jam Masuk : ${item.jam_masuk}"

                                val tvKeluar =
                                    TextView(
                                        this@MonitoringAbsensiActivity
                                    )

                                tvKeluar.text =
                                    "Jam Keluar : ${item.jam_keluar}"

                                val tvStatus =
                                    TextView(
                                        this@MonitoringAbsensiActivity
                                    )

                                tvStatus.text =
                                    "Status : ${item.status}"

                                card.addView(tvNama)
                                card.addView(tvTanggal)
                                card.addView(tvMasuk)
                                card.addView(tvKeluar)
                                card.addView(tvStatus)

                                layoutAbsensi.addView(card)
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<MonitoringAbsensiResponse>,
                        t: Throwable
                    ) {
                    }
                }
            )
    }
}