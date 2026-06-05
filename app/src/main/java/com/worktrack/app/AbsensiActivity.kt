package com.worktrack.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.AbsensiResponse
import com.worktrack.app.response.StatusAbsensiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.LinearLayout
import android.view.ViewGroup
import com.worktrack.app.response.RiwayatAbsensiResponse
class AbsensiActivity : AppCompatActivity() {

    private lateinit var txtJamMasuk: TextView
    private lateinit var txtJamKeluar: TextView
    private lateinit var txtStatus: TextView

    private lateinit var btnCheckIn: Button
    private lateinit var btnCheckOut: Button

    private lateinit var layoutRiwayat: LinearLayout

    private var idUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absensi)

        txtJamMasuk = findViewById(R.id.txtJamMasuk)
        txtJamKeluar = findViewById(R.id.txtJamKeluar)
        txtStatus = findViewById(R.id.txtStatus)

        btnCheckIn = findViewById(R.id.btnCheckIn)
        btnCheckOut = findViewById(R.id.btnCheckOut)

        layoutRiwayat = findViewById(R.id.layoutRiwayat)

        idUser = intent.getStringExtra("id_user")

        if (!idUser.isNullOrEmpty()) {
            loadStatusAbsensi(idUser!!)
            loadRiwayatAbsensi(idUser!!)
        }

        btnCheckIn.setOnClickListener {

            if (idUser.isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "ID User tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Check In")
                .setMessage("Apakah Anda yakin ingin melakukan Check In?")
                .setPositiveButton("Ya") { _, _ ->

                    RetrofitClient.instance
                        .checkIn(idUser!!)
                        .enqueue(object : Callback<AbsensiResponse> {

                            override fun onResponse(
                                call: Call<AbsensiResponse>,
                                response: Response<AbsensiResponse>
                            ) {

                                if (
                                    response.isSuccessful &&
                                    response.body()?.success == true
                                ) {

                                    Toast.makeText(
                                        this@AbsensiActivity,
                                        response.body()?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    loadStatusAbsensi(idUser!!)
                                    loadRiwayatAbsensi(idUser!!)

                                } else {

                                    Toast.makeText(
                                        this@AbsensiActivity,
                                        response.body()?.message
                                            ?: "Check In Gagal",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }

                            override fun onFailure(
                                call: Call<AbsensiResponse>,
                                t: Throwable
                            ) {

                                Toast.makeText(
                                    this@AbsensiActivity,
                                    t.message,
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        })

                }
                .setNegativeButton("Batal", null)
                .show()
        }

        btnCheckOut.setOnClickListener {

            if (idUser.isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "ID User tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Check Out")
                .setMessage("Apakah Anda yakin ingin melakukan Check Out?")
                .setPositiveButton("Ya") { _, _ ->

                    RetrofitClient.instance
                        .checkOut(idUser!!)
                        .enqueue(object : Callback<AbsensiResponse> {

                            override fun onResponse(
                                call: Call<AbsensiResponse>,
                                response: Response<AbsensiResponse>
                            ) {

                                if (
                                    response.isSuccessful &&
                                    response.body()?.success == true
                                ) {

                                    Toast.makeText(
                                        this@AbsensiActivity,
                                        response.body()?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    loadStatusAbsensi(idUser!!)
                                    loadRiwayatAbsensi(idUser!!)
                                } else {

                                    Toast.makeText(
                                        this@AbsensiActivity,
                                        response.body()?.message
                                            ?: "Check Out Gagal",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }

                            override fun onFailure(
                                call: Call<AbsensiResponse>,
                                t: Throwable
                            ) {

                                Toast.makeText(
                                    this@AbsensiActivity,
                                    t.message,
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        })

                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun loadStatusAbsensi(idUser: String) {

        RetrofitClient.instance
            .getStatusAbsensi(idUser)
            .enqueue(object : Callback<StatusAbsensiResponse> {

                override fun onResponse(
                    call: Call<StatusAbsensiResponse>,
                    response: Response<StatusAbsensiResponse>
                ) {

                    if (
                        response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        txtJamMasuk.text =
                            response.body()?.jam_masuk ?: "--:--"

                        txtJamKeluar.text =
                            response.body()?.jam_keluar ?: "--:--"

                        txtStatus.text =
                            response.body()?.status ?: "Belum Absen"

                    }
                }

                override fun onFailure(
                    call: Call<StatusAbsensiResponse>,
                    t: Throwable
                ) {
                }
            })
    }


    private fun loadRiwayatAbsensi(idUser: String) {

        RetrofitClient.instance
            .getRiwayatAbsensi(idUser)
            .enqueue(object : Callback<RiwayatAbsensiResponse> {

                override fun onResponse(
                    call: Call<RiwayatAbsensiResponse>,
                    response: Response<RiwayatAbsensiResponse>
                ) {

                    if (
                        response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        layoutRiwayat.removeAllViews()

                        val data =
                            response.body()?.data ?: emptyList()

                        if (data.isEmpty()) {

                            val tv = TextView(this@AbsensiActivity)
                            tv.text = "Belum ada data absensi"

                            layoutRiwayat.addView(tv)

                            return
                        }

                        for (item in data) {

                            val tv = TextView(this@AbsensiActivity)

                            tv.text =
                                "Tanggal : ${item.tanggal}\n" +
                                        "Masuk : ${item.jam_masuk ?: "-"}\n" +
                                        "Keluar : ${item.jam_keluar ?: "-"}\n" +
                                        "Status : ${item.status}\n\n"

                            tv.layoutParams =
                                LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )

                            layoutRiwayat.addView(tv)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<RiwayatAbsensiResponse>,
                    t: Throwable
                ) {
                }
            })
    }
}