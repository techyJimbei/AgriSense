package com.example.mymajor1.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object ApiService {
    private const val BASE_URL = "http://192.168.1.2:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Custom LocalDate adapter for Gson
    private val localDateAdapter = object : JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: com.google.gson.JsonDeserializationContext?
        ): LocalDate? {
            return try {
                json?.asString?.let { LocalDate.parse(it, formatter) }
            } catch (e: Exception) {
                null
            }
        }

        override fun serialize(
            src: LocalDate?,
            typeOfSrc: Type?,
            context: com.google.gson.JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src?.format(formatter))
        }
    }

    // Configure Gson with LocalDate adapter
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, localDateAdapter)
        .setLenient()
        .create()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: ApiEndpoints by lazy {
        retrofit.create(ApiEndpoints::class.java)
    }
}