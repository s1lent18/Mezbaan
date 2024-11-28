package com.example.mezbaan.model.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val address: String,
    val amenities: List<String>,
    val baseGuestCount: Int,
    val capacity: Int,
    val description: String?,
    val id: Int,
    val images: List<String>,
    val incrementPrice: String?,
    val incrementStep: Int?,
    val locationLink: String?,
    val managerName: String?,
    val managerNumber: String,
    val name: String,
    val priceDay: String,
    val priceNight: String,
    val priceOff: String,
    val userId: Int,
    val venueType: String,
    val rating: Float
) : Parcelable