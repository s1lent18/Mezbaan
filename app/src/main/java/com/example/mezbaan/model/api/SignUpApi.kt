package com.example.mezbaan.model.api

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SignUpApi {

    @Multipart
    @POST("/register")
    suspend fun signupUser(
        @Part("name") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("roleId") roleId: RequestBody,
    ): Response<Unit>
}