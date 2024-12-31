package com.example.mezbaan.model.dataclasses

data class OtherServicesBook(
    val address: String,
    val bill: Double,
    val date: String,
    val endTime: String,
    val otherServiceId: Int,
    val serviceCount: Int,
    val startTime: String
)