package com.example.mymajor1.api

import com.example.mymajor1.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiEndpoints {

    //Auth endpoints
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: UserSignUpRequest): Response<UserSignUpResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body request: UserLoginRequest): Response<UserLoginResponse>

    @POST("/api/auth/verify")
    suspend fun verifyToken(@Header("Authorization") token: String): Response<Boolean>

    //farmer account endpoints
    @POST("/api/farmer/register")
    suspend fun registerFarmer(@Body request: FarmerAccountRequest): Response<FarmerAccountResponse>

    @GET("/api/farmer/details")
    suspend fun getFarmerDetails(@Header("Authorization") token: String): Response<FarmerAccountResponse>

    @PUT("/api/farmer/{id}")
    suspend fun updateFarmer(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body request: FarmerAccountRequest
    ): Response<FarmerAccountResponse>

    //crop diagnosis endpoints
    @POST("/api/crop/detect")
    suspend fun detectDisease(
        @Header("Authorization") token: String,
        @Body disease: CropDetectionRequest
    ): Response<CropDetectionResponse>

    //speech to text endpoints
    @POST("api/query/ask")
    suspend fun sendQuery(
        @Header("Authorization") token: String,
        @Body request: QueryRequest
    ): Response<QueryResponse>

    //weather endpoints (main weather API)
    @GET("api/weather")
    suspend fun getWeather(
        @Header("Authorization") token: String,
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("language") language: String = "en"
    ): Response<WeatherResponse>

    //crop calendar endpoints
    @POST("api/calendar/advice")
    suspend fun getAIAdvice(
        @Header("Authorization") token: String,
        @Body request: AdviceRequest
    ): Response<AIAdvice>

    @POST("api/calendar/schedule")
    suspend fun createSchedule(
        @Header("Authorization") token: String,
        @Body request: ScheduleRequest
    ): Response<ActivityScheduleResponse>

    @POST("api/calendar/activities")
    suspend fun saveActivities(
        @Header("Authorization") token: String,
        @Query("farmerId") farmerId: Long,
        @Body activities: List<CropActivity>
    ): Response<Unit>

    @GET("api/calendar/activities")
    suspend fun getActivities(
        @Header("Authorization") token: String,
        @Query("id") id: Long,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<List<CropActivity>>

    @GET("api/calendar/schedule")
    suspend fun getCurrentSchedule(
        @Header("Authorization") token: String,
        @Query("farmerId") farmerId: Long
    ): Response<ActivityScheduleResponse>
}