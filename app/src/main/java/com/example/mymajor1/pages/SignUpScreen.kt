package com.example.mymajor1.pages

import android.widget.Toast
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymajor1.R
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.mymajor1.model.UserSignUpRequest
import com.example.mymajor1.pages.navigation.Screen
import com.example.mymajor1.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }

    val context = LocalContext.current

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
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.size(78.dp)
            )
            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = stringResource(R.string.name),
                color = colorResource(R.color.text_green),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = "Sign Up",
                color = colorResource(R.color.text_green),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(31.dp))

            Text(
                text = "Use proper information to continue",
                color = colorResource(R.color.black),
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                shape = RoundedCornerShape(7.dp),
                value = username,
                onValueChange = {username = it},
                label = {
                    Text(
                        text = "Enter Username",
                        color = colorResource(R.color.grey),
                        fontSize = 12.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedBorderColor = colorResource(R.color.text_green)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                shape = RoundedCornerShape(7.dp),
                value = email,
                onValueChange = {email = it},
                label = {
                    Text(
                        text = "Enter Email",
                        color = colorResource(R.color.grey),
                        fontSize = 12.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedBorderColor = colorResource(R.color.text_green)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                shape = RoundedCornerShape(7.dp),
                value = password,
                onValueChange = {password = it},
                label = {
                    Text(
                        text = "Enter Password",
                        color = colorResource(R.color.grey),
                        fontSize = 12.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedBorderColor = colorResource(R.color.text_green)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                shape = RoundedCornerShape(7.dp),
                value = repassword,
                onValueChange = {repassword = it},
                label = {
                    Text(
                        text = "Re-enter Password",
                        color = colorResource(R.color.grey),
                        fontSize = 12.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedBorderColor = colorResource(R.color.text_green)
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "By signing up you are agreeing to our Terms & Condition and Privacy Policy",
                color = colorResource(R.color.text_green),
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 65.dp, end = 65.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if(username.isEmpty() || email.isEmpty() || password.isEmpty() || repassword.isEmpty()){
                        Toast.makeText(context, "Enter remaining fields", Toast.LENGTH_SHORT).show()
                    }
                    if(password != repassword){
                        Toast.makeText(context, "Your passwords don't match", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val request = UserSignUpRequest(
                            username = username,
                            email = email,
                            password = password
                        )
                        authViewModel.registerUser(request)
                        navController.navigate(Screen.Profile.route){
                            popUpTo(Screen.Onboarding.route) {inclusive = true}
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 65.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(7.dp)
                    ),
                shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.text_green))
            ) {
                Text(text = "Sign Up", color = colorResource(R.color.white), fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = "Already have an account? ",
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.grey),
                    fontSize = 13.sp
                )
                Text(
                    modifier = Modifier.clickable { },
                    text = "Sign In",
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.text_green),
                    fontSize = 13.sp
                )
            }
        }
    }
}
