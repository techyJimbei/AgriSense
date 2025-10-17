package com.example.mymajor1.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymajor1.api.ApiEndpoints
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.model.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CropCalendarViewModel(
    private val api: ApiEndpoints,
    private val tokenManager: TokenManager
) : ViewModel() {

    // ======== State ========
    var activities by mutableStateOf<List<CropActivity>>(emptyList()); private set
    var currentMonth by mutableStateOf(YearMonth.now())
    var selectedLanguage by mutableStateOf("en")
    var isLoading by mutableStateOf(false); private set
    var isWeatherLoading by mutableStateOf(false); private set
    var isAdviceLoading by mutableStateOf(false); private set
    var errorMessage by mutableStateOf<String?>(null); private set

    var currentWeather by mutableStateOf<WeatherResponse?>(null); private set
    var currentAdvice by mutableStateOf<AIAdvice?>(null); private set
    var currentSchedule by mutableStateOf<ActivityScheduleResponse?>(null); private set
    var weatherForecast by mutableStateOf<WeatherForecast?>(null); private set

    // ======== Token helper ========
    private fun bearerToken(): String = "Bearer ${tokenManager.getToken() ?: ""}"

    // ======== Calendar Methods ========
    fun loadActivities(id: Long) = viewModelScope.launch {
        isLoading = true
        errorMessage = null
        Log.d("Calendar", "Loading activities for ${currentMonth.year}-${currentMonth.monthValue}")

        try {
            val response = api.getActivities(bearerToken(), id, currentMonth.year, currentMonth.monthValue)
            if (response.isSuccessful && response.body() != null) {
                activities = response.body()!!
                Log.d("Calendar", "Fetched ${activities.size} activities")
            } else {
                errorMessage = "Failed to load activities: ${response.message()}"
                Log.e("Calendar", "Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            errorMessage = "Error loading activities: ${e.message}"
            activities = emptyList()
            Log.e("Calendar", "Exception loading activities", e)
        } finally {
            isLoading = false
        }
    }

    fun changeMonth(id: Long, delta: Int) {
        currentMonth = currentMonth.plusMonths(delta.toLong())
        Log.d("Calendar", "Changed month to: ${currentMonth.year}-${currentMonth.monthValue}")
        loadActivities(id)
    }

    fun getActivitiesForDate(date: LocalDate): List<CropActivity> =
        activities.filter { it.date == date }.also {
            Log.d("Calendar", "Activities for $date: ${it.size}")
        }

    // ======== Today Screen Methods ========
    fun loadTodayScreenData(farmerId: Long, latitude: Float, longitude: Float, date: LocalDate) {
        viewModelScope.launch {
            loadWeather(latitude, longitude)
            loadSchedule(farmerId)
            loadAIAdvice(farmerId, date)
        }
    }

    private suspend fun loadWeather(latitude: Float, longitude: Float) {
        isWeatherLoading = true
        errorMessage = null
        try {
            val response = api.getWeather(bearerToken(), latitude, longitude, selectedLanguage)
            if (response.isSuccessful && response.body() != null) {
                currentWeather = response.body()
                weatherForecast = generateForecastSummary(currentWeather)
                Log.d("Calendar", "Weather loaded: ${currentWeather?.weatherDescription}")
            } else {
                errorMessage = "Failed to load weather: ${response.message()}"
                Log.e("Calendar", "Weather error: ${response.code()}")
            }
        } catch (e: Exception) {
            errorMessage = "Error loading weather: ${e.message}"
            Log.e("Calendar", "Error loading weather", e)
        } finally {
            isWeatherLoading = false
        }
    }

    private suspend fun loadSchedule(farmerId: Long) {
        try {
            val response = api.getCurrentSchedule(bearerToken(), farmerId)
            if (response.isSuccessful && response.body() != null) {
                currentSchedule = response.body()
                Log.d("Calendar", "Schedule loaded: ${currentSchedule?.cropName}")
            } else {
                Log.w("Calendar", "No schedule found or error: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("Calendar", "Error loading schedule", e)
        }
    }

    suspend fun loadAIAdvice(farmerId: Long, date: LocalDate) {
        isAdviceLoading = true
        try {
            val request = AdviceRequest(
                farmerId = farmerId,
                date = date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                weather = currentWeather,
                language = selectedLanguage
            )
            val response = api.getAIAdvice(bearerToken(), request)
            if (response.isSuccessful && response.body() != null) {
                currentAdvice = response.body()
                Log.d("Calendar", "AI advice loaded")
            } else {
                errorMessage = "Failed to load advice: ${response.message()}"
                Log.e("Calendar", "Advice error: ${response.code()}")
            }
        } catch (e: Exception) {
            errorMessage = "Error loading advice: ${e.message}"
            Log.e("Calendar", "Error loading advice", e)
        } finally {
            isAdviceLoading = false
        }
    }

    private fun generateForecastSummary(weather: WeatherResponse?): WeatherForecast? {
        if (weather == null) return null
        val code = weather.weatherCode ?: return null

        return when {
            code in 61..65 || code in 80..82 -> WeatherForecast(
                summary = if (selectedLanguage == "hi") "आने वाले दिनों में बारिश" else "Rain expected in coming days",
                icon = "🌧️",
                severity = ForecastSeverity.MEDIUM
            )
            code in 71..77 || code in 85..86 -> WeatherForecast(
                summary = if (selectedLanguage == "hi") "बर्फबारी की संभावना" else "Snow expected",
                icon = "❄️",
                severity = ForecastSeverity.HIGH
            )
            code in 95..99 -> WeatherForecast(
                summary = if (selectedLanguage == "hi") "तूफान की चेतावनी" else "Thunderstorm warning",
                icon = "⛈️",
                severity = ForecastSeverity.HIGH
            )
            code in 0..1 -> WeatherForecast(
                summary = if (selectedLanguage == "hi") "अच्छा मौसम" else "Clear weather ahead",
                icon = "☀️",
                severity = ForecastSeverity.LOW
            )
            else -> WeatherForecast(
                summary = if (selectedLanguage == "hi") "बदलता मौसम" else "Variable conditions",
                icon = "⛅",
                severity = ForecastSeverity.LOW
            )
        }
    }

    // ======== Activity Creation ========
    // ✅ FIXED: Now passes farmerId to saveActivities
    fun createScheduleFromSowing(
        farmerId: Long,  // ← CHANGED: renamed from 'id' for clarity
        cropName: String,
        sowingDate: LocalDate,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        isLoading = true
        errorMessage = null
        try {
            val request = ScheduleRequest(
                cropName = cropName,
                sowingDate = sowingDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                language = selectedLanguage
            )
            val scheduleResponse = api.createSchedule(bearerToken(), request)
            if (scheduleResponse.isSuccessful && scheduleResponse.body() != null) {
                val schedule = scheduleResponse.body()!!
                currentSchedule = schedule

                val newActivities = generateActivitiesFromSchedule(schedule)
                // ✅ FIXED: Pass farmerId here
                val saveResponse = api.saveActivities(bearerToken(), farmerId, newActivities)
                if (saveResponse.isSuccessful) {
                    loadActivities(farmerId)
                    onSuccess()
                } else onError("Failed to save activities: ${saveResponse.message()}")
            } else onError("Failed to create schedule: ${scheduleResponse.message()}")
        } catch (e: Exception) {
            val error = "Error creating schedule: ${e.message}"
            Log.e("Calendar", error, e)
            onError(error)
        } finally {
            isLoading = false
        }
    }

    // ✅ FIXED: Now passes farmerId to saveActivities
    fun addManualActivity(
        farmerId: Long,  // ← CHANGED: renamed from 'id' for clarity
        date: LocalDate,
        activityType: ActivityType,
        cropName: String? = null,
        notes: String? = null,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        isLoading = true
        try {
            val activity = CropActivity(
                id = null,
                date = date,
                type = activityType,
                cropName = cropName,
                notes = notes
            )
            // ✅ FIXED: Pass farmerId here
            val response = api.saveActivities(bearerToken(), farmerId, listOf(activity))
            if (response.isSuccessful) {
                loadActivities(farmerId)
                onSuccess()
            } else onError("Failed to save activity: ${response.message()}")
        } catch (e: Exception) {
            val error = "Error saving activity: ${e.message}"
            Log.e("Calendar", error, e)
            onError(error)
        } finally {
            isLoading = false
        }
    }

    private fun generateActivitiesFromSchedule(schedule: ActivityScheduleResponse): List<CropActivity> {
        val list = mutableListOf<CropActivity>()

        list.add(CropActivity(null, schedule.sowingDate, ActivityType.SOWING, schedule.cropName))

        var prevDate = schedule.sowingDate
        schedule.irrigationDates.forEach { date ->
            var d = prevDate.plusDays(1)
            while (d.isBefore(date)) {
                list.add(CropActivity(null, d, ActivityType.IDLE, schedule.cropName, "Monitor crop growth"))
                d = d.plusDays(1)
            }
            list.add(CropActivity(null, date, ActivityType.IRRIGATION, schedule.cropName))
            prevDate = date
        }

        schedule.fertilizerDates.forEach {
            list.add(CropActivity(null, it, ActivityType.FERTILIZER, schedule.cropName))
        }

        list.add(CropActivity(null, schedule.harvestDate, ActivityType.HARVEST, schedule.cropName))

        return list
    }

    // ======== Settings ========
    fun setLanguage(language: String) {
        selectedLanguage = language
    }

    fun clearError() {
        errorMessage = null
    }

    fun resetTodayScreenData() {
        currentWeather = null
        currentAdvice = null
        weatherForecast = null
    }
}