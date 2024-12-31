package com.example.mezbaan.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mezbaan.model.api.RetrofitInstance
import com.example.mezbaan.model.models.ImageModel
import com.example.mezbaan.model.response.NetworkResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraViewModel : ViewModel() {


    private fun bitmapToFile(bitmap: Bitmap): File {
        val file = File.createTempFile("photo", ".jpg")
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
        } catch (e: IOException) {
            Log.e("CameraViewModel", "Error saving bitmap to file", e)
        }
        return file
    }

    private val imageapi = RetrofitInstance.imageapi
    private val _imageresult = MutableLiveData<NetworkResponse<ImageModel>>()
    val imageresult: LiveData<NetworkResponse<ImageModel>> = _imageresult

    fun uploadimage(image: Bitmap, tooken: String, onSuccess: (String) -> Unit) {
        _imageresult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val file = bitmapToFile(image)
                val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestBody)
                val response = imageapi.uploadProfilePicture(
                    token = tooken,
                    image = multipartBody
                )
                Log.d("CameraViewModel", "HTTP response code: ${response.code()}")
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        _imageresult.value = NetworkResponse.Success(it)

                        onSuccess(it.imageUrl)
                    }
                } else {
                    _imageresult.value = NetworkResponse.Failure("Error")
                }
            }
            catch (e: Exception) {
                _imageresult.value = NetworkResponse.Failure("Server IrResponsive")
            }
        }
    }
}