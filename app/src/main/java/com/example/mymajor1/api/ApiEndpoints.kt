package com.example.mymajor1.api

import com.example.mymajor1.model.CropDetectionRequest
import com.example.mymajor1.model.CropDetectionResponse
import com.example.mymajor1.model.FarmerAccountRequest
import com.example.mymajor1.model.FarmerAccountResponse
import com.example.mymajor1.model.UserLoginRequest
import com.example.mymajor1.model.UserLoginResponse
import com.example.mymajor1.model.UserSignUpRequest
import com.example.mymajor1.model.UserSignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @GET("/api/farmer/details")
    suspend fun getFarmerDetails(@Header("Authorization") token: String): Response<FarmerAccountResponse>

    @PUT("/api/farmer/{id}")
    suspend fun updateFarmer(@Header("Authorization") token: String, @Path("id") id: Long, @Body request: FarmerAccountRequest): Response<FarmerAccountResponse>

    //crop diagnosis
    @POST("/api/crop/detect")
    suspend fun detectDisease(@Header("Authorization") token: String, @Body disease: CropDetectionRequest): Response<CropDetectionResponse>
}
