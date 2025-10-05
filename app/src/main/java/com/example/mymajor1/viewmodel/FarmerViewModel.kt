package com.example.mymajor1.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymajor1.api.ApiEndpoints
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.model.FarmerAccountRequest
import com.example.mymajor1.model.FarmerAccountResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FarmerViewModel(
    private val api: ApiEndpoints,
    private val tokenManager: TokenManager
): ViewModel(){

    private val _farmerInfo = MutableStateFlow<FarmerAccountResponse?>(null)
    val farmerInfo: StateFlow<FarmerAccountResponse?> = _farmerInfo


    fun registerFarmer(request: FarmerAccountRequest){
        viewModelScope.launch {
            try{
                val response = api.registerFarmer(request)
                if(response.isSuccessful && response.body()!= null){
                    _farmerInfo.value = response.body()
                    Log.e("Farmer", "Farmer Account created")
                }
                else{
                    Log.e("Farmer", "Farmer response is null")
                }
            }
            catch(e: Exception){
                Log.e("Farmer", "Exception occurred"+e.message)
            }
        }
    }
}
