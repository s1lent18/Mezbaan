package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.CateringBook
import com.example.mezbaan.model.requests.BookingReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BookCateringApi {

    @POST("/bookings/cateringService")
    suspend fun cateringBooking(
        @Header("Authorization") token: String,
        @Body cateringreq: CateringBook
    ) : Response<BookingReq>
}