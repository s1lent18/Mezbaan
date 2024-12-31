package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.ImageModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApi {

    @Multipart
    @POST("upload-profile-picture")
    suspend fun uploadProfilePicture(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Response<ImageModel>
}