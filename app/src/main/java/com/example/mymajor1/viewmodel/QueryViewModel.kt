package com.example.mymajor1.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymajor1.api.ApiEndpoints
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.model.QueryRequest
import com.example.mymajor1.speechtotext.SpeechToTextManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QueryViewModel(
    private val api: ApiEndpoints,
    private val tokenManager: TokenManager
) : ViewModel() {

    private lateinit var speechManager: SpeechToTextManager
    private var userLanguage: String = "en-IN"

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _partialText = MutableStateFlow("")
    val partialText: StateFlow<String> = _partialText.asStateFlow()

    private val _finalText = MutableStateFlow("")
    val finalText: StateFlow<String> = _finalText.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _audioLevel = MutableStateFlow(0f)
    val audioLevel: StateFlow<Float> = _audioLevel.asStateFlow()

    private val _backendResponse = MutableStateFlow("")
    val backendResponse: StateFlow<String> = _backendResponse.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun initializeSpeech(context: Context, language: String) {
        userLanguage = language
        speechManager = SpeechToTextManager(context, language)
        speechManager.initialize()

        speechManager.onListeningStarted = {
            _isListening.value = true
            _errorMessage.value = null
        }

        speechManager.onPartialResult = { text ->
            _partialText.value = text
        }

        speechManager.onFinalResult = { text ->
            _isListening.value = false
            _finalText.value = text
            _partialText.value = ""

            // Send to backend
            sendQueryToBackend(text)
        }

        speechManager.onAudioLevelChanged = { level ->
            _audioLevel.value = level
        }

        speechManager.onError = { error ->
            _isListening.value = false
            _errorMessage.value = error
            _partialText.value = ""
        }
    }

    fun startListening() {
        if (::speechManager.isInitialized) {
            speechManager.startListening()
        }
    }

    fun stopListening() {
        if (::speechManager.isInitialized) {
            speechManager.stopListening()
            _isListening.value = false
        }
    }

    private fun sendQueryToBackend(query: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val token = tokenManager.getToken()

                val request = QueryRequest(
                    query = query,
                    language = userLanguage
                )

                val response = api.sendQuery("Bearer $token", request)

                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        _backendResponse.value = it.response
                    }
                } else {
                    _errorMessage.value = "Server error: ${response.code()}"
                }

            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        if (::speechManager.isInitialized) {
            speechManager.destroy()
        }
    }
}