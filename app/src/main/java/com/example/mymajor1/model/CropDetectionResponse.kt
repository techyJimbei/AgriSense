package com.example.mymajor1.model

data class CropDetectionResponse(
    val symptoms: String,
    val diseaseName: String,
    val pestName: String,
    val remedy: String
)