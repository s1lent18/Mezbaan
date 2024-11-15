package com.example.mezbaan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.RetrofitInstance
import com.example.mezbaan.model.dataclasses.CateringBook
import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.models.CateringReqHandle
import com.example.mezbaan.model.models.VenueReqHandle
import com.example.mezbaan.model.response.NetworkResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class CateringViewModel : ViewModel() {

    private val cateringapi = RetrofitInstance.cateringapi
    private val _cateringbookingresult = MutableLiveData<NetworkResponse<CateringReqHandle>>()
    val cateringbookingresult: LiveData<NetworkResponse<CateringReqHandle>> = _cateringbookingresult

    fun bookcatering(cateringbook: CateringBook) {
        _cateringbookingresult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = cateringapi.cateringBooking(cateringbook)
                withTimeout(15_000) {
                    _cateringbookingresult.value = NetworkResponse.Failure("Request Failed")
                }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _cateringbookingresult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _cateringbookingresult.value = NetworkResponse.Failure("Request Failed")
                }
            } catch (e: Exception) {
                _cateringbookingresult.value = NetworkResponse.Failure("Request Failed")
            }
        }
    }
}