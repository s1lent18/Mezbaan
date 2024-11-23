package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.GetDecoratorApi
import com.example.mezbaan.model.models.DataX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DecoratorViewModel @Inject constructor(
    private val getdecoratorapi : GetDecoratorApi
) : ViewModel() {

    init {
        fetchDecorators()
    }

    private val _decorators = MutableStateFlow<List<DataX>>(emptyList())
    val decorators: StateFlow<List<DataX>> = _decorators

    fun fetchDecorators(limit: Int = 20, page: Int = 1) {
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
}