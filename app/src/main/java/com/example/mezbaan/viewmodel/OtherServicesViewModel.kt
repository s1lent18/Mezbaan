package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookOtherServicesApi
import com.example.mezbaan.model.api.DeleteOtherServicesBookingApi
import com.example.mezbaan.model.api.EditOtherServicesBookingApi
import com.example.mezbaan.model.api.GetOtherServicesApi
import com.example.mezbaan.model.api.GetSingleOtherServicesApi
import com.example.mezbaan.model.api.GetSingleOtherServicesBookingApi
import com.example.mezbaan.model.dataclasses.DecoratorBook
import com.example.mezbaan.model.dataclasses.OtherServicesBook
import com.example.mezbaan.model.models.DataXXXXX
import com.example.mezbaan.model.models.DataXXXXXXX
import com.example.mezbaan.model.models.DataXXXXXXXXX
import com.example.mezbaan.model.models.SingleCateringBookingModel
import com.example.mezbaan.model.models.SingleOtherServicesBookingModel
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
class OtherServicesViewModel @Inject constructor(
    private val getotherservicesapi : GetOtherServicesApi,
    private val bookotherservicesapi : BookOtherServicesApi,
    private val getsingleotherservicesapi : GetSingleOtherServicesApi,
    private val editotherservicesbookingapi : EditOtherServicesBookingApi,
    private val deleteotherservicebookingapi : DeleteOtherServicesBookingApi,
    private val getsingleotherservicesbookingapi : GetSingleOtherServicesBookingApi
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

    private val _singleOtherServices = MutableStateFlow<DataXXXXXXXXX?>(null)
    val singleOtherServices: StateFlow<DataXXXXXXXXX?> = _singleOtherServices

    private val _deletebookingresult = MutableLiveData<NetworkResponse<DeleteReq>>()
    val deletebookingresult: LiveData<NetworkResponse<DeleteReq>> = _deletebookingresult

    private val _editbookingresult = MutableLiveData<NetworkResponse<EditReq>>()
    val editbookingresult: LiveData<NetworkResponse<EditReq>> = _editbookingresult

    private val _singleOtherServicesBooking = MutableStateFlow<SingleOtherServicesBookingModel?>(null)
    val singleOtherServicesBooking: StateFlow<SingleOtherServicesBookingModel?> = _singleOtherServicesBooking

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

    fun getsinglevendor(id: Int) {
        viewModelScope.launch {
            try {
                val response = getsingleotherservicesapi.getSingleVendor(id = id)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _singleOtherServices.value = decoratorResponse.data
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
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

    fun fetchSingleOtherServicesBooking(id: Int, token: String) {
        Log.d("API Response", "$id, $token")
        viewModelScope.launch {
            try {
                val response = getsingleotherservicesbookingapi.getSingleOtherServicesBooking(id, token = token)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        Log.e("API Response", "Failed: ${response.code()}")
                        _singleOtherServicesBooking.value = decoratorResponse
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    fun deleteVendorBooking(id: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = deleteotherservicebookingapi.deleteSingleVendor(id = id, token = token)
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

    fun editvendor(otherservicesbook : OtherServicesBook, token: String, id: Int) {
        _editbookingresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
//                    Log.d("Booking", "$decoratorbook")
//                    Log.d("Booking", "$id")
//                    Log.d("Booking", token)
                    val response = editotherservicesbookingapi.editOtherServicesBooking(id = id, token = token, otherservicesreq = otherservicesbook)

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