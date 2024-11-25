package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookVenueApi
import com.example.mezbaan.model.api.GetVenueApi
import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.models.Data
import com.example.mezbaan.model.requests.VenueReq
import com.example.mezbaan.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import androidx.compose.runtime.State

@HiltViewModel
class VenueViewModel @Inject constructor(
    private val getvenueapi: GetVenueApi,
    private val getbookingvenueapi: BookVenueApi
) : ViewModel() {

    private val _venuebookingresult = MutableLiveData<NetworkResponse<VenueReq>>()
    val venuebookingresult: LiveData<NetworkResponse<VenueReq>> = _venuebookingresult

    private val _venues = MutableStateFlow<List<Data>>(emptyList())
    val venues: StateFlow<List<Data>> = _venues

    private val _isDialogVisible = MutableStateFlow<Boolean>(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    fun bookvenue(venuebook: VenueBook, token: String) {
        Log.d("Booking", "Venues Updated: $token, ${venuebook.venueId}, ${venuebook.date}, ${venuebook.endTime}, ${venuebook.startTime}. ${venuebook.guestCount}, ${venuebook.bill}")
        _venuebookingresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
                    val response = getbookingvenueapi.venueBooking(token = token, venuereq = venuebook)
                    Log.d("Booking", "${response.code()}")
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _venuebookingresult.postValue(NetworkResponse.Success(it))
                            _isDialogVisible.value = true
                            Log.d("Booking", "Venues Updated: done")
                        }
                    } else {
                        _venuebookingresult.postValue(NetworkResponse.Failure("Request Failed"))
                        Log.d("Booking", "Venues Updated: failed")
                    }
                }
            } catch (e: Exception) {
                _venuebookingresult.postValue(NetworkResponse.Failure("Request Failed: ${e.message}"))
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

    fun closeDialog() {
        _isDialogVisible.value = false
    }
}