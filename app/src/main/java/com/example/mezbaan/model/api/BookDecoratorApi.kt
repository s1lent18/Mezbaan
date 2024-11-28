package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.DecoratorBook
import com.example.mezbaan.model.requests.BookingReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BookDecoratorApi {

    @POST("/bookings/decorationService")
    suspend fun decoratorBooking(
        @Header("Authorization") token: String,
        @Body decoratorreq: DecoratorBook
    ) : Response<BookingReq>
}