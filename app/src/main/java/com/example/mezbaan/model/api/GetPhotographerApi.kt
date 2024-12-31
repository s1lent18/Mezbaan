package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.PhotographerModel
import retrofit2.Response
import retrofit2.http.GET

interface GetPhotographerApi {

    @GET("/photography")
    suspend fun getPhotographers(): Response<PhotographerModel>
}