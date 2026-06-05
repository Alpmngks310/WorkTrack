package com.worktrack.app

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.widget.Button

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val txtNama = findViewById<TextView>(R.id.txtNama)
        val txtRole = findViewById<TextView>(R.id.txtRole)

        val idUser = intent.getStringExtra("id_user")
        val nama = intent.getStringExtra("nama")
        val role = intent.getStringExtra("role")

        txtNama.text = nama
        txtRole.text = role

        val menuAbsensi =
            findViewById<LinearLayout>(
                R.id.menuAbsensi
            )
        val menuStatus =
            findViewById<LinearLayout>(
                R.id.menuStatus
            )
        val menuCuti =
            findViewById<LinearLayout>(
                R.id.menuCuti
            )
        val menuReimbursement =
            findViewById<LinearLayout>(
                R.id.menuReimbursement
            )
        val btnLogout =
            findViewById<Button>(
                R.id.btnLogout
            )

        menuAbsensi.setOnClickListener {

            val intent = Intent(
                this,
                AbsensiActivity::class.java
            )

            intent.putExtra(
                "id_user",
                idUser
            )

            startActivity(intent)

        }

        menuStatus.setOnClickListener {

            val intent = Intent(
                this,
                StatusPengajuanActivity::class.java
            )

            intent.putExtra(
                "id_user",
                idUser
            )

            startActivity(intent)

        }

        menuCuti.setOnClickListener {

            val intent = Intent(
                this,
                CutiKaryawanActivity::class.java
            )

            intent.putExtra(
                "id_user",
                idUser
            )

            startActivity(intent)

        }

        menuReimbursement.setOnClickListener {

            val intent = Intent(
                this,
                ReimbursementActivity::class.java
            )

            intent.putExtra(
                "id_user",
                idUser
            )

            startActivity(intent)

        }

        btnLogout.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage(
                    "Yakin ingin keluar?"
                )
                .setPositiveButton(
                    "Ya"
                ) { _, _ ->

                    SessionManager(this)
                        .logout()

                    val intent =
                        Intent(
                            this,
                            MainActivity::class.java
                        )

                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)

                    finish()
                }
                .setNegativeButton(
                    "Batal",
                    null
                )
                .show()
        }
    }
}