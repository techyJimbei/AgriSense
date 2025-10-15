package com.example.mymajor1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymajor1.api.ApiEndpoints
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.model.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val api: ApiEndpoints,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchWeather(latitude: Float, longitude: Float, language: String = "en") {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                val token = "Bearer ${tokenManager.getToken() ?: ""}"
                val response = api.getWeather(
                    token = token,
                    latitude = latitude,
                    longitude = longitude,
                    language = language
                )

                if (response.isSuccessful && response.body() != null) {
                    _weatherState.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Failed to fetch weather: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}
