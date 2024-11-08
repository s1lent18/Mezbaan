package com.example.mezbaan


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mezbaan.ui.theme.MezbaanTheme
import com.example.mezbaan.viewmodel.navigation.NavGraph

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MezbaanTheme {
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}