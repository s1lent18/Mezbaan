package com.example.mezbaan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.prefs.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val userpref: UserPref) : ViewModel() {

//    private val _username = MutableStateFlow("")
//    private val _email = MutableStateFlow("")
//    private val _phone = MutableStateFlow("")
//
//    val username: StateFlow<String> get() = _username
//    val email: StateFlow<String> get() = _email
//    val phone: StateFlow<String> get() = _phone

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

    init {
        startAutoLogoutTimer()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            userpref.saveToken(token)
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
        }
    }

    private fun startAutoLogoutTimer() {

        viewModelScope.launch {
            delay(3600000)
            logout()
        }
    }
}