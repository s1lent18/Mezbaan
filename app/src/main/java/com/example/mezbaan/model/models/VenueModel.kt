package com.example.mezbaan.model.models

data class VenueModel(
    val `data`: List<Data>,
    val limit: Int,
    val page: Int,
    val total: Int
)