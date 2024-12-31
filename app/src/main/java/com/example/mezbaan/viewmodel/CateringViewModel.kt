package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookCateringApi
import com.example.mezbaan.model.api.DeleteCateringBookingApi
import com.example.mezbaan.model.api.EditCateringBookingApi
import com.example.mezbaan.model.api.GetCateringApi
import com.example.mezbaan.model.api.GetSingleCateringApi
import com.example.mezbaan.model.api.GetSingleCateringBookingApi
import com.example.mezbaan.model.dataclasses.CateringBook
import com.example.mezbaan.model.dataclasses.DecoratorBook
import com.example.mezbaan.model.models.DataXX
import com.example.mezbaan.model.models.DataXXXXXXX
import com.example.mezbaan.model.models.DataXXXXXXXX
import com.example.mezbaan.model.models.SingleCateringBookingModel
import com.example.mezbaan.model.models.SinglePhotographerBookingModel
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
class CateringViewModel @Inject constructor(
    private val getcateringapi : GetCateringApi,
    private val bookcateringapi : BookCateringApi,
    private val getsinglecateringapi: GetSingleCateringApi,
    private val editcateringbookingapi : EditCateringBookingApi,
    private val deletecateringbookingapi : DeleteCateringBookingApi,
    private val getsinglecateringbookingapi : GetSingleCateringBookingApi

) : ViewModel() {

    init {
        fetchCatering()
    }

    private val _cateringbookresult = MutableLiveData<NetworkResponse<BookingReq>>()
    val cateringbookresult: LiveData<NetworkResponse<BookingReq>> = _cateringbookresult

    private val _menu = MutableStateFlow<List<DataXX>>(emptyList())
    val menu: StateFlow<List<DataXX>> = _menu

    private val _singleCatering = MutableStateFlow<DataXXXXXXXX?>(null)
    val singleCatering: StateFlow<DataXXXXXXXX?> = _singleCatering

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    private val _singleCateringBooking = MutableStateFlow<SingleCateringBookingModel?>(null)
    val singleCateringBooking: StateFlow<SingleCateringBookingModel?> = _singleCateringBooking

    private val _deletebookingresult = MutableLiveData<NetworkResponse<DeleteReq>>()
    val deletebookingresult: LiveData<NetworkResponse<DeleteReq>> = _deletebookingresult

    private val _editbookingresult = MutableLiveData<NetworkResponse<EditReq>>()
    val editbookingresult: LiveData<NetworkResponse<EditReq>> = _editbookingresult

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

    fun fetchSingleCateringBooking(id: Int, token: String) {
        Log.d("API Response", "$id, $token")
        viewModelScope.launch {
            try {
                val response = getsinglecateringbookingapi.getSingleCateringBooking(id, token = token)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        Log.e("API Response", "Failed: ${response.code()}")
                        _singleCateringBooking.value = decoratorResponse
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    fun getsinglecatering(id: Int) {
        viewModelScope.launch {
            try {
                val response = getsinglecateringapi.getSingleCatering(id = id)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _singleCatering.value = decoratorResponse.data
                        Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
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

    fun deleteDecoratorBooking(id: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = deletecateringbookingapi.deleteSingleCatering(id = id, token = token)
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

    fun editdecorator(cateringbook : CateringBook, token: String, id: Int) {
        _editbookingresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
//                    Log.d("Booking", "$decoratorbook")
//                    Log.d("Booking", "$id")
//                    Log.d("Booking", token)
                    val response = editcateringbookingapi.editCateringBooking(id = id, token = token, cateringreq = cateringbook)

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