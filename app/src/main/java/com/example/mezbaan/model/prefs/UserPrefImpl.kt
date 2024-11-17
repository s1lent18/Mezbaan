package com.example.mezbaan.model.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPrefImpl (private val dataStore: DataStore<Preferences>) : UserPref {

    override fun getToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[USER_KEY]?: ""
        }
    }

    override fun getUsername(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[USERNAME_KEY]?: ""
        }
    }

    override fun getEmail(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[EMAIL_KEY]?: ""
        }
    }

    override fun getPhone(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[PHONE_KEY]?: ""
        }
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit {
            it[USER_KEY] = token
        }
    }

    override suspend fun saveEmail(email: String) {
        dataStore.edit {
            it[EMAIL_KEY] = email
        }
    }

    override suspend fun saveUsername(username: String) {
        dataStore.edit {
            it[USERNAME_KEY] = username
        }
    }

    override suspend fun savePhone(phone: String) {
        dataStore.edit {
            it[PHONE_KEY] = phone
        }
    }
}