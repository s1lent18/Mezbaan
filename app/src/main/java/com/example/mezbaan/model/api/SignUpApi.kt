package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.SignUpReq
import com.example.mezbaan.model.models.SignUpHandle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {

    @POST("/SignUp")
    suspend fun signupUser(
        @Body signup: SignUpReq
    ) : Response<SignUpHandle>
}