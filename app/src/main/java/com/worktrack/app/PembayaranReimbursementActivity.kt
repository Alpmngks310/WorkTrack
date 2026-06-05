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
import com.worktrack.app.response.SimpleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranReimbursementActivity : AppCompatActivity() {

    private lateinit var layoutPembayaran: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_pembayaran_reimbursement
        )

        layoutPembayaran =
            findViewById(
                R.id.layoutPembayaran
            )

        loadData()
    }

    private fun loadData() {

        RetrofitClient.instance
            .getApprovedReimbursement()
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

                            layoutPembayaran.removeAllViews()

                            val data =
                                response.body()?.data
                                    ?: emptyList()

                            if (data.isEmpty()) {

                                val tvKosong =
                                    TextView(
                                        this@PembayaranReimbursementActivity
                                    )

                                tvKosong.text =
                                    "Tidak ada reimbursement yang siap dibayar"

                                tvKosong.textSize = 16f

                                tvKosong.setPadding(
                                    20,
                                    20,
                                    20,
                                    20
                                )

                                layoutPembayaran.addView(tvKosong)

                                return
                            }

                            for (item in data) {

                                val card =
                                    LinearLayout(
                                        this@PembayaranReimbursementActivity
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
                                        this@PembayaranReimbursementActivity
                                    )

                                tvNama.text =
                                    "Karyawan : ${item.nama}"

                                tvNama.textSize = 18f

                                val tvTanggal =
                                    TextView(
                                        this@PembayaranReimbursementActivity
                                    )

                                tvTanggal.text =
                                    "Tanggal : ${item.tanggal}"

                                val tvNominal =
                                    TextView(
                                        this@PembayaranReimbursementActivity
                                    )

                                tvNominal.text =
                                    "Nominal : Rp ${item.nominal}"

                                val tvKeterangan =
                                    TextView(
                                        this@PembayaranReimbursementActivity
                                    )

                                tvKeterangan.text =
                                    "Keterangan : ${item.keterangan}"

                                val btnBukti =
                                    Button(
                                        this@PembayaranReimbursementActivity
                                    )

                                btnBukti.text =
                                    "Lihat Bukti"

                                btnBukti.setOnClickListener {

                                    val intent =
                                        Intent(
                                            this@PembayaranReimbursementActivity,
                                            BuktiReimbursementActivity::class.java
                                        )

                                    intent.putExtra(
                                        "url_bukti",
                                        RetrofitClient.BASE_URL +
                                                item.bukti
                                    )

                                    startActivity(intent)
                                }

                                val btnBayar =
                                    Button(
                                        this@PembayaranReimbursementActivity
                                    )

                                btnBayar.text =
                                    "Bayar Reimbursement"

                                btnBayar.setOnClickListener {

                                    bayarReimbursement(
                                        item.id_reimbursement
                                    )
                                }

                                card.addView(tvNama)
                                card.addView(tvTanggal)
                                card.addView(tvNominal)
                                card.addView(tvKeterangan)
                                card.addView(btnBukti)
                                card.addView(btnBayar)

                                layoutPembayaran.addView(card)
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<ApprovalReimbursementResponse>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            this@PembayaranReimbursementActivity,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
    }

    private fun bayarReimbursement(
        idReimbursement: String
    ) {

        RetrofitClient.instance
            .paidReimbursement(
                idReimbursement
            )
            .enqueue(
                object : Callback<SimpleResponse> {

                    override fun onResponse(
                        call: Call<SimpleResponse>,
                        response: Response<SimpleResponse>
                    ) {

                        Toast.makeText(
                            this@PembayaranReimbursementActivity,
                            "Pembayaran berhasil"
                            , Toast.LENGTH_SHORT).show()

                        loadData()
                    }

                    override fun onFailure(
                        call: Call<SimpleResponse>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            this@PembayaranReimbursementActivity,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
    }
}