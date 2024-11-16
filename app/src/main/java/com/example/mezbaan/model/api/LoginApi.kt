package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.LoginReq
import com.example.mezbaan.model.models.LoginHandle
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginApi {

    @POST("/login")
    suspend fun loginUser(
        @Body loginRequest: LoginReq
    ) : Response<LoginHandle>
}