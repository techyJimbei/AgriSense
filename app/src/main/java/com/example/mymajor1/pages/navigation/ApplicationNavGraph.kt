package com.example.mymajor1.pages.navigation


import UserProfileScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymajor1.api.ApiService
import com.example.mymajor1.jwt.TokenManager
import com.example.mymajor1.jwt.dataStore
import com.example.mymajor1.pages.HomeScreen
import com.example.mymajor1.pages.LoginScreen
import com.example.mymajor1.pages.OnboardingScreen
import com.example.mymajor1.pages.SignUpScreen
import com.example.mymajor1.pages.Splash
import com.example.mymajor1.viewmodel.AuthViewModel
import com.example.mymajor1.viewmodel.AuthViewModelFactory
import com.example.mymajor1.viewmodel.FarmerViewModel
import com.example.mymajor1.viewmodel.FarmerViewModelFactory

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object Onboarding: Screen("onboarding_screen")
    object Login: Screen("login_screen")
    object SignUp: Screen("signup_screen")
    object Profile: Screen("profile_screen")
    object Home: Screen("home_screen")
}

@Composable
fun ApplicationNavGraph(
    navController: NavHostController = rememberNavController()
){

    val context = LocalContext.current
    val tokenManager = TokenManager(context.dataStore)
    val apiService = ApiService.api


    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(apiService, tokenManager)
    )

    val farmerViewModel: FarmerViewModel = viewModel(
        factory = FarmerViewModelFactory(apiService, tokenManager)
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
            HomeScreen()
        }

    }

}