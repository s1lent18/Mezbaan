package com.example.mezbaan.model.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val address: String,
    val amenities: List<String>,
    val averageRating: Int,
    val baseGuestCount: Int,
    val capacity: Int,
    val coverImages: List<String>,
    val description: String?,
    val id: Int,
    val incrementPrice: String?,
    val incrementStep: Int?,
    val locationLink: String?,
    val managerName: String?,
    val managerNumber: String,
    val name: String,
    val otherImages: List<String>,
    val priceDay: String,
    val priceNight: String,
    val priceOff: String,
    val userId: Int,
    val venueType: String
) : Parcelable