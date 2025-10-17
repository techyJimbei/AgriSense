package com.example.mymajor1.model

import java.time.LocalDate

data class CropActivity(
    val id: String?,  // Changed from Long
    val date: LocalDate,
    val type: ActivityType,
    val cropName: String? = null,
    val notes: String? = null
)
