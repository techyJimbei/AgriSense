package com.example.mymajor1.pages

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mymajor1.R
import com.example.mymajor1.data.IndianCrops
import com.example.mymajor1.data.IndianSoilTypes
import com.example.mymajor1.model.SoilDataResponse
import com.example.mymajor1.viewmodel.SoilAdviceState
import com.example.mymajor1.viewmodel.SoilAdviceViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoilAndNutrientsScreen(
    viewModel: SoilAdviceViewModel
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var selectedPreviousCrop by remember { mutableStateOf("") }
    var selectedSoilType by remember { mutableStateOf("") }
    var cropExpanded by remember { mutableStateOf(false) }
    var soilExpanded by remember { mutableStateOf(false) }

    var currentLatitude by remember { mutableStateOf<Double?>(null) }
    var currentLongitude by remember { mutableStateOf<Double?>(null) }
    var locationError by remember { mutableStateOf<String?>(null) }
    var isLoadingLocation by remember { mutableStateOf(false) }

    val soilAdviceState by viewModel.soilAdviceState.collectAsState()

    // Permission launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocationGranted || coarseLocationGranted) {
            // Permission granted, get location
            isLoadingLocation = true
            getCurrentLocation(fusedLocationClient) { lat, lon, error ->
                isLoadingLocation = false
                if (error != null) {
                    locationError = error
                } else {
                    currentLatitude = lat
                    currentLongitude = lon
                    locationError = null
                }
            }
        } else {
            locationError = "Location permission denied"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
        // Background image
        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "Background",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Soil & Nutrients Advice") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(R.color.text_green),
                        titleContentColor = Color.White
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Previous Crop Dropdown
                DropdownField(
                    label = "Previous Crop Grown",
                    selectedValue = selectedPreviousCrop,
                    expanded = cropExpanded,
                    onExpandedChange = { cropExpanded = it },
                    onValueSelected = { selectedPreviousCrop = it },
                    items = IndianCrops.crops
                )

                // Soil Type Dropdown
                DropdownField(
                    label = "Soil Type",
                    selectedValue = selectedSoilType,
                    expanded = soilExpanded,
                    onExpandedChange = { soilExpanded = it },
                    onValueSelected = { selectedSoilType = it },
                    items = IndianSoilTypes.soilTypes
                )

                // Location Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.light_green)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = colorResource(R.color.text_green)
                            )
                            Text(
                                "Location",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.text_green)
                            )
                        }

                        if (currentLatitude != null && currentLongitude != null) {
                            Text(
                                "Latitude: $currentLatitude",
                                fontSize = 14.sp,
                                color = colorResource(R.color.text_green).copy(alpha = 0.8f)
                            )
                            Text(
                                "Longitude: $currentLongitude",
                                fontSize = 14.sp,
                                color = colorResource(R.color.text_green).copy(alpha = 0.8f)
                            )
                        } else if (locationError != null) {
                            Text(
                                locationError!!,
                                fontSize = 14.sp,
                                color = Color(0xFFD32F2F)
                            )
                        } else {
                            Text(
                                "Location not fetched yet",
                                fontSize = 14.sp,
                                color = colorResource(R.color.text_green).copy(alpha = 0.6f)
                            )
                        }

                        Button(
                            onClick = {
                                val hasPermission = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) == PackageManager.PERMISSION_GRANTED

                                if (hasPermission) {
                                    isLoadingLocation = true
                                    getCurrentLocation(fusedLocationClient) { lat, lon, error ->
                                        isLoadingLocation = false
                                        if (error != null) {
                                            locationError = error
                                        } else {
                                            currentLatitude = lat
                                            currentLongitude = lon
                                            locationError = null
                                        }
                                    }
                                } else {
                                    locationPermissionLauncher.launch(
                                        arrayOf(
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        )
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.text_green)
                            ),
                            enabled = !isLoadingLocation
                        ) {
                            if (isLoadingLocation) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(Modifier.width(8.dp))
                            }
                            Text(if (isLoadingLocation) "Getting Location..." else "Get Current Location")
                        }
                    }
                }

                // Get Advice Button
                Button(
                    onClick = {
                        if (selectedPreviousCrop.isNotEmpty() &&
                            selectedSoilType.isNotEmpty() &&
                            currentLatitude != null &&
                            currentLongitude != null) {
                            viewModel.getSoilAdvice(
                                previousCrop = selectedPreviousCrop,
                                soilType = selectedSoilType,
                                latitude = currentLatitude!!.toString(),
                                longitude = currentLongitude!!.toString()
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.text_green)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = selectedPreviousCrop.isNotEmpty() &&
                            selectedSoilType.isNotEmpty() &&
                            currentLatitude != null &&
                            currentLongitude != null
                ) {
                    Text("Get Soil Advice", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                // Results Section
                when (val state = soilAdviceState) {
                    is SoilAdviceState.Idle -> {
                        // Show nothing
                    }
                    is SoilAdviceState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = colorResource(R.color.text_green)
                            )
                        }
                    }
                    is SoilAdviceState.Success -> {
                        SoilAdviceResultSection(data = state.data)
                    }
                    is SoilAdviceState.Error -> {
                        ErrorCard(message = state.message)
                    }
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onResult: (latitude: Double?, longitude: Double?, error: String?) -> Unit
) {
    try {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onResult(location.latitude, location.longitude, null)
                } else {
                    onResult(null, null, "Unable to get location. Please try again.")
                }
            }
            .addOnFailureListener { exception ->
                onResult(null, null, "Failed to get location: ${exception.message}")
            }
    } catch (e: Exception) {
        onResult(null, null, "Error: ${e.message}")
    }
}

@Composable
fun SoilAdviceResultSection(data: SoilDataResponse) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (!data.advice.isNullOrEmpty()) {
            InfoSection(
                title = "Recommendations",
                content = data.advice
                    .split(",")
                    .joinToString("\n• ", prefix = "• "),
                icon = "📋"
            )
        }

        if (!data.requestSummary.isNullOrEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.text_green)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("🌾", fontSize = 24.sp)
                        Text(
                            "Your Concern",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        data.requestSummary
                            .split(",")
                            .joinToString("\n• ", prefix = "• ")
                        ,
                        fontSize = 15.sp,
                        color = Color.White,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Warnings Section
        if (!data.warnings.isNullOrEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("⚠️", fontSize = 24.sp)
                        Text(
                            "Important Warnings",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )
                    }
                    Text(
                        data.warnings
                            .split(",")
                            .joinToString("\n• ", prefix = "• "),
                        fontSize = 15.sp,
                        color = Color(0xFFD32F2F),
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

