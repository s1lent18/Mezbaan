package com.example.mezbaan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.RetrofitInstance
import com.example.mezbaan.model.dataclasses.SignUpReq
import com.example.mezbaan.model.models.SignUpHandle
import com.example.mezbaan.model.response.NetworkResponse
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {

    private val signupApi = RetrofitInstance.signupapi
    private val _signupresult = MutableLiveData<NetworkResponse<SignUpHandle>>()
    val signupresult: LiveData<NetworkResponse<SignUpHandle>> = _signupresult

    fun signup(signupreq: SignUpReq) {
        _signupresult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = signupApi.signupUser(signupreq)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _signupresult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _signupresult.value = NetworkResponse.Failure("Wrong Username/Password")
                }
            } catch (e: Exception) {
                _signupresult.value = NetworkResponse.Failure("Server IrResponsive")
            }
        }
    }
}