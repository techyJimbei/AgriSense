package com.example.mymajor1.model


data class FarmerAccountRequest(
    val userId: Long?,
    val farmerName: String,
    val farmerPhoneNumber: Long,
    val farmerGender: String,
    val farmerAddress: String,
    val farmerLanguage: String,
    val farmerAge: Int
)
