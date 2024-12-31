package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookPhotographerApi
import com.example.mezbaan.model.api.DeletePhotographerBookingApi
import com.example.mezbaan.model.api.EditPhotographerBookingApi
import com.example.mezbaan.model.api.GetPhotographerApi
import com.example.mezbaan.model.api.GetSinglePhotographerApi
import com.example.mezbaan.model.api.GetSinglePhotographerBookingApi
import com.example.mezbaan.model.dataclasses.PhotographerBook
import com.example.mezbaan.model.models.DataXXX
import com.example.mezbaan.model.models.DataXXXX
import com.example.mezbaan.model.models.DataXXXXXXX
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
class PhotographerViewModel @Inject constructor(
    private val getphotographerapi : GetPhotographerApi,
    private val bookphotographerapi : BookPhotographerApi,
    private val getsinglephotographerapi : GetSinglePhotographerApi,
    private val editphotographerbookingapi : EditPhotographerBookingApi,
    private val deletephotographerbookingapi : DeletePhotographerBookingApi,
    private val getsinglephotographerbookingapi : GetSinglePhotographerBookingApi
) : ViewModel() {

    private val _photographerbookingresult = MutableLiveData<NetworkResponse<BookingReq>>()
    val photographerbookingresult: LiveData<NetworkResponse<BookingReq>> = _photographerbookingresult

    private val _photographers = MutableStateFlow<List<DataXXX>>(emptyList())
    val photographers: StateFlow<List<DataXXX>> = _photographers

    private val _singlePhotographer = MutableStateFlow<DataXXXXXXX?>(null)
    val singlePhotographer: StateFlow<DataXXXXXXX?> = _singlePhotographer

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    private val _singlePhotographerBooking = MutableStateFlow<SinglePhotographerBookingModel?>(null)
    val singlePhotographerBooking: StateFlow<SinglePhotographerBookingModel?> = _singlePhotographerBooking

    private val _deletebookingresult = MutableLiveData<NetworkResponse<DeleteReq>>()
    val deletebookingresult: LiveData<NetworkResponse<DeleteReq>> = _deletebookingresult

    private val _editbookingresult = MutableLiveData<NetworkResponse<EditReq>>()
    val editbookingresult: LiveData<NetworkResponse<EditReq>> = _editbookingresult

    init {
        fetchPhotographers()
    }

    fun bookphotographer(photographerbook: PhotographerBook, token: String) {
        _photographerbookingresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
                    val response = bookphotographerapi.photographerBooking(token = token, photographerreq = photographerbook)
                    Log.d("Booking", "${response.code()}")
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _photographerbookingresult.postValue(NetworkResponse.Success(it))
                            _isDialogVisible.value = true
                            Log.d("Booking", "Venues Updated: done")
                        }
                    } else {
                        _photographerbookingresult.postValue(NetworkResponse.Failure("Request Failed"))
                        Log.d("Booking", "Venues Updated: failed")
                    }
                }
            } catch (e: Exception) {
                _photographerbookingresult.postValue(NetworkResponse.Failure("Request Failed: ${e.message}"))
            }
        }
    }

    fun getsinglephotographer(id: Int) {
        viewModelScope.launch {
            try {
                val response = getsinglephotographerapi.getSinglePhotography(id = id)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _singlePhotographer.value = decoratorResponse.data
                        Log.d("API Response", "Failed: ${response.errorBody()?.string()}")
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    private fun fetchPhotographers() {
        viewModelScope.launch {
            try {
                val response = getphotographerapi.getPhotographers()
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _photographers.value = decoratorResponse.data
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    fun fetchSinglePhotographerBooking(id: Int, token: String) {
        Log.d("API Response", "$id, $token")
        viewModelScope.launch {
            try {
                val response = getsinglephotographerbookingapi.getSinglePhotographerBooking(id, token = token)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        Log.e("API Response", "Failed: ${response.code()}")
                        _singlePhotographerBooking.value = decoratorResponse
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    fun deletePhotographerBooking(id: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = deletephotographerbookingapi.deleteSinglePhotographer(id = id, token = token)
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

    fun editphotographer(photographerbook: PhotographerBook, token: String, id: Int) {
        _editbookingresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
//                    Log.d("Booking", "$decoratorbook")
//                    Log.d("Booking", "$id")
//                    Log.d("Booking", token)
                    val response = editphotographerbookingapi.editPhotographerBooking(id = id, token = token, photographerreq = photographerbook)

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