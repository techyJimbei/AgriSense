package com.example.mymajor1.model

data class WeatherInfo(
    val temperature: Float,
    val condition: String,
    val humidity: Int,
    val precipitation: Float,
    val forecast7Day: List<WeatherForecastDay> = emptyList()
)

data class WeatherForecastDay(
    val date: String,
    val condition: String,
    val temperature: Float
)

