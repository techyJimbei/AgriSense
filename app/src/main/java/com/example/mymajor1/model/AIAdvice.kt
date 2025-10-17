package com.example.mymajor1.model

import com.google.gson.annotations.SerializedName

data class AIAdvice(
    val crop: String,
    @SerializedName("sowing_date")
    val sowingDate: String?,  // Will receive ISO datetime string
    val advice: String,
    val language: String
)