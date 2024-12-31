package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.DecoratorModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDecoratorApi {

    @GET("/decorationServices")
    suspend fun getDecorators(): Response<DecoratorModel>
}