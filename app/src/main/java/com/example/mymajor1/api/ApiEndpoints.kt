package com.example.mymajor1.api

import com.example.mymajor1.model.FarmerAccountRequest
import com.example.mymajor1.model.FarmerAccountResponse
import com.example.mymajor1.model.UserLoginRequest
import com.example.mymajor1.model.UserLoginResponse
import com.example.mymajor1.model.UserSignUpRequest
import com.example.mymajor1.model.UserSignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiEndpoints {

    //Auth endpoints
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: UserSignUpRequest): Response<UserSignUpResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body request: UserLoginRequest): Response<UserLoginResponse>

    @POST("/api/auth/verify")
    suspend fun verifyToken(@Header("Authorization") token : String): Response<Boolean>

    //farmer account endpoints
    @POST("/api/farmer/register")
    suspend fun registerFarmer(@Body request: FarmerAccountRequest): Response<FarmerAccountResponse>

}
