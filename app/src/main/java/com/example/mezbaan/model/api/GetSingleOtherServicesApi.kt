package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SingleOtherServicesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetSingleOtherServicesApi {

    @GET("otherServices/{id}")
    suspend fun getSingleVendor(
        @Path("id") id: Int,
    ): Response<SingleOtherServicesModel>
}