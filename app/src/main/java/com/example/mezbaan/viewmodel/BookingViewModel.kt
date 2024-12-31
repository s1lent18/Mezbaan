package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.GetBookingsApi
import com.example.mezbaan.model.models.Booking
import com.example.mezbaan.model.prefs.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val getbookingapi : GetBookingsApi,
    private val userpref : UserPref
) : ViewModel() {

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings

    init {
        fetchBookings()
    }

    private fun fetchBookings() {
        viewModelScope.launch {
            try {
                val token = userpref.getToken().firstOrNull()
                val bearerToken = "Bearer $token"
                val response = getbookingapi
                    .getBookings(token = bearerToken)
                Log.d("bookings: ", bearerToken)
                if (response.isSuccessful) {
                    response.body()?.let { bookingResponse ->
                        Log.d("bookings", "${bookingResponse.bookings}")
                        _bookings.value = bookingResponse.bookings
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