package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookCateringApi
import com.example.mezbaan.model.api.GetCateringApi
import com.example.mezbaan.model.dataclasses.CateringBook
import com.example.mezbaan.model.models.DataXX
import com.example.mezbaan.model.requests.BookingReq
import com.example.mezbaan.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class CateringViewModel @Inject constructor(
    private val getcateringapi : GetCateringApi,
    private val bookcateringapi : BookCateringApi
) : ViewModel() {

    init {
        fetchCatering()
    }

    private val _cateringbookresult = MutableLiveData<NetworkResponse<BookingReq>>()
    val cateringbookresult: LiveData<NetworkResponse<BookingReq>> = _cateringbookresult

    private val _menu = MutableStateFlow<List<DataXX>>(emptyList())
    val menu: StateFlow<List<DataXX>> = _menu

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    fun bookcatering(cateringbook: CateringBook, token: String) {
        Log.d("Booking", "Response Code: $token, " +
                "${cateringbook.cateringServiceId}," +
                " ${cateringbook.bill}," +
                " ${cateringbook.date}" +
                " ${cateringbook.endTime}" +
                " ${cateringbook.address}" +
                " ${cateringbook.guestCount}," +
                " ${cateringbook.menuItemIds}" +
                " ${cateringbook.packageIds}" +
                " ${cateringbook.startTime}")
        _cateringbookresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
                    val response = bookcateringapi.cateringBooking(token = token, cateringreq = cateringbook)
                    Log.d("Booking", "${response.code()}")
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _cateringbookresult.postValue(NetworkResponse.Success(it))
                            _isDialogVisible.value = true
                            Log.d("Booking", "Venues Updated: done")
                        }
                    } else {
                        _cateringbookresult.postValue(NetworkResponse.Failure("Request Failed"))
                        Log.d("Booking", "Venues Updated: failed")
                    }
                }
            } catch (e: Exception) {
                _cateringbookresult.postValue(NetworkResponse.Failure("Request Failed: ${e.message}"))
            }
        }
    }

    private fun fetchCatering() {
        viewModelScope.launch {
            try {
                val response = getcateringapi.getCatering()
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _menu.value = decoratorResponse.data
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