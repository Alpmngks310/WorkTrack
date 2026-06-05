package com.worktrack.app

import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.RiwayatCutiResponse
import com.worktrack.app.response.StatusPengajuanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusPengajuanActivity : AppCompatActivity() {

    private lateinit var txtDipakai: TextView
    private lateinit var txtSisa: TextView
    private lateinit var layoutRiwayatStatus: LinearLayout

    private var idUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_pengajuan)

        txtDipakai = findViewById(R.id.txtDipakai)
        txtSisa = findViewById(R.id.txtSisa)
        layoutRiwayatStatus =
            findViewById(R.id.layoutRiwayatStatus)

        idUser = intent.getStringExtra("id_user")

        loadStatusPengajuan()
        loadRiwayatCuti()    }

    private fun loadRiwayatCuti() {

        RetrofitClient.instance
            .getRiwayatCuti(idUser!!)
            .enqueue(object : Callback<RiwayatCutiResponse> {

                override fun onResponse(
                    call: Call<RiwayatCutiResponse>,
                    response: Response<RiwayatCutiResponse>
                ) {

                    if (
                        response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        val data =
                            response.body()?.data ?: emptyList()

                        layoutRiwayatStatus.removeAllViews()

                        var totalDipakai = 0

                        for (item in data) {

                            if (
                                item.status.equals(
                                    "Approved",
                                    true
                                )
                            ) {

                                totalDipakai++
                            }

                            val card =
                                LinearLayout(
                                    this@StatusPengajuanActivity
                                )

                            card.orientation =
                                LinearLayout.VERTICAL

                            card.setPadding(
                                24,
                                24,
                                24,
                                24
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

                            card.layoutParams = params

                            val tvTanggal =
                                TextView(
                                    this@StatusPengajuanActivity
                                )

                            tvTanggal.text =
                                "Tanggal Pengajuan : ${item.tanggal_pengajuan}"

                            tvTanggal.setTypeface(
                                null,
                                Typeface.BOLD
                            )

                            val tvPeriode =
                                TextView(
                                    this@StatusPengajuanActivity
                                )

                            tvPeriode.text =
                                "Periode : ${item.tanggal_mulai} s/d ${item.tanggal_selesai}"

                            val tvAlasan =
                                TextView(
                                    this@StatusPengajuanActivity
                                )

                            tvAlasan.text =
                                "Alasan : ${item.alasan}"

                            val tvStatus =
                                TextView(
                                    this@StatusPengajuanActivity
                                )

                            when(item.status){

                                "Pending" ->
                                    tvStatus.setTextColor(
                                        getColor(
                                            android.R.color.holo_orange_dark
                                        )
                                    )

                                "Approved" ->
                                    tvStatus.setTextColor(
                                        getColor(
                                            android.R.color.holo_green_dark
                                        )
                                    )

                                "Rejected" ->
                                    tvStatus.setTextColor(
                                        getColor(
                                            android.R.color.holo_red_dark
                                        )
                                    )
                            }
                            card.addView(tvTanggal)
                            card.addView(tvPeriode)
                            card.addView(tvAlasan)
                            card.addView(tvStatus)

                            layoutRiwayatStatus.addView(card)
                        }

                        val sisa = 12 - totalDipakai

                        txtDipakai.text =
                            "Dipakai : $totalDipakai Hari"

                        txtSisa.text =
                            "Sisa : $sisa Hari"
                    }
                }

                override fun onFailure(
                    call: Call<RiwayatCutiResponse>,
                    t: Throwable
                ) {
                }
            })
    }
    private fun loadStatusPengajuan() {

        RetrofitClient.instance
            .getStatusPengajuan(idUser!!)
            .enqueue(object : Callback<StatusPengajuanResponse> {

                override fun onResponse(
                    call: Call<StatusPengajuanResponse>,
                    response: Response<StatusPengajuanResponse>
                ) {

                    if (
                        response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        txtDipakai.text =
                            "Dipakai : ${response.body()?.dipakai} Hari"

                        txtSisa.text =
                            "Sisa : ${response.body()?.sisa} Hari"
                    }
                }

                override fun onFailure(
                    call: Call<StatusPengajuanResponse>,
                    t: Throwable
                ) {
                }
            })
    }
}
