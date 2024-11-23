package com.example.mezbaan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.prefs.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userpref: UserPref) : ViewModel() {

    private val sessionDurationMillis = TimeUnit.HOURS.toMillis(1)

    private val _session = MutableStateFlow(false)
    val session = _session
        .onStart {
            if (isSessionExpired()) {
                logout()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            initialValue = false
        )


    val token = userpref.getToken().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val username = userpref.getUsername().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val email = userpref.getEmail().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val phone = userpref.getPhone().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val image = userpref.getImage().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = "https://imgs.search.brave.com/7_-25qcHnU9PLXYYiiK-IwkQx93yFpp__txSD1are3s/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly90NC5m/dGNkbi5uZXQvanBn/LzAwLzY0LzY3LzYz/LzM2MF9GXzY0Njc2/MzgzX0xkYm1oaU5N/NllwemIzRk00UFB1/RlA5ckhlN3JpOEp1/LmpwZw"
    )

    val timestamp = userpref.getTimeStamp().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    init {
        startAutoLogoutTimer()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            userpref.saveToken(token)
        }
    }

    fun saveImage(image: String?) {
        viewModelScope.launch {
            if (image != null) {
                userpref.saveImage(image)
            }
        }
    }

    private fun saveTimeStamp() {
        viewModelScope.launch {
            val currentTimestamp = System.currentTimeMillis().toString()
            userpref.saveTimeStamp(currentTimestamp)
        }
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            userpref.saveUsername(username)
        }
    }

    fun saveEmail(email: String) {
        viewModelScope.launch {
            userpref.saveEmail(email)
        }
    }

    fun savePhone(phone: String) {
        viewModelScope.launch {
            userpref.savePhone(phone)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userpref.saveToken("")
            userpref.saveUsername("")
            userpref.saveEmail("")
            userpref.savePhone("")
            userpref.saveTimeStamp("")
        }
    }

    private suspend fun isSessionExpired(): Boolean {
        val loginTimestamp = userpref.getTimeStamp().first().toLongOrNull() ?: return true
        return (System.currentTimeMillis() - loginTimestamp) > sessionDurationMillis
    }

    fun saveUserData(token: String, name: String, email: String, phone: String, image: String?) {
        viewModelScope.launch {
            userpref.saveToken(token)
            userpref.saveUsername(name)
            userpref.saveEmail(email)
            userpref.savePhone(phone)
            if (image != null) {
                userpref.saveImage(image)
            }
            saveTimeStamp()
        }
    }

    private fun startAutoLogoutTimer() {

        viewModelScope.launch {
            delay(3600000)
            logout()
        }
    }
}