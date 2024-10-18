package com.example.mezbaan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.LoginApi
import com.example.mezbaan.model.dataclasses.LoginHandle
import com.example.mezbaan.model.dataclasses.LoginReq
import com.example.mezbaan.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginApi: LoginApi
) : ViewModel() {

    private val _loginresult = MutableLiveData<NetworkResponse<LoginHandle>>()
    val loginresult: LiveData<NetworkResponse<LoginHandle>> = _loginresult

    fun login(loginReq: LoginReq) {
        _loginresult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = loginApi.loginUser(loginReq)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginresult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _loginresult.value = NetworkResponse.Failure("Wrong Username/Password")
                }
            } catch (e: Exception) {
                _loginresult.value = NetworkResponse.Failure("Server IrResponsive")
            }
        }
    }
}