package com.worktrack.app

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.ReimbursementResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.Calendar
import com.worktrack.app.response.RiwayatReimbursementResponse
import android.graphics.Typeface
import android.widget.LinearLayout

class ReimbursementActivity : AppCompatActivity() {

    private lateinit var etTanggal: EditText
    private lateinit var etNominal: EditText
    private lateinit var etKeterangan: EditText

    private lateinit var btnPilihBukti: Button
    private lateinit var btnAjukanReimbursement: Button

    private lateinit var txtNamaFile: TextView
    private lateinit var layoutRiwayatReimbursement: LinearLayout
    private var idUser: String? = null
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reimbursement)

        etTanggal = findViewById(R.id.etTanggal)
        etNominal = findViewById(R.id.etNominal)
        etKeterangan = findViewById(R.id.etKeterangan)

        btnPilihBukti = findViewById(R.id.btnPilihBukti)
        btnAjukanReimbursement =
            findViewById(R.id.btnAjukanReimbursement)

        txtNamaFile = findViewById(R.id.txtNamaFile)
        layoutRiwayatReimbursement =
            findViewById(
                R.id.layoutRiwayatReimbursement
            )

        idUser = intent.getStringExtra("id_user")
        loadRiwayatReimbursement()

        etTanggal.setOnClickListener {

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

                    etTanggal.setText(tanggal)

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnPilihBukti.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_GET_CONTENT
            )

            intent.type = "image/*"

            startActivityForResult(
                intent,
                100
            )
        }

        btnAjukanReimbursement.setOnClickListener {

            if (selectedImageUri == null) {

                Toast.makeText(
                    this,
                    "Pilih bukti terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val tanggal =
                etTanggal.text.toString().trim()

            val nominal =
                etNominal.text.toString().trim()

            val keterangan =
                etKeterangan.text.toString().trim()

            if (
                tanggal.isEmpty() ||
                nominal.isEmpty() ||
                keterangan.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Semua data wajib diisi",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            uploadReimbursement(
                tanggal,
                nominal,
                keterangan
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (
            requestCode == 100 &&
            resultCode == RESULT_OK &&
            data != null
        ) {

            selectedImageUri = data.data

            txtNamaFile.text =
                selectedImageUri?.lastPathSegment
                    ?: "File Dipilih"
        }
    }

    private fun uriToFile(uri: Uri): File {

        val inputStream: InputStream? =
            contentResolver.openInputStream(uri)

        val file = File(
            cacheDir,
            "upload_" + System.currentTimeMillis() + ".jpg"
        )

        val outputStream: OutputStream =
            file.outputStream()

        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        return file
    }

    private fun uploadReimbursement(
        tanggal: String,
        nominal: String,
        keterangan: String
    ) {

        val file =
            uriToFile(selectedImageUri!!)

        val requestFile =
            file.asRequestBody(
                "image/*".toMediaTypeOrNull()
            )

        val buktiPart =
            MultipartBody.Part.createFormData(
                "bukti",
                file.name,
                requestFile
            )

        val idUserBody =
            idUser!!.toRequestBody(
                "text/plain".toMediaTypeOrNull()
            )

        val tanggalBody =
            tanggal.toRequestBody(
                "text/plain".toMediaTypeOrNull()
            )

        val nominalBody =
            nominal.toRequestBody(
                "text/plain".toMediaTypeOrNull()
            )

        val keteranganBody =
            keterangan.toRequestBody(
                "text/plain".toMediaTypeOrNull()
            )

        RetrofitClient.instance
            .ajukanReimbursement(
                idUserBody,
                tanggalBody,
                nominalBody,
                keteranganBody,
                buktiPart
            )
            .enqueue(object : Callback<ReimbursementResponse> {

                override fun onResponse(
                    call: Call<ReimbursementResponse>,
                    response: Response<ReimbursementResponse>
                ) {

                    if (
                        response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        Toast.makeText(
                            this@ReimbursementActivity,
                            response.body()?.message,
                            Toast.LENGTH_LONG
                        ).show()

                        etTanggal.setText("")
                        etNominal.setText("")
                        etKeterangan.setText("")

                        txtNamaFile.text =
                            "Belum ada file dipilih"

                        selectedImageUri = null
                        loadRiwayatReimbursement()

                    } else {

                        Toast.makeText(
                            this@ReimbursementActivity,
                            "Gagal mengirim reimbursement",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ReimbursementResponse>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        this@ReimbursementActivity,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
    private fun loadRiwayatReimbursement() {

        RetrofitClient.instance
            .getRiwayatReimbursement(
                idUser!!
            )
            .enqueue(
                object : Callback<RiwayatReimbursementResponse> {

                    override fun onResponse(
                        call: Call<RiwayatReimbursementResponse>,
                        response: Response<RiwayatReimbursementResponse>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body()?.success == true
                        ) {

                            layoutRiwayatReimbursement
                                .removeAllViews()

                            val data =
                                response.body()?.data
                                    ?: emptyList()

                            for (item in data) {

                                val card =
                                    LinearLayout(
                                        this@ReimbursementActivity
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

                                card.layoutParams =
                                    params

                                val tvTanggal =
                                    TextView(
                                        this@ReimbursementActivity
                                    )

                                tvTanggal.text =
                                    item.tanggal

                                tvTanggal.setTypeface(
                                    null,
                                    Typeface.BOLD
                                )

                                val tvNominal =
                                    TextView(
                                        this@ReimbursementActivity
                                    )

                                tvNominal.text =
                                    "Rp ${item.nominal}"

                                val tvKeterangan =
                                    TextView(
                                        this@ReimbursementActivity
                                    )

                                tvKeterangan.text =
                                    item.keterangan

                                val tvStatus =
                                    TextView(
                                        this@ReimbursementActivity
                                    )

                                tvStatus.text =
                                    "Status : ${item.status}"

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

                                    "Paid" ->
                                        tvStatus.setTextColor(
                                            getColor(
                                                android.R.color.holo_blue_dark
                                            )
                                        )
                                }
                                val btnLihatBukti =
                                    Button(
                                        this@ReimbursementActivity
                                    )

                                btnLihatBukti.text =
                                    "Lihat Bukti"

                                btnLihatBukti.setOnClickListener {

                                    val intent =
                                        Intent(
                                            this@ReimbursementActivity,
                                            BuktiReimbursementActivity::class.java
                                        )

                                    intent.putExtra(
                                        "bukti",
                                        item.bukti
                                    )

                                    startActivity(intent)
                                }
                                card.addView(tvTanggal)
                                card.addView(tvNominal)
                                card.addView(tvKeterangan)
                                card.addView(tvStatus)
                                card.addView(btnLihatBukti)


                                layoutRiwayatReimbursement
                                    .addView(card)
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<RiwayatReimbursementResponse>,
                        t: Throwable
                    ) {
                    }
                }
            )
    }
}
