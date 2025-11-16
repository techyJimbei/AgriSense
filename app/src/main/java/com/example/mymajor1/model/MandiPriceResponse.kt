package com.example.mymajor1.model

data class MandiPriceResponse(
    val state: String,
    val district: String,
    val market: String,
    val commodity: String,
    val variety: String,
    val grade: String,
    val arrivalDate: String,
    val minPrice: String,
    val maxPrice: String,
    val modalPrice: String,
)
