package com.example.mymajor1.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mymajor1.R
import com.example.mymajor1.pages.navigation.Screen

@Composable
fun OnboardingScreen(
    navController: NavController
){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.bg_green)),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Already have an account?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.grey)
            )

            Button(
                onClick = {
                    navController.navigate(Screen.Login.route)
                },
                modifier=Modifier.width(280.dp).padding(8.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.white),
                    contentColor = colorResource(R.color.text_green)
                ),
                border = BorderStroke(2.dp, colorResource(R.color.text_green))
            ) {
                Text(text = "Log into your account", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "New User?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.grey)
            )

            Button(
                onClick = {
                    navController.navigate(Screen.SignUp.route)
                },
                modifier=Modifier.width(280.dp).padding(8.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.light_green),
                    contentColor = colorResource(R.color.black)
                ),
                border = BorderStroke(2.dp, colorResource(R.color.text_green))
            ) {
                Text(text = "Create new account", fontSize = 20.sp)
            }
        }
    }
}