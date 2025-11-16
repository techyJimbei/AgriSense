package com.example.mymajor1.pages

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymajor1.R
import com.example.mymajor1.data.IndiaStatesDistricts
import com.example.mymajor1.data.MandiCommodities
import com.example.mymajor1.model.MandiPriceResponse
import com.example.mymajor1.viewmodel.MandiPriceState
import com.example.mymajor1.viewmodel.MandiPriceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandiPriceScreen(
    viewModel: MandiPriceViewModel
) {
    var selectedState by remember { mutableStateOf("") }
    var selectedDistrict by remember { mutableStateOf("") }
    var selectedCommodity by remember { mutableStateOf("") }

    var stateExpanded by remember { mutableStateOf(false) }
    var districtExpanded by remember { mutableStateOf(false) }
    var commodityExpanded by remember { mutableStateOf(false) }

    val mandiPriceState by viewModel.mandiPriceState.collectAsState()

    val districts = if (selectedState.isNotEmpty()) {
        IndiaStatesDistricts.getDistrictsForState(selectedState)
    } else {
        emptyList()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
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
                    title = { Text("Mandi Price") },
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
                DropdownField(
                    label = "Select State",
                    selectedValue = selectedState,
                    expanded = stateExpanded,
                    onExpandedChange = { stateExpanded = it },
                    onValueSelected = {
                        selectedState = it
                        selectedDistrict = ""
                    },
                    items = IndiaStatesDistricts.states
                )

                DropdownField(
                    label = "Select District",
                    selectedValue = selectedDistrict,
                    expanded = districtExpanded,
                    onExpandedChange = { districtExpanded = it },
                    onValueSelected = { selectedDistrict = it },
                    items = districts,
                    enabled = selectedState.isNotEmpty()
                )

                DropdownField(
                    label = "Select Commodity",
                    selectedValue = selectedCommodity,
                    expanded = commodityExpanded,
                    onExpandedChange = { commodityExpanded = it },
                    onValueSelected = { selectedCommodity = it },
                    items = MandiCommodities.commodities
                )

                Button(
                    onClick = {
                        if (selectedState.isNotEmpty() &&
                            selectedDistrict.isNotEmpty() &&
                            selectedCommodity.isNotEmpty()) {
                            viewModel.getMandiPrices(
                                commodity = selectedCommodity,
                                state = selectedState,
                                district = selectedDistrict
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
                    enabled = selectedState.isNotEmpty() &&
                            selectedDistrict.isNotEmpty() &&
                            selectedCommodity.isNotEmpty()
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Search Prices", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                when (val state = mandiPriceState) {
                    is MandiPriceState.Idle -> {
                        // Show nothing or initial message
                    }
                    is MandiPriceState.Loading -> {
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
                    is MandiPriceState.Success -> {
                        if (state.data.isNotEmpty()) {
                            MandiPriceResultsSection(prices = state.data)
                        } else {
                            EmptyResultCard()
                        }
                    }
                    is MandiPriceState.Error -> {
                        ErrorCard(message = state.message)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    selectedValue: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueSelected: (String) -> Unit,
    items: List<String>,
    enabled: Boolean = true
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabled) onExpandedChange(it) }
    ) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = if (enabled) colorResource(R.color.text_green) else Color.Gray
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = colorResource(R.color.text_green),
                unfocusedBorderColor = colorResource(R.color.text_green),
                disabledBorderColor = Color.Gray,
                focusedLabelColor = colorResource(R.color.text_green),
                unfocusedLabelColor = colorResource(R.color.text_green),
                disabledLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Gray
            ),
            enabled = enabled
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.heightIn(max = 300.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueSelected(item)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
fun MandiPriceResultsSection(prices: List<MandiPriceResponse>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Mandi Price Results",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.text_green),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            prices.forEach { price ->
                MandiPriceCard(price)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun MandiPriceCard(price: MandiPriceResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.light_green)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    price.commodity,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.text_green)
                )
                Text(
                    price.arrivalDate,
                    fontSize = 14.sp,
                    color = colorResource(R.color.text_green).copy(alpha = 0.7f)
                )
            }

            if (!price.variety.isNullOrEmpty()) {
                Text(
                    "Variety: ${price.variety}",
                    fontSize = 14.sp,
                    color = colorResource(R.color.text_green).copy(alpha = 0.8f)
                )
            }

            Divider(
                color = colorResource(R.color.text_green).copy(alpha = 0.3f),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            PriceRow("Market", price.market)
            PriceRow("District", price.district)
            PriceRow("State", price.state)

            Divider(
                color = colorResource(R.color.text_green).copy(alpha = 0.3f),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriceBox("Min Price", "₹${price.minPrice}")
                PriceBox("Modal Price", "₹${price.modalPrice}")
                PriceBox("Max Price", "₹${price.maxPrice}")
            }
        }
    }
}

@Composable
fun PriceRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "$label:",
            fontSize = 14.sp,
            color = colorResource(R.color.text_green).copy(alpha = 0.8f)
        )
        Text(
            value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.text_green)
        )
    }
}

@Composable
fun PriceBox(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            label,
            fontSize = 11.sp,
            color = colorResource(R.color.text_green).copy(alpha = 0.7f)
        )
        Text(
            value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.text_green)
        )
    }
}

@Composable
fun EmptyResultCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.light_green)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("📊", fontSize = 48.sp)
            Spacer(Modifier.height(16.dp))
            Text(
                "No prices found",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.text_green)
            )
            Text(
                "Try different search criteria",
                fontSize = 14.sp,
                color = colorResource(R.color.text_green).copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun ErrorCard(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("⚠️", fontSize = 48.sp)
            Spacer(Modifier.height(16.dp))
            Text(
                "Error",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD32F2F)
            )
            Text(
                message,
                fontSize = 14.sp,
                color = Color(0xFFD32F2F).copy(alpha = 0.8f)
            )
        }
    }
}