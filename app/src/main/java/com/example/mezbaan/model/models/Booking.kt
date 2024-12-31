package com.example.mezbaan.model.models

data class Booking(
    val bill: String,
    val bookingId: Int,
    val cover: String,
    val date: String,
    val serviceId: Int,
    val serviceName: String,
    val status: String,
    val type: String,
    val userContact: UserContact,
    val userId: Int,
    val vendorId: Int
)