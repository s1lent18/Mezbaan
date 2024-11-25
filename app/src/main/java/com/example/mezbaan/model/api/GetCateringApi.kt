package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.CateringModel
import retrofit2.Response
import retrofit2.http.GET

interface GetCateringApi {

    @GET("/cateringServices")
    suspend fun getCatering () : Response<CateringModel>
}