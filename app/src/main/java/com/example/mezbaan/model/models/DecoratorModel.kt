package com.example.mezbaan.model.models

data class DecoratorModel(
    val `data`: List<DataX>,
    val limit: Int,
    val page: Int,
    val total: Int
)