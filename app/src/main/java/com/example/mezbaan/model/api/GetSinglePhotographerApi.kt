package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SinglePhotographerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GetSinglePhotographerApi {

    @GET("photography/{id}")
    suspend fun getSinglePhotography(
        @Path("id") id: Int,
    ): Response<SinglePhotographerModel>

}