package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.DecoratorBook
import com.example.mezbaan.model.dataclasses.PhotographerBook
import com.example.mezbaan.model.requests.EditReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditPhotographerBookingApi {

    @PUT("/bookings/photography/{id}")
    suspend fun editPhotographerBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body photographerreq: PhotographerBook
    ) : Response<EditReq>
}