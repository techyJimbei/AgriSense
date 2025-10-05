package com.example.mymajor1.model

data class FarmerAccountResponse(
    val farmerId: Long,
    val farmerName: String,
    val farmerPhone: Long,
    val farmerGender: String,
    val farmerAddress: String,
    val farmerLanguage: String,
    val farmerAge: Int
)
