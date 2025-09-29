package com.example.mymajor1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mymajor1.UserProfile
import com.example.mymajor1.login.Login
import com.example.mymajor1.Register
import com.example.mymajor1.ui.theme.MyMajor1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMajor1Theme {
                Login()
                Register()
                UserProfile()
                }
            }
        }
    }