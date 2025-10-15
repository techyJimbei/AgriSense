import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mymajor1.R
import com.example.mymajor1.model.FarmerAccountRequest
import com.example.mymajor1.pages.navigation.Screen
import com.example.mymajor1.viewmodel.AuthViewModel
import com.example.mymajor1.viewmodel.FarmerViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun UserProfileScreen(
    authViewModel: AuthViewModel,
    farmerViewModel: FarmerViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var address by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf("English") }
    var showError by remember { mutableStateOf(false) }

    val userId by authViewModel.userId.collectAsState()

    val locationPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(locationPermissionState.status) {
        if (locationPermissionState.status.isGranted) {
            address = getCurrentLocation(fusedLocationClient).toString()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "background",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.size(78.dp)
            )

            Text(
                text = stringResource(R.string.name),
                color = colorResource(R.color.text_green),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                placeholder = { Text("Enter your full name") },
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 65.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone
            OutlinedTextField(
                value = number,
                onValueChange = { number = it.filter { ch -> ch.isDigit() } },
                label = { Text("Contact Number") },
                placeholder = { Text("Enter your contact number") },
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 65.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Age
            OutlinedTextField(
                value = age,
                onValueChange = { age = it.filter { ch -> ch.isDigit() } },
                label = { Text("Age") },
                placeholder = { Text("Enter your age") },
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 65.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gender dropdown
            val genders = listOf("Male", "Female", "Other")
            DropdownSelector(
                options = genders,
                selectedOption = gender,
                onOptionSelected = { gender = it },
                label = "Gender"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Address field with runtime permission
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                placeholder = { Text("Fetching current location...") },
                trailingIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            if (locationPermissionState.status.isGranted) {
                                address = getCurrentLocation(fusedLocationClient).toString()
                            } else {
                                locationPermissionState.launchPermissionRequest()
                            }
                        }
                    }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Get Location")
                    }
                },
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 65.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Language dropdown
            val languages = listOf("English", "Hindi", "Tamil", "Telugu")
            DropdownSelector(
                options = languages,
                selectedOption = selectedLanguage,
                onOptionSelected = { selectedLanguage = it },
                label = "Language"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save button
            Button(
                onClick = {
                    if (
                        name.isEmpty() ||
                        number.length != 10 ||
                        age.isEmpty() ||
                        address.isEmpty()
                    ) {
                        showError = true
                    } else {
                        showError = false
                        val farmerPhone = number.toLongOrNull() ?: 0L
                        val farmerAge = age.toIntOrNull() ?: 0

                        val request = FarmerAccountRequest(
                            farmerName = name,
                            farmerPhoneNumber = farmerPhone,
                            farmerGender = gender,
                            farmerAddress = address,
                            farmerLanguage = selectedLanguage,
                            farmerAge = farmerAge,
                            userId = userId?.userId
                        )

                        farmerViewModel.registerFarmer(request)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 65.dp),
                shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.text_green)),
                elevation = ButtonDefaults.buttonElevation(6.dp, 10.dp)
            ) {
                Text("Save Profile", color = Color.White, fontSize = 15.sp)
            }

            if (showError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please fill all fields correctly (phone must be 10 digits).",
                    color = Color.Red,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 65.dp)
                )
            }
        }
    }
}

@SuppressLint("MissingPermission")
suspend fun getCurrentLocation(fusedLocationClient: FusedLocationProviderClient): Pair<Double, Double>? {
    return try {
        val location: Location? = fusedLocationClient.lastLocation.await()
        location?.let { Pair(it.latitude, it.longitude) }
    } catch (e: Exception) {
        null
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 65.dp)
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            ),
            readOnly = true
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
