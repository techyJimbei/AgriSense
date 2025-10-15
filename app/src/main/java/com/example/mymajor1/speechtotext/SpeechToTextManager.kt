package com.example.mymajor1.speechtotext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class SpeechToTextManager(
    private val context: Context,
    private val language: String
) {

    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false

    fun initialize() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(recognitionListener)
        }
    }

    fun startListening() {
        if (isListening) return

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        speechRecognizer?.startListening(intent)
        isListening = true
    }

    fun stopListening() {
        speechRecognizer?.stopListening()
        isListening = false
    }

    private val recognitionListener = object : RecognitionListener {

        override fun onReadyForSpeech(params: Bundle?) {
            onListeningStarted()
        }

        override fun onBeginningOfSpeech() {
        }

        override fun onRmsChanged(rmsdB: Float) {
            onAudioLevelChanged(rmsdB)
        }

        override fun onPartialResults(partialResults: Bundle?) {
            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            matches?.firstOrNull()?.let {
                onPartialResult(it)
            }
        }

        override fun onResults(results: Bundle?) {
            isListening = false
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            val recognizedText = matches?.firstOrNull() ?: ""

            if (recognizedText.isNotEmpty()) {
                onFinalResult(recognizedText)
            } else {
                onError("No speech detected")
            }
        }

        override fun onError(error: Int) {
            isListening = false
            val errorMessage = when (error) {
                SpeechRecognizer.ERROR_NETWORK -> "Network error"
                SpeechRecognizer.ERROR_NO_MATCH -> "Could not understand"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech detected"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Microphone permission needed"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Speech recognizer busy"
                else -> "Recognition error"
            }
            onError(errorMessage)
        }

        override fun onEndOfSpeech() {
            isListening = false
        }

        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    var onListeningStarted: () -> Unit = {}
    var onPartialResult: (String) -> Unit = {}
    var onFinalResult: (String) -> Unit = {}
    var onAudioLevelChanged: (Float) -> Unit = {}
    var onError: (String) -> Unit = {}

    fun destroy() {
        speechRecognizer?.destroy()
        speechRecognizer = null
    }
}