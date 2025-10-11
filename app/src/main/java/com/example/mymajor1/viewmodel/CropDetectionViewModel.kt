package com.example.mymajor1.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymajor1.api.ApiEndpoints
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.model.CropDetectionRequest
import com.example.mymajor1.model.CropDetectionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CropDetectionViewModel(
    private val api: ApiEndpoints,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _cropDiseaseInfo = MutableStateFlow<CropDetectionResponse?>(null)
    val cropDiseaseInfo: StateFlow<CropDetectionResponse?> = _cropDiseaseInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun detectDisease(disease: String) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                Log.d("TokenCheck", "Sending token: $token")

                val request = CropDetectionRequest(disease = disease)
                val response = api.detectDisease(token, request)

                if (response.isSuccessful && response.body() != null) {
                    _cropDiseaseInfo.value = response.body()
                    Log.d("Crop", "Crop detection data received: ${response.body()?.diseaseName}")
                } else {
                    Log.e("Crop", "Response code: ${response.code()}, error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("Crop", "Exception Occurred: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}