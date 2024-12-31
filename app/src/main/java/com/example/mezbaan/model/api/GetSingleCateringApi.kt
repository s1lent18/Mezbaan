package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SingleCateringModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetSingleCateringApi {

    @GET("cateringServices/{id}")
    suspend fun getSingleCatering(
        @Path("id") id: Int,
    ): Response<SingleCateringModel>
}