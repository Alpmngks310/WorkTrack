package com.worktrack.app

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardFinanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_finance)

        val txtNamaFinance =
            findViewById<TextView>(
                R.id.txtNamaFinance
            )

        val txtRoleFinance =
            findViewById<TextView>(
                R.id.txtRoleFinance
            )

        val menuPembayaran =
            findViewById<LinearLayout>(
                R.id.menuPembayaran
            )

        val menuRiwayatPembayaran =
            findViewById<LinearLayout>(
                R.id.menuRiwayatPembayaran
            )

        val btnLogoutFinance =
            findViewById<Button>(
                R.id.btnLogoutFinance
            )

        val nama =
            intent.getStringExtra("nama")

        val role =
            intent.getStringExtra("role")

        txtNamaFinance.text = nama
        txtRoleFinance.text = role

        menuPembayaran.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    PembayaranReimbursementActivity::class.java
                )
            )

        }


            menuRiwayatPembayaran.setOnClickListener {

                startActivity(
                    Intent(
                        this,
                        RiwayatPembayaranActivity::class.java
                    )
                )

            }


        btnLogoutFinance.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->

                    SessionManager(this)
                        .logout()

                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        )
                    )

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