package com.example.mymajor1.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymajor1.R
import com.example.mymajor1.model.*
import com.example.mymajor1.viewmodel.CropCalendarViewModel
import com.example.mymajor1.viewmodel.FarmerViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import getCurrentLocation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(
    selectedDate: LocalDate,
    viewModel: CropCalendarViewModel,
    farmerViewModel: FarmerViewModel,
    onBack: () -> Unit,
    onAddActivity: () -> Unit,
    modifier: Modifier = Modifier,
    fusedLocationClient: FusedLocationProviderClient
) {
    val dayActivities = viewModel.getActivitiesForDate(selectedDate)
    val schedule = viewModel.currentSchedule
    val farmerInfo by farmerViewModel.farmerInfo.collectAsState()
    val weather = viewModel.currentWeather
    val isWeatherLoading = viewModel.isWeatherLoading

    LaunchedEffect(selectedDate) {
        val coordinates = getCurrentLocation(fusedLocationClient)
        coordinates?.let { (lat, lon) ->
            Log.d("TodayScreen", "Loading data for farmerId: ${farmerInfo?.farmerId}")
            viewModel.loadTodayScreenData(
                farmerId = farmerInfo?.farmerId ?: 0,
                latitude = lat.toFloat(),
                longitude = lon.toFloat(),
                date = selectedDate
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.resetTodayScreenData() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(selectedDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.text_green),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddActivity,
                containerColor = colorResource(R.color.text_green)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Activity", tint = Color.White)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(colorResource(R.color.bg_green))
        ) {
            item {
                CurrentWeatherCard(weather = weather, isLoading = isWeatherLoading)
            }

            item {
                TodayWeatherLine(weather = weather, isLoading = isWeatherLoading)
            }

            item {
                WeeklyForecastLine(
                    forecast = viewModel.weatherForecast,
                    isLoading = isWeatherLoading
                )
            }

            item {
                CropProgressBar(currentDate = selectedDate, schedule = schedule)
            }

            item {
                AIAdviceCard(
                    advice = viewModel.currentAdvice,
                    isLoading = viewModel.isAdviceLoading
                )
            }

            if (dayActivities.isNotEmpty()) {
                item {
                    Text(
                        "Scheduled Activities",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.text_green),
                        modifier = Modifier.padding(16.dp)
                    )
                }
                items(dayActivities) { activity ->
                    ActivityCard(activity = activity)
                }
            } else {
                item {
                    EmptyActivitiesCard(onAddClick = onAddActivity)
                }
            }

            viewModel.errorMessage?.let { error ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFD32F2F)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(error, color = Color(0xFFD32F2F))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentWeatherCard(weather: WeatherResponse?, isLoading: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = colorResource(R.color.text_green)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Current Weather",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = colorResource(R.color.text_green))
                }
            } else {
                if (weather == null) {
                    Text(
                        "Weather data unavailable",
                        color = colorResource(R.color.text_green),
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    val temp = weather.temperature ?: 0f
                    val icon = weather.weatherIcon ?: "🌡️"
                    val condition = weather.weatherDescription ?: "N/A"
                    val windSpeed = weather.windSpeed ?: 0f

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "$temp°C",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                condition,
                                fontSize = 16.sp,
                                color = colorResource(R.color.text_green)
                            )
                        }
                        Text(
                            icon,
                            fontSize = 48.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = colorResource(R.color.light_green))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Wind Speed: $windSpeed km/h",
                            fontSize = 14.sp,
                            color = colorResource(R.color.text_green)
                        )
                        Text(
                            if (weather.isDay == true) "☀️ Day" else "🌙 Night",
                            fontSize = 14.sp,
                            color = colorResource(R.color.text_green)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodayWeatherLine(weather: WeatherResponse?, isLoading: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_green))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Today's Weather Summary",
                color = colorResource(R.color.text_green),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                tint = colorResource(R.color.text_green),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))

            if (isLoading) {
                Text(
                    text = "Loading today's weather...",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.text_green)
                )
            } else {
                val summary = when {
                    weather == null -> "Weather unavailable"
                    weather.weatherCode == null -> "Weather unavailable"
                    weather.weatherCode in 0..1 -> "☀️ Sunny today"
                    weather.weatherCode in 2..3 -> "⛅ Cloudy today"
                    weather.weatherCode in 61..65 -> "🌧️ Rainy today"
                    weather.weatherCode in 95..99 -> "⛈️ Thunderstorms today"
                    else -> "${weather.weatherIcon} ${weather.weatherDescription}"
                }

                Text(
                    text = summary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.black)
                )
            }
        }
    }
}

@Composable
fun WeeklyForecastLine(forecast: WeatherForecast?, isLoading: Boolean) {
    if (forecast == null && !isLoading) return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (forecast?.severity) {
                ForecastSeverity.HIGH -> Color(0xFFFFEBEE)
                ForecastSeverity.MEDIUM -> Color(0xFFFFF3E0)
                else -> colorResource(R.color.light_green)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Weekly Weather Forecast",
                color = colorResource(R.color.text_green),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = colorResource(R.color.text_green)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "Loading forecast...",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.black)
                )
            } else {
                Text(
                    text = forecast?.icon ?: "",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = forecast?.summary ?: "",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (forecast?.severity) {
                        ForecastSeverity.HIGH -> Color(0xFFD32F2F)
                        ForecastSeverity.MEDIUM -> Color(0xFFE65100)
                        else -> colorResource(R.color.black)
                    }
                )
            }
        }
    }
}

@Composable
fun CropProgressBar(currentDate: LocalDate, schedule: ActivityScheduleResponse?) {
    if (schedule == null) return

    val totalDays = (schedule.harvestDate.toEpochDay() - schedule.sowingDate.toEpochDay()).coerceAtLeast(1)
    val elapsedDays = (currentDate.toEpochDay() - schedule.sowingDate.toEpochDay()).coerceIn(0, totalDays)
    val progress = (elapsedDays.toFloat() / totalDays.toFloat())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_green))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Crop: ${schedule.cropName}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.text_green)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = colorResource(R.color.text_green),
                trackColor = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Harvest on ${schedule.harvestDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.text_green)
            )
        }
    }
}

@Composable
fun AIAdviceCard(advice: AIAdvice?, isLoading: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = colorResource(R.color.text_green)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "AI Farming Advice",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = colorResource(R.color.text_green))
                }
            } else {
                advice?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        it.advice,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 20.sp,
                        color = Color.Black
                    )
                    if (!it.crop.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "For: ${it.crop}",
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(R.color.text_green),
                            fontWeight = FontWeight.Medium
                        )
                    }
                } ?: run {
                    Text(
                        "AI advice unavailable. Please add a sowing activity first.",
                        color = colorResource(R.color.text_green),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ActivityCard(activity: CropActivity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(activity.type.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    activity.type.icon,
                    contentDescription = null,
                    tint = activity.type.color
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    activity.type.displayName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                activity.cropName?.let {
                    Text(
                        it,
                        color = colorResource(R.color.text_green),
                        fontSize = 14.sp
                    )
                }
                activity.notes?.let {
                    Text(
                        it,
                        color = colorResource(R.color.text_green),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyActivitiesCard(onAddClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onAddClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = colorResource(R.color.text_green).copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "No activities scheduled",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.text_green)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Tap + to add a new activity",
                fontSize = 14.sp,
                color = colorResource(R.color.text_green)
            )
        }
    }
}