package com.example.mezbaan.model.dataclasses

data class VenueBook(
    val startTime: String,
    val endTime: String,
    val guestCount: Int,
    val date: String,
    val venueId: Int,
    val bill: Int
)
