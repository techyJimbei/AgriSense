package com.example.mymajor1.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ScheduleRequest(
    @SerializedName("crop_name")
    val cropName: String,
    @SerializedName("sowing_date")
    val sowingDate: String, // Format: "2024-07-01"
    val language: String
)
