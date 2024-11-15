package com.example.mezbaan.model.dataclasses

data class CateringBook(
    val items: List<CartItems>,
    val time: String,
    val bill: Int,
    val guestcount: Int,
    val edate: String,
    val bdate: String,
    val address: String,
    val username: String,
    val email: String,
    val phone: String,
    val locationname: String,
)
