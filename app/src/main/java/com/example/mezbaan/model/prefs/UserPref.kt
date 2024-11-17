package com.example.mezbaan.model.prefs

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

val USERNAME_KEY = stringPreferencesKey("username")
val EMAIL_KEY = stringPreferencesKey("email")
val PHONE_KEY = stringPreferencesKey("phone")
val USER_KEY = stringPreferencesKey("token")

interface UserPref {

    fun getToken(): Flow<String>
    fun getUsername(): Flow<String>
    fun getEmail(): Flow<String>
    fun getPhone(): Flow<String>

    suspend fun saveToken(token: String)
    suspend fun saveUsername(username: String)
    suspend fun saveEmail(email: String)
    suspend fun savePhone(phone: String)
}