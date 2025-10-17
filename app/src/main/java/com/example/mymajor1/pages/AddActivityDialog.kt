package com.example.mymajor1.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymajor1.model.ActivityType
import com.example.mymajor1.viewmodel.CropCalendarViewModel
import com.example.mymajor1.viewmodel.FarmerViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityDialog(
    selectedDate: LocalDate,
    viewModel: CropCalendarViewModel,
    farmerViewModel: FarmerViewModel,
    onDismiss: () -> Unit,
    onActivityAdded: () -> Unit
) {
    var selectedActivityType by remember { mutableStateOf(ActivityType.SOWING) }
    var cropName by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val farmerInfo by farmerViewModel.farmerInfo.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Activity") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Select Activity Type",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Activity type selector
                listOf(
                    ActivityType.SOWING,
                    ActivityType.IRRIGATION,
                    ActivityType.FERTILIZER,
                    ActivityType.HARVEST
                ).forEach { type ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedActivityType = type }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedActivityType == type,
                            onClick = { selectedActivityType = type }
                        )
                        Icon(
                            type.icon,
                            contentDescription = null,
                            tint = type.color,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text(type.displayName)
                    }
                }

                // Input fields based on activity type
                Spacer(modifier = Modifier.height(16.dp))

                when (selectedActivityType) {
                    ActivityType.SOWING -> {
                        OutlinedTextField(
                            value = cropName,
                            onValueChange = { cropName = it },
                            label = { Text("Crop Name *") },
                            placeholder = { Text("e.g., Wheat, Rice, Corn") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Text(
                            "AI will generate irrigation and fertilizer schedule",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    else -> {
                        OutlinedTextField(
                            value = cropName,
                            onValueChange = { cropName = it },
                            label = { Text("Crop Name (Optional)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            label = { Text("Notes (Optional)") },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 3
                        )
                    }
                }

                // Error message
                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Validate input
                    if (selectedActivityType == ActivityType.SOWING && cropName.isBlank()) {
                        errorMessage = "Please enter crop name"
                        return@Button
                    }

                    errorMessage = null

                    if (selectedActivityType == ActivityType.SOWING) {
                        // Create full schedule from sowing
                        viewModel.createScheduleFromSowing(
                            farmerId = farmerInfo?.farmerId ?: 0,
                            cropName = cropName.trim(),
                            sowingDate = selectedDate,
                            onSuccess = {
                                onActivityAdded()
                            },
                            onError = { error ->
                                errorMessage = error
                            }
                        )
                    } else {
                        // Add manual activity
                        viewModel.addManualActivity(
                            farmerId = farmerInfo?.farmerId ?: 0,
                            date = selectedDate,
                            activityType = selectedActivityType,
                            cropName = cropName.ifBlank { null },
                            notes = notes.ifBlank { null },
                            onSuccess = {
                                onActivityAdded()
                            },
                            onError = { error ->
                                errorMessage = error
                            }
                        )
                    }
                },
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(if (viewModel.isLoading) "Adding..." else "Add")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !viewModel.isLoading
            ) {
                Text("Cancel")
            }
        }
    )
}