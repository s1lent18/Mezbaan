package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.OtherServicesModel
import retrofit2.Response
import retrofit2.http.GET

interface GetOtherServicesApi {

    @GET("/otherServices")
    suspend fun getOtherServices(): Response<OtherServicesModel>
}