package com.example.mezbaan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.RetrofitInstance
import com.example.mezbaan.model.dataclasses.PhotographerBook
import com.example.mezbaan.model.models.PhotographerReqHandle
import com.example.mezbaan.model.response.NetworkResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class PhotographerViewModel : ViewModel() {

    private val photographerapi = RetrofitInstance.photographerapi
    private val _photographerbookingresult = MutableLiveData<NetworkResponse<PhotographerReqHandle>>()
    val photographerbookingresult: LiveData<NetworkResponse<PhotographerReqHandle>> = _photographerbookingresult

    fun bookphotographer(photographerbook: PhotographerBook) {
        _photographerbookingresult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = photographerapi.photographerBooking(photographerbook)
                withTimeout(15_000) {
                    _photographerbookingresult.value = NetworkResponse.Failure("Request Failed")
                }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _photographerbookingresult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _photographerbookingresult.value = NetworkResponse.Failure("Request Failed")
                }
            } catch (e: Exception) {
                _photographerbookingresult.value = NetworkResponse.Failure("Request Failed")
            }
        }
    }
}