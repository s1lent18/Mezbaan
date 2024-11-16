package com.example.mezbaan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _username = MutableStateFlow<String>("")
    private val _email = MutableStateFlow<String>("")
    private val _phone = MutableStateFlow<String>("")

    val username: StateFlow<String> get() = _username
    val email: StateFlow<String> get() = _email
    val phone: StateFlow<String> get() = _phone

    init {
        setUserData(username = "d", "d", phone = "d")
    }

    fun setUserData(username: String, email: String, phone: String) {
        _username.value = username
        _email.value = email
        _phone.value = phone
    }
}