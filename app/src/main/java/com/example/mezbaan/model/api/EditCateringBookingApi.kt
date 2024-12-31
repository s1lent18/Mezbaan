package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.CateringBook
import com.example.mezbaan.model.dataclasses.DecoratorBook
import com.example.mezbaan.model.requests.EditReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditCateringBookingApi {

    @PUT("/bookings/cateringService/{id}")
    suspend fun editCateringBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body cateringreq: CateringBook
    ) : Response<EditReq>
}