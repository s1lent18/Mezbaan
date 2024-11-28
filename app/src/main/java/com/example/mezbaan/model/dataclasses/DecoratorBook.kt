package com.example.mezbaan.model.dataclasses

data class DecoratorBook(
    val address: String,
    val amenities: List<Amenity?>,
    val bill: Double,
    val date: String,
    val decorationServiceId: Int,
    val endTime: String,
    val startTime: String
)