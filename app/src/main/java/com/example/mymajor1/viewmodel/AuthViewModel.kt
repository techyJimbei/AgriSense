package com.example.mymajor1.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymajor1.api.ApiEndpoints
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.model.UserLoginRequest
import com.example.mymajor1.model.UserSignUpRequest
import com.example.mymajor1.model.UserSignUpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object LoginSuccess : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val api: ApiEndpoints,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _userId = MutableStateFlow<UserSignUpResponse?>(null)
    val userId: StateFlow<UserSignUpResponse?> = _userId

    fun registerUser(request: UserSignUpRequest){
        viewModelScope.launch {
            try{
                val response = api.registerUser(request)

                if(response.isSuccessful && response.body()!=null){
                    _userId.value = response.body()
                    Log.e("Auth", "User Signed in successfully")
                }
                else{
                    Log.e("Auth", "User Sign in failed")
                }
            }
            catch(e: Exception){
                Log.e("Auth", "Exception occurred"+e.message)
            }
        }
    }

    fun login(request: UserLoginRequest) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            tokenManager.clearToken()
            try {
                val response = api.login(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.token.isNotEmpty()) {
                        tokenManager.saveToken(body.token)
                        Log.e("Auth", "Login Successful")
                        _loginState.value = AuthState.LoginSuccess
                    } else {
                        Log.e("Auth", "Login Failed: Empty token")
                        _loginState.value = AuthState.Error("Login failed: Empty token")
                    }
                } else {
                    Log.e("Auth", "Login Failed: ${response.code()} - ${response.message()}")
                    _loginState.value = AuthState.Error("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Auth", "Cannot Login: ${e.message}")
                _loginState.value = AuthState.Error("Cannot login: ${e.message}")
            }
        }
    }

    suspend fun verifyToken(): Boolean {
        val token = tokenManager.getToken()
        return if (token.isNullOrEmpty()) {
            false
        } else {
            try {
                val response = api.verifyToken("Bearer $token")
                response.isSuccessful && (response.body() == true)
            } catch (e: Exception) {
                false
            }
        }
    }

}