package com.example.mezbaan.model.models

data class SingleCateringBookingModel(
    val bookedMenuItems: List<BookedMenuItems>,
    val bookedPackages: List<BookedPackage>,
    val booking: BookingXXXX,
    val cateringBooking: CateringBooking,
    val cateringService: CateringService,
    val customer: CustomerXXX
)