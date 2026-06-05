package com.worktrack.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.ApprovalReimbursementResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatPembayaranActivity :
    AppCompatActivity() {

    private lateinit var layoutRiwayat: LinearLayout

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_riwayat_pembayaran
        )

        layoutRiwayat =
            findViewById(
                R.id.layoutRiwayat
            )

        loadData()
    }

    private fun loadData() {

        RetrofitClient.instance
            .getRiwayatPembayaran()
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
                                        this@RiwayatPembayaranActivity
                                    )

                                tvKosong.text =
                                    "Belum ada riwayat pembayaran"

                                tvKosong.textSize = 16f

                                layoutRiwayat.addView(
                                    tvKosong
                                )

                                return
                            }

                            for (item in data) {

                                val card =
                                    LinearLayout(
                                        this@RiwayatPembayaranActivity
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
                                    TextView(this@RiwayatPembayaranActivity)

                                tvNama.text =
                                    "Karyawan : ${item.nama}"

                                tvNama.textSize = 18f

                                val tvNominal =
                                    TextView(this@RiwayatPembayaranActivity)

                                tvNominal.text =
                                    "Nominal : Rp ${item.nominal}"

                                val tvTanggal =
                                    TextView(this@RiwayatPembayaranActivity)

                                tvTanggal.text =
                                    "Tanggal : ${item.tanggal}"

                                val tvStatus =
                                    TextView(this@RiwayatPembayaranActivity)

                                tvStatus.text =
                                    "Status : PAID"

                                val btnBukti =
                                    Button(this@RiwayatPembayaranActivity)

                                btnBukti.text =
                                    "Lihat Bukti"

                                btnBukti.setOnClickListener {

                                    val intent =
                                        Intent(
                                            this@RiwayatPembayaranActivity,
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

                        Toast.makeText(
                            this@RiwayatPembayaranActivity,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
    }
}