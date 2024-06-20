package com.example.ktornoteapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ktornoteapp.utils.Constants.JWT_TOKEN_KEY
import kotlinx.coroutines.flow.first

class SessionManager(val context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("sessionManager")

    suspend fun saveToken(token: String) {
        val jwtTokenKey = stringPreferencesKey(JWT_TOKEN_KEY)
        context.datastore.edit { preference ->
            preference[jwtTokenKey] = token
        }
    }

    suspend fun getToken(): String? { // Not nullable string
        val jwtToken = stringPreferencesKey(JWT_TOKEN_KEY)
        val preference = context.datastore.data.first()
        return preference[jwtToken]
    }
}