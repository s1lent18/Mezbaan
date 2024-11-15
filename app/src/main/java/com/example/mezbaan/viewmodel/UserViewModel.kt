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
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _username.value = "JohnDoe"
            _email.value = "johndoe@example.com"
            _phone.value = "+1234567890"
        }
    }
}