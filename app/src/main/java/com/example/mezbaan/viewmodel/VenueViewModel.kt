package com.example.mezbaan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.RetrofitInstance
import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.models.VenueReqHandle
import com.example.mezbaan.model.response.NetworkResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class VenueViewModel : ViewModel() {

    private val venueapi = RetrofitInstance.venueapi
    private val _venuebookingresult = MutableLiveData<NetworkResponse<VenueReqHandle>>()
    val venuebookingresult: LiveData<NetworkResponse<VenueReqHandle>> = _venuebookingresult

    fun bookvenue(venuebook: VenueBook) {
        _venuebookingresult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = venueapi.venueBooking(venuebook)
                withTimeout(15_000) {
                    _venuebookingresult.value = NetworkResponse.Failure("Request Failed")
                }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _venuebookingresult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _venuebookingresult.value = NetworkResponse.Failure("Request Failed")
                }
            } catch (e: Exception) {
                _venuebookingresult.value = NetworkResponse.Failure("Request Failed")
            }
        }
    }
}