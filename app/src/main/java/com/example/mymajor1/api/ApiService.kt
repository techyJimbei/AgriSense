package com.example.mymajor1.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    private const val BASE_URL = "http://192.168.1.3:8080/"

    // Logging interceptor to see API requests/responses in Logcat
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient with increased timeouts for Ollama AI responses
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)    // Time to establish connection
        .readTimeout(60, TimeUnit.SECONDS)       // Time to wait for response (Ollama needs this!)
        .writeTimeout(30, TimeUnit.SECONDS)      // Time to send request
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // Add custom OkHttp client with timeouts
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiEndpoints by lazy {
        retrofit.create(ApiEndpoints::class.java)
    }
}