package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.temp1
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetPhotographerApi {

    @GET("/")
    suspend fun getPhotographers(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<temp1>
}