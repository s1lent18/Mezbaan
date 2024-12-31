package com.example.mezbaan.model.dataclasses

data class Ratings(
    val bookingId: Int,
    val comments: String,
    val rating: Int,
    val serviceId: Int,
    val serviceType: String
)