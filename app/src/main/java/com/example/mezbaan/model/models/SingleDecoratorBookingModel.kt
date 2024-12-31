package com.example.mezbaan.model.models

data class SingleDecoratorBookingModel(
    val bookedAmenities: List<BookedAmenity>,
    val booking: BookingX,
    val customer: Customer,
    val decorationBooking: DecorationBooking,
    val decorationService: DecorationService
)