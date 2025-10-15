package com.example.mymajor1.pages

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mymajor1.R
import com.example.mymajor1.pages.navigation.Screen
import com.example.mymajor1.viewmodel.FarmerViewModel
import com.example.mymajor1.viewmodel.QueryViewModel
import com.example.mymajor1.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import getCurrentLocation

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    farmerViewModel: FarmerViewModel,
    queryViewModel: QueryViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavController,
    fusedLocationClient: FusedLocationProviderClient
) {

    LaunchedEffect(Unit) {
        farmerViewModel.getFarmerDetails()
        val coordinates = getCurrentLocation(fusedLocationClient)
        coordinates?.let { (lat, lon) ->
            weatherViewModel.fetchWeather(lat.toFloat(), lon.toFloat())
        }
    }

    val farmerInfo by farmerViewModel.farmerInfo.collectAsState()

    val isListening by queryViewModel.isListening.collectAsState()
    val partialText by queryViewModel.partialText.collectAsState()
    val finalText by queryViewModel.finalText.collectAsState()
    val backendResponse by queryViewModel.backendResponse.collectAsState()
    val isLoading by queryViewModel.isLoading.collectAsState()
    val errorMessage by queryViewModel.errorMessage.collectAsState()
    val audioLevel by queryViewModel.audioLevel.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val weather by weatherViewModel.weatherState.collectAsState()
    val loading by weatherViewModel.loading.collectAsState()
    val error by weatherViewModel.error.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showDialog = true
        } else {
            showPermissionDialog = true
        }
    }

    fun checkAndRequestPermission() {
        when {
            context.checkSelfPermission(Manifest.permission.RECORD_AUDIO) ==
                    android.content.pm.PackageManager.PERMISSION_GRANTED -> {
                showDialog = true
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            val languageCode = when (farmerInfo?.farmerLanguage?.lowercase()) {
                "hindi" -> "hi-IN"
                "tamil" -> "ta-IN"
                "telugu" -> "te-IN"
                "kannada" -> "kn-IN"
                "marathi" -> "mr-IN"
                "bengali" -> "bn-IN"
                "gujarati" -> "gu-IN"
                "malayalam" -> "ml-IN"
                "punjabi" -> "pa-IN"
                "english" -> "en-IN"
                else -> farmerInfo?.farmerLanguage ?: "en-IN"
            }
            queryViewModel.initializeSpeech(context, languageCode)
        }
    }

    val buttons = listOf("Mandi Price", " Soil and\nNutrients", "Crop\nCalender", "Crop\nDiagnosis", "Helpline\nNumber", "Government\n   Schemes")

    val icons = listOf(R.drawable.rupees_icon, R.drawable.plant_icon, R.drawable.calender_icon
        , R.drawable.diagnosis_icon, R.drawable.call_icon, R.drawable.sceheme_icon)

    val routes = listOf(
        Screen.MandiPrice.route,
        Screen.SoilAndNutrients.route,
        Screen.CropCalendar.route,
        Screen.CropDiagnosis.route,
        Screen.Helpline.route,
        Screen.GovtSchemes.route
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "crop",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {

                Text(
                    text = "AgriSense",
                    color = colorResource(R.color.text_green),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    IconButton(
                        modifier = Modifier.size(46.dp),
                        onClick = {
                            navController.navigate(Screen.ProfileDetail.route)
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.profile_icon2),
                            contentDescription = "profile image"
                        )
                    }

                    IconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = {}
                    ) {
                        Image(
                            painter = painterResource(R.drawable.bell_icon),
                            contentDescription = "notification icon"
                        )
                    }

                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = buildAnnotatedString {
                            append("Namaste, ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(farmerInfo?.farmerName ?: "User")
                            }
                            append(" Ji!")
                        },
                        color = colorResource(R.color.text_green),
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light
                    )

                    Text(
                        text = "Aapki fasal aaj kesi hai?",
                        color = colorResource(R.color.text_green),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Italic
                    )

                }

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when {
                        loading -> Text(
                            text = "Loading...",
                            color = colorResource(R.color.text_green),
                            fontSize = 20.sp
                        )

                        error != null -> Text(
                            text = "⚠️ ${error ?: "Error"}",
                            color = colorResource(R.color.black),
                            fontSize = 16.sp
                        )

                        weather != null -> {
                            Text(
                                text = "${weather!!.temperature}°C",
                                color = colorResource(R.color.text_green),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = weather!!.weatherIcon,
                                    fontSize = 32.sp,
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = weather!!.weatherDescription,
                                    color = colorResource(R.color.black),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Light
                                )
                            }

                        }

                        else -> Text(
                            text = "Weather unavailable",
                            color = colorResource(R.color.black),
                            fontSize = 18.sp
                        )
                    }
                }


            }

            Spacer(modifier =  Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 8.dp)
                    .background(
                        color = colorResource(R.color.white).copy(alpha = 0.8f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(onClick = { checkAndRequestPermission() })
                    .border(2.dp, color = colorResource(R.color.text_green), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Click to Speak...   ",
                        color = colorResource(R.color.text_green),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        painter = painterResource(R.drawable.microphone_icon),
                        contentDescription = "microphone icon",
                        modifier = Modifier.size(45.dp),
                        tint = colorResource(R.color.text_green)
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = buttons.size) { index ->
                    GridItem(
                        label = buttons[index],
                        icon = icons[index],
                        onClick = {
                            navController.navigate(routes[index])
                        }
                    )
                }
            }

        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = {
                Text("Microphone Permission Required")
            },
            text = {
                Text("This app needs microphone permission to recognize your voice. Please grant permission in app settings.")
            },
            confirmButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    if (showDialog) {
        VoiceQueryDialog(
            isListening = isListening,
            partialText = partialText,
            finalText = finalText,
            backendResponse = backendResponse,
            isLoading = isLoading,
            errorMessage = errorMessage,
            audioLevel = audioLevel,
            onStartListening = { queryViewModel.startListening() },
            onStopListening = { queryViewModel.stopListening() },
            onDismiss = {
                queryViewModel.stopListening()
                showDialog = false
            }
        )
    }
}
@Composable
fun GridItem(
    label: String,
    icon: Int,
    onClick: () -> Unit
){

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.white).copy(alpha = 0.8f),
                shape = CardDefaults.shape
            )
            .border(2.dp, color = colorResource(R.color.light_green), shape = RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = icon),
                contentDescription = "icons",
                modifier = Modifier.size(35.dp)
            )

            Text(
                text = label,
                color = colorResource(R.color.text_green),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

        }
    }

}
