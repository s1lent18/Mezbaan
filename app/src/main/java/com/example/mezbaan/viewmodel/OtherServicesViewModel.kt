package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookOtherServicesApi
import com.example.mezbaan.model.api.GetOtherServicesApi
import com.example.mezbaan.model.dataclasses.OtherServicesBook
import com.example.mezbaan.model.models.DataXXXXX
import com.example.mezbaan.model.requests.BookingReq
import com.example.mezbaan.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class OtherServicesViewModel @Inject constructor(
    private val getotherservicesapi : GetOtherServicesApi,
    private val bookotherservicesapi : BookOtherServicesApi
) : ViewModel() {

    init {
        fetchOtherService()
    }

    private val _otherservicebookresult = MutableLiveData<NetworkResponse<BookingReq>>()
    val otherservicebookresult: LiveData<NetworkResponse<BookingReq>> = _otherservicebookresult

    private val _vendors = MutableStateFlow<List<DataXXXXX>>(emptyList())
    val vendors: StateFlow<List<DataXXXXX>> = _vendors

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    fun bookotherservice(otherservicebook: OtherServicesBook, token: String) {
        
        _otherservicebookresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
                    val response = bookotherservicesapi.otherservicesBooking(token = token, otherservicesreq = otherservicebook)
                    Log.d("Booking", "${response.code()}")
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _otherservicebookresult.postValue(NetworkResponse.Success(it))
                            _isDialogVisible.value = true
                            Log.d("Booking", "Venues Updated: done")
                        }
                    } else {
                        _otherservicebookresult.postValue(NetworkResponse.Failure("Request Failed"))
                        Log.d("Booking", "Venues Updated: failed")
                    }
                }
            } catch (e: Exception) {
                _otherservicebookresult.postValue(NetworkResponse.Failure("Request Failed: ${e.message}"))
            }
        }
    }

    private fun fetchOtherService() {
        viewModelScope.launch {
            try {
                val response = getotherservicesapi.getOtherServices()
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _vendors.value = decoratorResponse.data
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