package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookPhotographerApi
import com.example.mezbaan.model.api.GetPhotographerApi
import com.example.mezbaan.model.api.GetSinglePhotographerApi
import com.example.mezbaan.model.dataclasses.PhotographerBook
import com.example.mezbaan.model.models.DataXXX
import com.example.mezbaan.model.models.DataXXXX
import com.example.mezbaan.model.requests.BookingReq
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
    private val getsinglephotographerapi : GetSinglePhotographerApi
) : ViewModel() {

    private val _photographerbookingresult = MutableLiveData<NetworkResponse<BookingReq>>()
    val photographerbookingresult: LiveData<NetworkResponse<BookingReq>> = _photographerbookingresult

    private val _photographers = MutableStateFlow<List<DataXXX>>(emptyList())
    val photographers: StateFlow<List<DataXXX>> = _photographers

    private val _singlePhotographer = MutableStateFlow<DataXXXX?>(null)
    val singlePhotographer: StateFlow<DataXXXX?> = _singlePhotographer

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

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
                val response = getsinglephotographerapi.getPhotography(id = id)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _singlePhotographer.value = decoratorResponse.data
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

    fun closeDialog() {
        _isDialogVisible.value = false
    }
}