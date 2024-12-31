package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.RetrofitInstance
import com.example.mezbaan.model.requests.SignUpReq
import com.example.mezbaan.model.response.NetworkResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class SignupViewModel : ViewModel() {

    private val signupApi = RetrofitInstance.signupapi
    private val _signupresult = MutableLiveData<NetworkResponse<Unit>>()
    val signupresult: LiveData<NetworkResponse<Unit>> = _signupresult

    fun signup(signupreq: SignUpReq) {
        _signupresult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val username = signupreq.username.toRequestBody("text/plain".toMediaTypeOrNull())
                val email = signupreq.email.toRequestBody("text/plain".toMediaTypeOrNull())
                val phone = signupreq.phone.toRequestBody("text/plain".toMediaTypeOrNull())
                val password = signupreq.password.toRequestBody("text/plain".toMediaTypeOrNull())
                val roleId = signupreq.roleId.toRequestBody("text/plain".toMediaTypeOrNull())
                val response = signupApi.signupUser(
                    username = username,
                    email = email,
                    password = password,
                    phone = phone,
                    roleId = roleId,
                )
                Log.d("SignupViewModel", "HTTP response code: ${response.code()}")
                if (response.isSuccessful && response.code() == 201) {
                    response.body()?.let {
                        _signupresult.value = NetworkResponse.Success(Unit)
                    }
                } else {
                    _signupresult.value = NetworkResponse.Failure("User Already Exists")
                }
            } catch (e: Exception) {
                _signupresult.value = NetworkResponse.Failure("Server IrResponsive")
            }
        }
    }
}