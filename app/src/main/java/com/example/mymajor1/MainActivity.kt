package com.example.mymajor1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.mymajor1.pages.navigation.ApplicationNavGraph
import com.example.mymajor1.ui.theme.MyMajor1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMajor1Theme {
                val navController = rememberNavController()
                ApplicationNavGraph(navController)
            }
        }
    }
}