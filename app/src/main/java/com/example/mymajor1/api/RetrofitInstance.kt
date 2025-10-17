package com.example.mymajor1.api


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

object ApiClient {
    fun create(tokenProvider: TokenProvider): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenProvider))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.110.91.207/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


}