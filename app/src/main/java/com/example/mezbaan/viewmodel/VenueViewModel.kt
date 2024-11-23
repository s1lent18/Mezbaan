package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.GetVenueApi
import com.example.mezbaan.model.api.RetrofitInstance
import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.models.Data
import com.example.mezbaan.model.models.VenueModel
import com.example.mezbaan.model.models.VenueReqHandle
import com.example.mezbaan.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class VenueViewModel @Inject constructor(
    private val getvenueapi: GetVenueApi
) : ViewModel() {

    private val venueapi = RetrofitInstance.venueapi
    private val _venuebookingresult = MutableLiveData<NetworkResponse<VenueReqHandle>>()
    val venuebookingresult: LiveData<NetworkResponse<VenueReqHandle>> = _venuebookingresult

    private val _venues = MutableStateFlow<List<Data>>(emptyList())
    val venues: StateFlow<List<Data>> = _venues

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

    fun fetchVenues(limit: Int = 20, page: Int = 1) {
        viewModelScope.launch {
            try {
                val response = getvenueapi.getVenues(limit, page)
                if (response.isSuccessful) {
                    response.body()?.let { venueResponse ->
                        _venues.value = venueResponse.data
                        Log.d("API Response", "Venues Updated: ${venueResponse.data.size}")
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