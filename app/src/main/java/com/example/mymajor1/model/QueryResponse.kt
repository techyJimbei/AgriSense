package com.example.mymajor1.model

data class QueryResponse(
    val response: String,
    val timestamp: Long? = null,
    val status: String? = null
)
