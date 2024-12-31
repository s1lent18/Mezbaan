package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.DecoratorBook
import com.example.mezbaan.model.requests.EditReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditDecoratorBookingApi {

    @PUT("/bookings/decorationService/{id}")
    suspend fun editDecoratorBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body decoratorreq: DecoratorBook
    ) : Response<EditReq>
}