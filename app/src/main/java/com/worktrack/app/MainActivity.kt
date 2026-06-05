package com.worktrack.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.worktrack.app.api.RetrofitClient
import com.worktrack.app.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val session = SessionManager(this)
        if (session.isLogin()) {

            val tujuanDashboard =
                when (session.getRole()) {

                    "Manager" ->
                        DashboardManagerActivity::class.java

                    "Finance" ->
                        DashboardFinanceActivity::class.java

                    "HR" ->
                        DashboardHRActivity::class.java

                    else ->
                        DashboardActivity::class.java
                }

            val intent =
                Intent(
                    this,
                    tujuanDashboard
                )

            intent.putExtra(
                "id_user",
                session.getIdUser()
            )

            intent.putExtra(
                "nama",
                session.getNama()
            )

            intent.putExtra(
                "role",
                session.getRole()
            )

            startActivity(intent)
            finish()
        }

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Email dan Password wajib diisi",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(
        email: String,
        password: String
    ) {

        RetrofitClient.instance.login(
            email,
            password
        ).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                if (
                    response.isSuccessful &&
                    response.body()?.success == true
                ) {

                    Toast.makeText(
                        this@MainActivity,
                        "Login Berhasil",
                        Toast.LENGTH_SHORT
                    ).show()

                    val data = response.body()?.data

                    SessionManager(this@MainActivity)
                        .saveLogin(
                            data?.id_user ?: "",
                            data?.nama ?: "",
                            data?.role ?: ""
                        )
                    Toast.makeText(
                        this@MainActivity,
                        "Session Role = ${SessionManager(this@MainActivity).getRole()}",
                        Toast.LENGTH_LONG
                    ).show()

                    val role = data?.role

                    Toast.makeText(
                        this@MainActivity,
                        "Role: $role",
                        Toast.LENGTH_LONG
                    ).show()

                    if (role == "Karyawan") {

                        val intent = Intent(
                            this@MainActivity,
                            DashboardActivity::class.java
                        )

                        intent.putExtra(
                            "id_user",
                            data?.id_user
                        )

                        intent.putExtra(
                            "nama",
                            data?.nama
                        )

                        intent.putExtra(
                            "role",
                            data?.role
                        )

                        startActivity(intent)
                        finish()

                    }
                    else if (role == "Manager") {

                        val intent = Intent(
                            this@MainActivity,
                            DashboardManagerActivity::class.java
                        )

                        intent.putExtra(
                            "id_user",
                            data?.id_user
                        )

                        intent.putExtra(
                            "nama",
                            data?.nama
                        )

                        intent.putExtra(
                            "role",
                            data?.role
                        )

                        startActivity(intent)
                        finish()

                    }

                    else if (role == "HR") {

                        val intent = Intent(
                            this@MainActivity,
                            DashboardHRActivity::class.java
                        )

                        intent.putExtra(
                            "id_user",
                            data?.id_user
                        )

                        intent.putExtra(
                            "nama",
                            data?.nama
                        )

                        intent.putExtra(
                            "role",
                            data?.role
                        )

                        startActivity(intent)
                        finish()


                    } else if (role == "Finance") {

                        val intent = Intent(
                            this@MainActivity,
                            DashboardFinanceActivity::class.java
                        )

                        intent.putExtra(
                            "id_user",
                            data?.id_user
                        )

                        intent.putExtra(
                            "nama",
                            data?.nama
                        )

                        intent.putExtra(
                            "role",
                            data?.role
                        )

                        startActivity(intent)
                        finish()
                    }

                } else {

                    Toast.makeText(
                        this@MainActivity,
                        "Email atau Password Salah",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(
                call: Call<LoginResponse>,
                t: Throwable
            ) {

                t.printStackTrace()

                Toast.makeText(
                    this@MainActivity,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()

            }
        })
    }
}