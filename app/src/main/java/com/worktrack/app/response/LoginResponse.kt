package com.worktrack.app.response

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: UserData?
)

data class UserData(
    val id_user: String,
    val nama: String,
    val email: String,
    val role: String
)