package com.worktrack.app

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardHRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_dashboard_hr
        )

        val txtNamaHR =
            findViewById<TextView>(
                R.id.txtNamaHR
            )

        val txtRoleHR =
            findViewById<TextView>(
                R.id.txtRoleHR
            )

        val menuMonitoringAbsensi =
            findViewById<LinearLayout>(
                R.id.menuMonitoringAbsensi
            )

        val menuMonitoringCuti =
            findViewById<LinearLayout>(
                R.id.menuMonitoringCuti
            )

        val menuMonitoringReimbursement =
            findViewById<LinearLayout>(
                R.id.menuMonitoringReimbursement
            )

        val btnLogoutHR =
            findViewById<Button>(
                R.id.btnLogoutHR
            )

        txtNamaHR.text =
            intent.getStringExtra("nama")

        txtRoleHR.text =
            intent.getStringExtra("role")

        menuMonitoringReimbursement.setOnClickListener {

            android.widget.Toast.makeText(
                this,
                "Masih dalam pengembangan",
                android.widget.Toast.LENGTH_SHORT
            ).show()


        }

        menuMonitoringCuti.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MonitoringCutiActivity::class.java
                )
            )

        }

        menuMonitoringReimbursement.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MonitoringReimbursementActivity::class.java
                )
            )

        }

        btnLogoutHR.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage(
                    "Yakin ingin logout?"
                )
                .setPositiveButton(
                    "Ya"
                ) { _, _ ->

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
        menuMonitoringAbsensi.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MonitoringAbsensiActivity::class.java
                )
            )

        }
    }
}