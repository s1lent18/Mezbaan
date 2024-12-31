package com.example.mezbaan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.RatingApi
import com.example.mezbaan.model.dataclasses.Ratings
import com.example.mezbaan.model.models.Rating
import com.example.mezbaan.model.models.SingleCateringBookingModel
import com.example.mezbaan.model.prefs.UserPref
import com.example.mezbaan.model.requests.BookingReq
import com.example.mezbaan.model.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userpref: UserPref,
    private val ratingapi : RatingApi
) : ViewModel() {

    private val _ratings = MutableLiveData<NetworkResponse<Rating>>()
    val ratings: LiveData<NetworkResponse<Rating>> = _ratings

    private val sessionDurationMillis = TimeUnit.HOURS.toMillis(1)

    private val _session = MutableStateFlow(false)
    val session = _session
        .onStart {
            if (isSessionExpired()) {
                logout()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            initialValue = false
        )


    val token = userpref.getToken().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val username = userpref.getUsername().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val email = userpref.getEmail().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val phone = userpref.getPhone().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val image = userpref.getImage().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val timestamp = userpref.getTimeStamp().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    val createdAt = userpref.getCreatedAt().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    init {
        startAutoLogoutTimer()
    }

    fun saveCreatedAt(createdat: String) {
        viewModelScope.launch {
            userpref.saveCreatedAt(createdat)
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            userpref.saveToken(token)
        }
    }

    fun saveImage(image: String?) {
        viewModelScope.launch {
            if (image != null) {
                userpref.saveImage(image)
            }
        }
    }

    private fun saveTimeStamp() {
        viewModelScope.launch {
            val currentTimestamp = System.currentTimeMillis().toString()
            userpref.saveTimeStamp(currentTimestamp)
        }
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            userpref.saveUsername(username)
        }
    }

    fun saveEmail(email: String) {
        viewModelScope.launch {
            userpref.saveEmail(email)
        }
    }

    fun savePhone(phone: String) {
        viewModelScope.launch {
            userpref.savePhone(phone)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userpref.saveToken("")
            userpref.saveUsername("")
            userpref.saveEmail("")
            userpref.savePhone("")
            userpref.saveTimeStamp("")
            userpref.saveCreatedAt("")
        }
    }

    private suspend fun isSessionExpired(): Boolean {
        val loginTimestamp = userpref.getTimeStamp().first().toLongOrNull() ?: return true
        return (System.currentTimeMillis() - loginTimestamp) > sessionDurationMillis
    }

    fun saveUserData(token: String, name: String, email: String, phone: String, image: String?, createdat: String) {
        viewModelScope.launch {
            userpref.saveToken(token)
            userpref.saveUsername(name)
            userpref.saveEmail(email)
            userpref.savePhone(phone)
            userpref.saveCreatedAt(createdat)
            if (image != null) {
                userpref.saveImage(image)
            }
            saveTimeStamp()
        }
    }

    private fun startAutoLogoutTimer() {

        viewModelScope.launch {
            delay(3600000)
            logout()
        }
    }

    fun giveRating(token: String, giverating: Ratings) {
        viewModelScope.launch {
            try {
                val response = ratingapi.rating(token = token, ratingreq = giverating)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.e("API Response", "Failed: ${response.code()}")
                        _ratings.value = NetworkResponse.Success(it)
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