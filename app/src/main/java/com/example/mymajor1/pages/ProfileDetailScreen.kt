package com.example.mymajor1.pages

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mymajor1.R
import com.example.mymajor1.viewmodel.FarmerViewModel
import java.util.Locale

fun getReadableAddress(context: Context, lat: Double, lng: Double): Pair<String?, String?> {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, lng, 1)
        if (!addresses.isNullOrEmpty()) {
            val addressLine = addresses[0].getAddressLine(0)
            val state = addresses[0].adminArea
            addressLine to state
        } else null to null
    } catch (e: Exception) {
        e.printStackTrace()
        null to null
    }
}


fun parseCoordinates(address: String): Pair<Double?, Double?> {
    val regex = """Lat:\s*([-+]?\d*\.\d+|\d+),\s*Lng:\s*([-+]?\d*\.\d+|\d+)""".toRegex()
    val match = regex.find(address)
    val lat = match?.groups?.get(1)?.value?.toDoubleOrNull()
    val lng = match?.groups?.get(2)?.value?.toDoubleOrNull()
    return lat to lng
}

@Composable
fun ProfileDetailScreen(
    farmerViewModel: FarmerViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    val farmer = farmerViewModel.farmerInfo.collectAsState().value

    var readableAddress by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    LaunchedEffect(farmer) {
        farmer?.let {
            val (lat, lng) = parseCoordinates(it.farmerAddress)
            if (lat != null && lng != null) {
                val (addr, st) = getReadableAddress(context, lat, lng)
                readableAddress = addr ?: "Unknown"
                state = st ?: "Unknown"
            }
        }
    }

    val dataFields = listOf(
        "Name:" to farmer?.farmerName,
        "Age:" to farmer?.farmerAge,
        "Gender:" to farmer?.farmerGender,
        "Contact:" to farmer?.farmerPhone,
        "Address:" to readableAddress,
        "State:" to state,
        "Language:" to farmer?.farmerLanguage
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = "back icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = {
                            navController.popBackStack()
                        })
                )

            }

            Text(
                text = "Profile Details",
                color = colorResource(R.color.text_green),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )

            Image(
                painter = painterResource(R.drawable.profile_icon2),
                contentDescription = "profile image",
                modifier = Modifier.size(100.dp)
            )

            OutlinedButton(
                onClick = {},
                modifier = Modifier.size(height = 50.dp, width = 140.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colorResource(R.color.text_green),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Text(
                    text = "Edit Profile",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .size(height = 400.dp, width = 300.dp)
                    .background(
                        color = colorResource(R.color.light_green),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        2.dp,
                        colorResource(R.color.text_green),
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start =  16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    dataFields.forEach { (label, value) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = label,
                                color = colorResource(R.color.text_green),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = value.toString(),
                                color = colorResource(R.color.black),
                                fontSize = 16.sp
                            )
                        }
                    }
                }

            }

        }

    }
}