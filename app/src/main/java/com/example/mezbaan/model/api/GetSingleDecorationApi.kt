package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SingleDecorationModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetSingleDecorationApi {

    @GET("decorationServices/{id}")
    suspend fun getSingleDecoration(
        @Path("id") id: Int,
    ): Response<SingleDecorationModel>
}