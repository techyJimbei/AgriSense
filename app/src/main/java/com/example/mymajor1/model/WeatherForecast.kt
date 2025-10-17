package com.example.mymajor1.model

data class WeatherForecast(
    val summary: String,      // "Rain expected in coming days"
    val icon: String,         // "🌧️"
    val severity: ForecastSeverity
)

enum class ForecastSeverity {
    LOW,    // Normal - green background
    MEDIUM, // Attention - orange background
    HIGH    // Warning - red background
}
