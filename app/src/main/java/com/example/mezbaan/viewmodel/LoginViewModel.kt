package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.RetrofitInstance
import com.example.mezbaan.model.dataclasses.LoginReq
import com.example.mezbaan.model.models.LoginHandle
import com.example.mezbaan.model.response.NetworkResponse
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginApi = RetrofitInstance.loginapi
    private val _loginresult = MutableLiveData<NetworkResponse<LoginHandle>>()
    val loginresult: LiveData<NetworkResponse<LoginHandle>> = _loginresult

    fun login(loginReq: LoginReq) {
        _loginresult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = loginApi.loginUser(loginReq)
                Log.d("LoginViewModel", "HTTP response code: ${response.code()}")
                if (response.isSuccessful && response.code() == 200) {
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