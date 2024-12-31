package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookVenueApi
import com.example.mezbaan.model.api.DeleteVenueBookingApi
import com.example.mezbaan.model.api.EditVenueBookingApi
import com.example.mezbaan.model.api.GetSingleVenueApi
import com.example.mezbaan.model.api.GetSingleVenueBookingApi
import com.example.mezbaan.model.api.GetVenueApi
import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.models.Data
import com.example.mezbaan.model.models.DataXXXXXX
import com.example.mezbaan.model.models.DataXXXXXXX
import com.example.mezbaan.model.models.SingleVenueBookingModel
import com.example.mezbaan.model.requests.BookingReq
import com.example.mezbaan.model.requests.DeleteReq
import com.example.mezbaan.model.requests.EditReq
import com.example.mezbaan.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class VenueViewModel @Inject constructor(
    private val getvenueapi : GetVenueApi,
    private val getbookingvenueapi : BookVenueApi,
    private val getsinglevenueapi : GetSingleVenueApi,
    private val editvenuebookingapi : EditVenueBookingApi,
    private val deletevenuebookingapi : DeleteVenueBookingApi,
    private val getsinglevenuebookingapi : GetSingleVenueBookingApi
) : ViewModel() {

    private val _venuebookingresult = MutableLiveData<NetworkResponse<BookingReq>>()
    val venuebookingresult: LiveData<NetworkResponse<BookingReq>> = _venuebookingresult

    private val _venues = MutableStateFlow<List<Data>>(emptyList())
    val venues: StateFlow<List<Data>> = _venues

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    private val _singleVenue = MutableStateFlow<DataXXXXXX?>(null)
    val singleVenue: StateFlow<DataXXXXXX?> = _singleVenue

    private val _singleVenueBooking = MutableStateFlow<SingleVenueBookingModel?>(null)
    val singleVenueBooking: StateFlow<SingleVenueBookingModel?> = _singleVenueBooking

    private val _deletebookingresult = MutableLiveData<NetworkResponse<DeleteReq>>()
    val deletebookingresult: LiveData<NetworkResponse<DeleteReq>> = _deletebookingresult

    private val _editbookingresult = MutableLiveData<NetworkResponse<EditReq>>()
    val editbookingresult: LiveData<NetworkResponse<EditReq>> = _editbookingresult

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

    fun getsinglevenue(id: Int) {
        viewModelScope.launch {
            try {
                val response = getsinglevenueapi.getSingleVenue(id = id)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _singleVenue.value = decoratorResponse.data
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    fun fetchSingleVenueBooking(id: Int, token: String) {
        Log.d("API Response", "$id, $token")
        viewModelScope.launch {
            try {
                val response = getsinglevenuebookingapi.getSingleVenueBooking(id, token = token)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        Log.e("API Response", "Failed: ${response.code()}")
                        _singleVenueBooking.value = decoratorResponse
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    fun fetchVenues() {
        viewModelScope.launch {
            try {
                val response = getvenueapi.getVenues()
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

    fun deleteVenueBooking(id: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = deletevenuebookingapi.deleteSingleVenue(id = id, token = token)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.e("API Response", "Failed: ${response.code()}")
                        _deletebookingresult.postValue(NetworkResponse.Success(it))
                    }
                } else {
                    _deletebookingresult.postValue(NetworkResponse.Failure("Request Failed"))
                }
            } catch (e: Exception) {
                _deletebookingresult.postValue(NetworkResponse.Failure("Request Failed"))
            }
        }
    }

    fun editvenue(venuebook: VenueBook, token: String, id: Int) {
        _editbookingresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
//                    Log.d("Booking", "$decoratorbook")
//                    Log.d("Booking", "$id")
//                    Log.d("Booking", token)
                    val response = editvenuebookingapi.editVenueBooking(id = id, token = token, venuereq = venuebook)

                    Log.d("Booking", "${response.code()}")
                    Log.d("Booking", "${response.body()}")
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _editbookingresult.postValue(NetworkResponse.Success(it))
                            _isDialogVisible.value = true
                            Log.d("Booking", "Venues Updated: done")
                        }
                    } else {
                        _editbookingresult.postValue(NetworkResponse.Failure("Request Failed"))
                        Log.d("Booking", "Venues Updated: failed")
                    }
                }
            } catch (e: Exception) {
                _editbookingresult.postValue(NetworkResponse.Failure("Request Failed: ${e.message}"))
            }
        }
    }

    fun closeDialog() {
        _isDialogVisible.value = false
    }
}