package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.BookDecoratorApi
import com.example.mezbaan.model.api.GetDecoratorApi
import com.example.mezbaan.model.dataclasses.DecoratorBook
import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.models.DataX
import com.example.mezbaan.model.requests.BookingReq
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
    private val bookdecoratorapi : BookDecoratorApi
) : ViewModel() {

    init {
        fetchDecorators()
    }

    private val _decoratorbookresult = MutableLiveData<NetworkResponse<BookingReq>>()
    val decoratorbookresult: LiveData<NetworkResponse<BookingReq>> = _decoratorbookresult

    private val _decorators = MutableStateFlow<List<DataX>>(emptyList())
    val decorators: StateFlow<List<DataX>> = _decorators

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    fun bookvenue(decoratorbook : DecoratorBook, token: String) {
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

    private fun fetchDecorators(limit: Int = 20, page: Int = 1) {
        viewModelScope.launch {
            try {
                val response = getdecoratorapi.getDecorators(limit, page)
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

    fun closeDialog() {
        _isDialogVisible.value = false
    }
}