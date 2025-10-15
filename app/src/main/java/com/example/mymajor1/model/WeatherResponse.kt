package com.example.mymajor1.model

data class WeatherResponse(
    val temperature: Float,
    val windSpeed: Float,
    val weatherCode: Int,
    val weatherDescription: String,
    val weatherIcon: String,
    val isDay: Boolean,
    val time: String,
    val language: String,
    val location: Location
)

data class Location(
    val latitude: Float,
    val longitude: Float
)
