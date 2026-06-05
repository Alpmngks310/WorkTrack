package com.worktrack.app

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_manager)

        val txtNamaManager =
            findViewById<TextView>(
                R.id.txtNamaManager
            )

        val txtRoleManager =
            findViewById<TextView>(
                R.id.txtRoleManager
            )

        val btnLogoutManager =
            findViewById<Button>(
                R.id.btnLogoutManager
            )

        val menuApprovalCuti =
            findViewById<LinearLayout>(
                R.id.menuApprovalCuti
            )

        val menuApprovalReimbursement =
            findViewById<LinearLayout>(
                R.id.menuApprovalReimbursement
            )

        val menuMonitoringAbsensi =
            findViewById<LinearLayout>(
                R.id.menuMonitoringAbsensi
            )

        val nama =
            intent.getStringExtra("nama")

        val role =
            intent.getStringExtra("role")

        txtNamaManager.text = nama
        txtRoleManager.text = role

        menuMonitoringAbsensi.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MonitoringAbsensiActivity::class.java
                )
            )

        }

        btnLogoutManager.setOnClickListener {

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

        menuApprovalCuti.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ApprovalCutiActivity::class.java
                )
            )

        }
        menuApprovalReimbursement.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ApprovalReimbursementActivity::class.java
                )
            )

        }

    }
}