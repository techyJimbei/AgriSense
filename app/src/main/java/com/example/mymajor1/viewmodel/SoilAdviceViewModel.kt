package com.example.mymajor1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymajor1.api.ApiEndpoints
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.model.SoilDataInput
import com.example.mymajor1.model.SoilDataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class SoilAdviceState {
    object Idle : SoilAdviceState()
    object Loading : SoilAdviceState()
    data class Success(val data: SoilDataResponse) : SoilAdviceState()
    data class Error(val message: String) : SoilAdviceState()
}

class SoilAdviceViewModel(
    private val api: ApiEndpoints,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _soilAdviceState = MutableStateFlow<SoilAdviceState>(SoilAdviceState.Idle)
    val soilAdviceState: StateFlow<SoilAdviceState> = _soilAdviceState

    fun getSoilAdvice(
        previousCrop: String,
        soilType: String,
        latitude: String,
        longitude: String
    ) {
        viewModelScope.launch {
            _soilAdviceState.value = SoilAdviceState.Loading

            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"

                val response = api.getSoilAdvice(
                    token = token,
                    prevCrop = previousCrop,
                    soilType = soilType,
                    lat = latitude,
                    lon = longitude
                )

                _soilAdviceState.value = SoilAdviceState.Success(response)
            } catch (e: Exception) {
                _soilAdviceState.value = SoilAdviceState.Error(
                    e.message ?: "Failed to fetch soil advice"
                )
            }
        }
    }

    fun resetState() {
        _soilAdviceState.value = SoilAdviceState.Idle
    }
}