package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.GetPhotographerApi
import com.example.mezbaan.model.api.RetrofitInstance
import com.example.mezbaan.model.dataclasses.PhotographerBook
import com.example.mezbaan.model.models.DataX
import com.example.mezbaan.model.models.PhotographerReqHandle
import com.example.mezbaan.model.models.temp1
import com.example.mezbaan.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class PhotographerViewModel @Inject constructor(
    private val getphotographerapi : GetPhotographerApi
) : ViewModel() {

    private val photographerapi = RetrofitInstance.photographerapi
    private val _photographerbookingresult = MutableLiveData<NetworkResponse<PhotographerReqHandle>>()
    val photographerbookingresult: LiveData<NetworkResponse<PhotographerReqHandle>> = _photographerbookingresult

    private val _photographers = MutableStateFlow<List<temp1>>(emptyList())
    val photographers: StateFlow<List<temp1>> = _photographers

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



    private fun fetchPhotographers() {
        viewModelScope.launch {
            try {
                val response = getphotographerapi.getPhotographers(limit = 20, page = 1)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                       // _photographers.value = decoratorResponse.name
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }
}