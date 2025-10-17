package com.example.mymajor1.pages.navigation


import UserProfileScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymajor1.api.ApiService
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.jwt.dataStore
import com.example.mymajor1.pages.AddActivityDialog
import com.example.mymajor1.pages.CropCalendarScreen
import com.example.mymajor1.pages.CropDiagnosisScreen
import com.example.mymajor1.pages.GovernmentSchemeScreen
import com.example.mymajor1.pages.HelpLineNumberScreen
import com.example.mymajor1.pages.HomeScreen
import com.example.mymajor1.pages.LoginScreen
import com.example.mymajor1.pages.MandiPriceScreen
import com.example.mymajor1.pages.OnboardingScreen
import com.example.mymajor1.pages.ProfileDetailScreen
import com.example.mymajor1.pages.SignUpScreen
import com.example.mymajor1.pages.SoilAndNutrientsScreen
import com.example.mymajor1.pages.Splash
import com.example.mymajor1.pages.TodayScreen
import com.example.mymajor1.viewmodel.AuthViewModel
import com.example.mymajor1.viewmodel.AuthViewModelFactory
import com.example.mymajor1.viewmodel.CropCalendarViewModel
import com.example.mymajor1.viewmodel.CropCalenderViewModelFactory
import com.example.mymajor1.viewmodel.CropDetectionViewModel
import com.example.mymajor1.viewmodel.CropDetectionViewModelFactory
import com.example.mymajor1.viewmodel.FarmerViewModel
import com.example.mymajor1.viewmodel.FarmerViewModelFactory
import com.example.mymajor1.viewmodel.QueryViewModel
import com.example.mymajor1.viewmodel.QueryViewModelFactory
import com.example.mymajor1.viewmodel.WeatherViewModel
import com.example.mymajor1.viewmodel.WeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import java.time.LocalDate


sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object Onboarding: Screen("onboarding_screen")
    object Login: Screen("login_screen")
    object SignUp: Screen("signup_screen")
    object Profile: Screen("profile_screen")
    object Home: Screen("home_screen")
    object ProfileDetail: Screen("profile_detail_screen")
    object MandiPrice: Screen("mandiprice_screen")
    object SoilAndNutrients: Screen("soilandnutrients_screen")
    object CropCalendar: Screen("cropcalender_screen")
    object TodayScreen: Screen("today_screen/{date}") {
        fun createRoute(date: LocalDate) = "today_screen/$date"
    }
    object CropDiagnosis: Screen("cropdiagnosis_screen")
    object Helpline: Screen("helpline_screen")
    object GovtSchemes: Screen("govtschemes_screen")
}

@Composable
fun ApplicationNavGraph(
    navController: NavHostController = rememberNavController()
){

    val context = LocalContext.current
    val tokenManager = TokenManager(context.dataStore)
    val apiService = ApiService.api

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(apiService, tokenManager)
    )

    val farmerViewModel: FarmerViewModel = viewModel(
        factory = FarmerViewModelFactory(apiService, tokenManager)
    )

    val cropDetectionViewModel: CropDetectionViewModel = viewModel (
        factory = CropDetectionViewModelFactory(apiService, tokenManager)
    )

    val queryViewModel: QueryViewModel = viewModel (
        factory = QueryViewModelFactory(apiService, tokenManager)
    )

    val weatherViewModel: WeatherViewModel = viewModel (
        factory = WeatherViewModelFactory(apiService, tokenManager)
    )

    val cropCalendarViewModel: CropCalendarViewModel = viewModel (
        factory = CropCalenderViewModelFactory(apiService, tokenManager)
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            Splash(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }


        composable(Screen.SignUp.route){
            SignUpScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }

        composable(Screen.Login.route){
            LoginScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }

        composable(Screen.Profile.route){
            UserProfileScreen(
                authViewModel = authViewModel,
                farmerViewModel = farmerViewModel,
                navController = navController
            )
        }

        composable(Screen.Home.route){
            HomeScreen(
                farmerViewModel = farmerViewModel,
                queryViewModel = queryViewModel,
                navController = navController,
                weatherViewModel = weatherViewModel,
                fusedLocationClient = fusedLocationClient
            )
        }

        composable(Screen.ProfileDetail.route) {
            ProfileDetailScreen(
                farmerViewModel = farmerViewModel,
                navController = navController,
                authViewModel = authViewModel,
                tokenManager = tokenManager
            )
        }

        composable(Screen.MandiPrice.route){
            MandiPriceScreen()
        }

        composable(Screen.SoilAndNutrients.route) {
            SoilAndNutrientsScreen()
        }

        // In your NavHost
        composable(Screen.CropCalendar.route) {
            CropCalendarScreen(
                viewModel = cropCalendarViewModel,
                farmerViewModel = farmerViewModel,
                onDayClick = { selectedDate ->
                    navController.navigate(Screen.TodayScreen.createRoute(selectedDate))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.TodayScreen.route) { backStackEntry ->
            val dateString = backStackEntry.arguments?.getString("date")
            val selectedDate = LocalDate.parse(dateString)

            var showAddActivityDialog by remember { mutableStateOf(false) }
            val farmerInfo by farmerViewModel.farmerInfo.collectAsState()

            TodayScreen(
                selectedDate = selectedDate,
                viewModel = cropCalendarViewModel,
                onBack = { navController.popBackStack() },
                onAddActivity = { showAddActivityDialog = true },
                modifier = Modifier,
                fusedLocationClient = fusedLocationClient,
                farmerViewModel = farmerViewModel
            )

            if (showAddActivityDialog) {
                AddActivityDialog(
                    farmerViewModel = farmerViewModel,
                    selectedDate = selectedDate,
                    viewModel = cropCalendarViewModel,
                    onDismiss = { showAddActivityDialog = false },
                    onActivityAdded = {
                        showAddActivityDialog = false
                        cropCalendarViewModel.loadActivities(farmerInfo?.farmerId ?: 0)
                    }
                )
            }
        }

        composable(Screen.CropDiagnosis.route){
            CropDiagnosisScreen(
                navController = navController,
                cropDetectionViewModel = cropDetectionViewModel
            )
        }

        composable(Screen.Helpline.route) {
            HelpLineNumberScreen()
        }

        composable(Screen.GovtSchemes.route){
            GovernmentSchemeScreen()
        }

    }

}