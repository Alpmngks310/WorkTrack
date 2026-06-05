package com.worktrack.app

import android.content.Context

class SessionManager(context: Context) {

    private val pref =
        context.getSharedPreferences(
            "WorkTrackSession",
            Context.MODE_PRIVATE
        )

    fun saveLogin(
        idUser: String,
        nama: String,
        role: String
    ) {

        pref.edit()
            .putBoolean("isLogin", true)
            .putString("id_user", idUser)
            .putString("nama", nama)
            .putString("role", role)
            .apply()
    }

    fun isLogin(): Boolean {
        return pref.getBoolean(
            "isLogin",
            false
        )
    }

    fun getIdUser(): String {
        return pref.getString(
            "id_user",
            ""
        ) ?: ""
    }

    fun getNama(): String {
        return pref.getString(
            "nama",
            ""
        ) ?: ""
    }

    fun getRole(): String {
        return pref.getString(
            "role",
            ""
        ) ?: ""
    }

    fun logout() {
        pref.edit().clear().apply()
    }
}