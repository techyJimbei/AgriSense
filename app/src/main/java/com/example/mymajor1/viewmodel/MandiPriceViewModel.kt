package com.example.mymajor1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymajor1.api.ApiEndpoints
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.model.MandiPriceRequest
import com.example.mymajor1.model.MandiPriceResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MandiPriceState {
    object Idle : MandiPriceState()
    object Loading : MandiPriceState()
    data class Success(val data: List<MandiPriceResponse>) : MandiPriceState()
    data class Error(val message: String) : MandiPriceState()
}

class MandiPriceViewModel(
    private val api: ApiEndpoints,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _mandiPriceState = MutableStateFlow<MandiPriceState>(MandiPriceState.Idle)
    val mandiPriceState: StateFlow<MandiPriceState> = _mandiPriceState

    fun getMandiPrices(
        commodity: String,
        state: String,
        district: String
    ) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.getToken() ?: ""}"
            _mandiPriceState.value = MandiPriceState.Loading

            try {

                val response = api.getMandiPrices(
                    token = token,
                    state = state,
                    district = district,
                    commodity = commodity
                )

                _mandiPriceState.value = MandiPriceState.Success(response)
            } catch (e: Exception) {
                _mandiPriceState.value = MandiPriceState.Error(
                    e.message ?: "Failed to fetch mandi prices"
                )
            }
        }
    }

    fun resetState() {
        _mandiPriceState.value = MandiPriceState.Idle
    }
}