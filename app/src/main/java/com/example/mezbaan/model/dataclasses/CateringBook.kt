package com.example.mezbaan.model.dataclasses

data class CateringBook(
    val address: String,
    val bill: Double,
    val cateringServiceId: Int,
    val date: String,
    val endTime: String?,
    val guestCount: Int,
    val menuItemIds: List<Int>,
    val packageIds: List<Int>,
    val startTime: String
)