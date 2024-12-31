package com.example.mezbaan.model.models

data class Venue(
    val address: String,
    val baseGuestCount: Int,
    val capacity: Int,
    val description: String,
    val id: Int,
    val incrementPrice: String,
    val incrementStep: Int,
    val locationLink: String,
    val managerName: String,
    val managerNumber: String,
    val name: String,
    val priceDay: String,
    val priceNight: String,
    val priceOff: String,
    val venueType: String
)