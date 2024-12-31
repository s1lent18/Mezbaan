package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.Ratings
import com.example.mezbaan.model.models.Rating
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RatingApi {

    @POST("/ratings")
    suspend fun rating(
        @Header("Authorization") token: String,
        @Body ratingreq: Ratings
    ) : Response<Rating>

}