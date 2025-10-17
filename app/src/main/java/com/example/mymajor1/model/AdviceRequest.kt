package com.example.mymajor1.model

data class AdviceRequest(
    val farmerId: Long,
    val date: String,
    val weather: WeatherResponse?,
    val language: String
)