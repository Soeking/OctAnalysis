package net.soeki.octanalysis.logic

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.apollographql.apollo3.api.Optional
import net.graphql.type.ObtainJSONWebTokenInput

class Auth(private val context: Context) {
    private var masterKey: MasterKey =
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    private var userPref: SharedPreferences
    private val userPrefName = "user_pref"
    private var tokenPref: SharedPreferences
    private val tokenPrefName = "token_pref"

    init {
        userPref = EncryptedSharedPreferences.create(
            context,
            userPrefName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        tokenPref = EncryptedSharedPreferences.create(
            context,
            tokenPrefName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    public fun tryAutoLogin(): Boolean {
        val email = userPref.getString("email", "nothing")
        val password = userPref.getString("password", "nothing")

        if (email != null && password != null) {
            login(email, password)
            return true
        }
        return false
    }

    public fun screenLogin(email: String, pass: String): Boolean {
        val result = login(email, pass)
        if (result) {
            writeUserInfo(email, pass)
        }
        return result
    }

    private fun login(email: String, pass: String): Boolean {
        val json = ObtainJSONWebTokenInput(Optional.present(email), Optional.present(pass))
        val token = CallAPI().doLogin(json)

        return if (token == null) false
        else {
            writeToken(token.token!!, token.refreshToken!!)
            true
        }
    }

    private fun writeUserInfo(email: String, pass: String) {
        with(userPref.edit()) {
            putString("email", email)
            putString("password", pass)
            apply()
        }
    }

    private fun writeToken(token: String, refresh: String) {
        with(tokenPref.edit()) {
            putString("token", token)
            putString("refresh", refresh)
            apply()
        }
    }
}