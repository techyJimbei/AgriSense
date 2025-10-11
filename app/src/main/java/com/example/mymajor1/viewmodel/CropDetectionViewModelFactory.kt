package com.example.mymajor1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymajor1.api.ApiEndpoints
import com.example.mymajor1.jwt.TokenManager

class CropDetectionViewModelFactory(
    private val api: ApiEndpoints,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CropDetectionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CropDetectionViewModel(api, tokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}