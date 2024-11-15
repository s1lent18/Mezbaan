package com.example.mezbaan.model.dataclasses

data class VenueBook(
    val starttime: String,
    val endtime: String,
    val guestcount: Int,
    val edate: String,
    val bdate: String,
    val username: String,
    val email: String,
    val phone: String,
    val locationname: String,
    val locationid: Int
)
