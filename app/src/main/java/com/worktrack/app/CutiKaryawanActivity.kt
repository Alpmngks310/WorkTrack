package com.worktrack.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.CutiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.DatePickerDialog
import java.util.Calendar
import android.widget.LinearLayout
import android.widget.TextView
import com.worktrack.app.response.RiwayatCutiResponse

class CutiKaryawanActivity : AppCompatActivity() {

    private lateinit var etTanggalMulai: EditText
    private lateinit var etTanggalSelesai: EditText
    private lateinit var etAlasan: EditText
    private lateinit var btnAjukanCuti: Button

    private lateinit var layoutRiwayatCuti: LinearLayout
    private var idUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuti_karyawan)

        etTanggalMulai = findViewById(R.id.etTanggalMulai)
        etTanggalSelesai = findViewById(R.id.etTanggalSelesai)
        etAlasan = findViewById(R.id.etAlasan)
        btnAjukanCuti = findViewById(R.id.btnAjukanCuti)
        layoutRiwayatCuti =
            findViewById(R.id.layoutRiwayatCuti)
        etTanggalMulai.setOnClickListener {

            val calendar = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, year, month, day ->

                    val tanggal =
                        String.format(
                            "%04d-%02d-%02d",
                            year,
                            month + 1,
                            day
                        )

                    etTanggalMulai.setText(tanggal)

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        etTanggalSelesai.setOnClickListener {

            val calendar = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, year, month, day ->

                    val tanggal =
                        String.format(
                            "%04d-%02d-%02d",
                            year,
                            month + 1,
                            day
                        )

                    etTanggalSelesai.setText(tanggal)

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        idUser = intent.getStringExtra("id_user")
        loadRiwayatCuti()
        btnAjukanCuti.setOnClickListener {

            val tanggalMulai =
                etTanggalMulai.text.toString().trim()

            val tanggalSelesai =
                etTanggalSelesai.text.toString().trim()

            val alasan =
                etAlasan.text.toString().trim()

            if (
                tanggalMulai.isEmpty() ||
                tanggalSelesai.isEmpty() ||
                alasan.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Semua data wajib diisi",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            RetrofitClient.instance
                .ajukanCuti(
                    idUser!!,
                    tanggalMulai,
                    tanggalSelesai,
                    alasan
                )
                .enqueue(object : Callback<CutiResponse> {

                    override fun onResponse(
                        call: Call<CutiResponse>,
                        response: Response<CutiResponse>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body()?.success == true
                        ) {

                            Toast.makeText(
                                this@CutiKaryawanActivity,
                                response.body()?.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            etTanggalMulai.setText("")
                            etTanggalSelesai.setText("")
                            etAlasan.setText("")
                            loadRiwayatCuti()

                        } else {

                            Toast.makeText(
                                this@CutiKaryawanActivity,
                                response.body()?.message
                                    ?: "Gagal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<CutiResponse>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            this@CutiKaryawanActivity,
                            t.message,
                            Toast.LENGTH_LONG
                        ).show()

                    }
                })
        }
    }
    private fun loadRiwayatCuti() {

        if (idUser.isNullOrEmpty()) return

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

                        layoutRiwayatCuti.removeAllViews()

                        val data =
                            response.body()?.data ?: emptyList()

                        if (data.isEmpty()) {

                            val tv =
                                TextView(this@CutiKaryawanActivity)

                            tv.text =
                                "Belum ada pengajuan cuti"

                            layoutRiwayatCuti.addView(tv)

                            return
                        }

                        for (item in data) {

                            val card = LinearLayout(this@CutiKaryawanActivity)

                            card.orientation = LinearLayout.VERTICAL

                            card.setPadding(
                                24,
                                24,
                                24,
                                24
                            )

                            val params = LinearLayout.LayoutParams(
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

                            card.setBackgroundResource(
                                R.drawable.card_background
                            )

                            val tvTanggal =
                                TextView(this@CutiKaryawanActivity)

                            tvTanggal.text =
                                "📅 ${item.tanggal_mulai} s/d ${item.tanggal_selesai}"

                            tvTanggal.textSize = 16f

                            tvTanggal.setTypeface(
                                null,
                                android.graphics.Typeface.BOLD
                            )

                            val tvStatus =
                                TextView(this@CutiKaryawanActivity)

                            tvStatus.text =
                                "Status : ${item.status}"

                            val tvAlasan =
                                TextView(this@CutiKaryawanActivity)

                            tvAlasan.text =
                                "Alasan : ${item.alasan}"

                            tvAlasan.setPadding(
                                0,
                                8,
                                0,
                                0
                            )

                            card.addView(tvTanggal)
                            card.addView(tvStatus)
                            card.addView(tvAlasan)

                            layoutRiwayatCuti.addView(card)}
                    }
                }

                override fun onFailure(
                    call: Call<RiwayatCutiResponse>,
                    t: Throwable
                ) {
                }
            })
    }
}