package com.example.mezbaan.model.prefs

import android.util.Log
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

    override fun getImage(): Flow<String?> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[IMAGE_KEY]?: "https://imgs.search.brave.com/7_-25qcHnU9PLXYYiiK-IwkQx93yFpp__txSD1are3s/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly90NC5m/dGNkbi5uZXQvanBn/LzAwLzY0LzY3LzYz/LzM2MF9GXzY0Njc2/MzgzX0xkYm1oaU5N/NllwemIzRk00UFB1/RlA5ckhlN3JpOEp1/LmpwZw"
        }
    }

    override suspend fun saveCreatedAt(createdat: String) {
        Log.d("SaveCreatedAt", "Saving CreatedAt: $createdat")
        dataStore.edit {
            it[CREATEDAT_KEY] = createdat
        }
    }

    override fun getCreatedAt(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[CREATEDAT_KEY]?: ""

        }
    }

    override fun getTimeStamp(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[TIMESTAMP_KEY]?: ""
        }
    }

    override suspend fun saveTimeStamp(timestamp: String) {
        dataStore.edit {
            it[TIMESTAMP_KEY] = timestamp
        }
    }

    override suspend fun saveImage(image: String?) {
        dataStore.edit {
            if (image != null) {
                it[IMAGE_KEY] = image.toString()
            }
            else {
                it.remove(IMAGE_KEY)
            }

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