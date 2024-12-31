package com.example.mezbaan.model.api

import com.example.mezbaan.model.requests.LoginReq
import com.example.mezbaan.model.models.LoginHandle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/login")
    suspend fun loginUser(
        @Body loginRequest: LoginReq
    ) : Response<LoginHandle>
}