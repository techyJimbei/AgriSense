package com.example.mymajor1.pages

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.mymajor1.R

data class Helpline(
    val title: String,
    val phoneNumber: String,
    val location: String
)

@Composable
fun HelplineCard(
    title: String,
    phoneNumber: String,
    location: String
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FFF4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.bodyMedium.copy(color = colorResource(R.color.text_green))
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = location.uppercase(),
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                }
            }

            IconButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phoneNumber")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(color = colorResource(R.color.text_green), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call Helpline",
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpLineNumberScreen() {
    val helplines = listOf(
        Helpline("National Crop Helpline", "+91-1800-111-000", "All India"),
        Helpline("Kisan Call Centre (KCC)", "1800-180-1551 / 1551", "All India"),
        Helpline("PM-Kisan Samman Nidhi Helpline", "1800-11-5526 / 155261", "All India"),
        Helpline("Kisan Sarathi", "1800-123-2175 / 14426", "All India"),
        Helpline("Agrinet (IVR/info service)", "1800-123-5000", "All India"),
        Helpline("eNAM Helpdesk", "1800-270-0224", "All India"),
        Helpline("NAFED Farmer Helpline", "1800-111-622", "All India"),
        Helpline("Agriculture Insurance Company (AIC)", "1800-11-6515", "All India"),
        Helpline("Agri Credit Helpline", "+91-1800-233-3444", "All India"),
        Helpline("Agri Transport / Logistics Helpline", "1800-180-4200 / 14488", "All India"),
        Helpline("Syngenta Kisan Helpline", "1800-121-5315", "All India"),
        Helpline("DeHaat Farmers Helpline", "1800-1036-110", "All India"),
        Helpline("ICAR Regional Farmer Helpline (example: VPKAS)", "1800-180-2311", "All India"),
        Helpline("Crop Insurance Helpline", "+91-1800-200-5577", "All India"),
        Helpline("Crop Insurance Helpline (AIC)", "1800-11-6515", "All India"),
        Helpline("NABARD Enquiries", "Varies by state/region", "All India"),
        Helpline("Soil Health Helpline", "+91-1555-222-333", "All India"),
        Helpline("Weather Advisory Helpline", "+91-1800-500-7070", "All India"),
        Helpline("Fertilizer Enquiry", "+91-1800-250-9000", "All India"),
        Helpline("Pesticide Control Board", "+91-1800-455-7788", "All India"),
        Helpline("Agri Market Support", "+91-1800-777-6666", "All India"),
        Helpline("Organic Farming Support", "+91-1800-999-8888", "All India"),
        Helpline("eNAM Technical Support", "1800-270-0224", "All India"),
        Helpline("Agricultural Produce Transport Helpline", "1800-180-4200 / 14488", "All India"),
        Helpline("Raitha Call Centre (Raitamitra)", "1800-425-3553", "Karnataka"),
        Helpline("Mahabeej / State Seed & Agriculture Helpline", "1800-233-8877", "Maharashtra"),
        Helpline("Haryana Agriculture Dept Helpline", "1800-180-2117", "Haryana"),
        Helpline("Odisha Agriculture Helpline", "155333", "Odisha"),
        Helpline("Gwalior District Farmer Call Centre", "1800-180-1551", "Madhya Pradesh")
    )

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filteredList = helplines.filter {
        it.title.contains(searchQuery.text, ignoreCase = true) ||
                it.phoneNumber.contains(searchQuery.text, ignoreCase = true) ||
                it.location.contains(searchQuery.text, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
        // 🌾 Background image (static, same as profile)
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
                    title = { Text("Kisaan Helpline") },
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
            ) {

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search helpline...") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = colorResource(R.color.text_green)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(R.color.text_green),
                        unfocusedBorderColor = colorResource(R.color.text_green),
                        cursorColor = colorResource(R.color.text_green)
                    )
                )


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp)
                ) {
                    items(filteredList) { helpline ->
                        HelplineCard(
                            title = helpline.title,
                            phoneNumber = helpline.phoneNumber,
                            location = helpline.location
                        )
                    }
                }
            }
        }
    }
}