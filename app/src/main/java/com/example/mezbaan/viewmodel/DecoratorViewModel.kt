package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookDecoratorApi
import com.example.mezbaan.model.api.DeleteDecoratorBookingApi
import com.example.mezbaan.model.api.EditDecoratorBookingApi
import com.example.mezbaan.model.api.GetDecoratorApi
import com.example.mezbaan.model.api.GetSingleDecorationApi
import com.example.mezbaan.model.api.GetSingleDecoratorBookingApi
import com.example.mezbaan.model.dataclasses.DecoratorBook
import com.example.mezbaan.model.models.DataX
import com.example.mezbaan.model.models.DataXXXXXXX
import com.example.mezbaan.model.models.DataXXXXXXXXXX
import com.example.mezbaan.model.models.SingleDecoratorBookingModel
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
class DecoratorViewModel @Inject constructor(
    private val getdecoratorapi : GetDecoratorApi,
    private val bookdecoratorapi : BookDecoratorApi,
    private val getsingledecoratorapi: GetSingleDecorationApi,
    private val editdecoratorbookingapi: EditDecoratorBookingApi,
    private val deletedecoratorbookingapi : DeleteDecoratorBookingApi,
    private val getsingledecoratorbookingapi : GetSingleDecoratorBookingApi
) : ViewModel() {

    init {
        fetchDecorators()
    }

    private val _decoratorbookresult = MutableLiveData<NetworkResponse<BookingReq>>()
    val decoratorbookresult: LiveData<NetworkResponse<BookingReq>> = _decoratorbookresult

    private val _decorators = MutableStateFlow<List<DataX>>(emptyList())
    val decorators: StateFlow<List<DataX>> = _decorators

    private val _singleDecorator = MutableStateFlow<DataXXXXXXXXXX?>(null)
    val singleDecorator: StateFlow<DataXXXXXXXXXX?> = _singleDecorator

    private val _singleDecoratorBooking = MutableStateFlow<SingleDecoratorBookingModel?>(null)
    val singleDecoratorBooking: StateFlow<SingleDecoratorBookingModel?> = _singleDecoratorBooking

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    private val _deletebookingresult = MutableLiveData<NetworkResponse<DeleteReq>>()
    val deletebookingresult: LiveData<NetworkResponse<DeleteReq>> = _deletebookingresult

    private val _editbookingresult = MutableLiveData<NetworkResponse<EditReq>>()
    val editbookingresult: LiveData<NetworkResponse<EditReq>> = _editbookingresult

    fun bookdecorator(decoratorbook : DecoratorBook, token: String) {
        _decoratorbookresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
                    val response = bookdecoratorapi.decoratorBooking(token = token, decoratorreq = decoratorbook)
                    Log.d("Booking", "${response.code()}")
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _decoratorbookresult.postValue(NetworkResponse.Success(it))
                            _isDialogVisible.value = true
                            Log.d("Booking", "Venues Updated: done")
                        }
                    } else {
                        _decoratorbookresult.postValue(NetworkResponse.Failure("Request Failed"))
                        Log.d("Booking", "Venues Updated: failed")
                    }
                }
            } catch (e: Exception) {
                _decoratorbookresult.postValue(NetworkResponse.Failure("Request Failed: ${e.message}"))
            }
        }
    }

    fun getsingledecoration(id: Int) {
        viewModelScope.launch {
            try {
                val response = getsingledecoratorapi.getSingleDecoration(id = id)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _singleDecorator.value = decoratorResponse.data
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    fun editdecorator(decoratorbook : DecoratorBook, token: String, id: Int) {
        _editbookingresult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                withTimeout(15_000) {
                    Log.d("Booking", "$decoratorbook")
                    Log.d("Booking", "$id")
                    Log.d("Booking", token)
                    val response = editdecoratorbookingapi.editDecoratorBooking(id = id, token = token, decoratorreq = decoratorbook)

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

    fun fetchSingleDecoratorBooking(id: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = getsingledecoratorbookingapi.getSingleDecoratorBooking(id, token = token)
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        Log.e("API Response", "Failed: ${response.code()}")
                        _singleDecoratorBooking.value = decoratorResponse
                    }
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    private fun fetchDecorators() {
        viewModelScope.launch {
            try {
                val response = getdecoratorapi.getDecorators()
                if (response.isSuccessful) {
                    response.body()?.let { decoratorResponse ->
                        _decorators.value = decoratorResponse.data
                        Log.d("API Response", "Venues Updated: ${decoratorResponse.data.size}")
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
                val response = deletedecoratorbookingapi.deleteSingleDecorator(id = id, token = token)
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

    fun closeDialog() {
        _isDialogVisible.value = false
    }
}