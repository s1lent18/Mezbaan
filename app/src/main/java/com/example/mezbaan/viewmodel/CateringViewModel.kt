package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.GetCateringApi
import com.example.mezbaan.model.models.DataXX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CateringViewModel @Inject constructor(
    private val getcateringapi : GetCateringApi
) : ViewModel() {

    init {
        fetchCatering()
    }

    private val _menu = MutableStateFlow<List<DataXX>>(emptyList())
    val menu: StateFlow<List<DataXX>> = _menu

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
}