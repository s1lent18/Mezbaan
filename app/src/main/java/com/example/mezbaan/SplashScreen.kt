package com.example.mezbaan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mezbaan.model.models.Data
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.MezbaanTheme
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.UserViewModel
import com.example.mezbaan.viewmodel.VenueViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MezbaanTheme {
                SplashScreen { startDestination, venues ->
                    Log.d("SplashActivity", "Start Destination: $startDestination, Venues: $venues")
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    intent.putExtra("startDestination", startDestination)
                    intent.putParcelableArrayListExtra("venues", ArrayList(venues))
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    @Composable
    private fun SplashScreen(onSplashFinished: (String, List<Data>) -> Unit) {

        val authviewmodel: AuthViewModel = viewModel()
        val venueViewModel = hiltViewModel<VenueViewModel>()
        val venues by venueViewModel.venues.collectAsStateWithLifecycle()
        val userviewmodel = hiltViewModel<UserViewModel>()
        val sessionstatus by userviewmodel.session.collectAsStateWithLifecycle()
        var isSplashComplete by remember { mutableStateOf(false) }
        val token by userviewmodel.token.collectAsStateWithLifecycle()
        val user by authviewmodel.user.observeAsState()

        val  text = "Mezbaan"
        var displayedText by remember { mutableStateOf("") }

        LaunchedEffect (Unit) {
            venueViewModel.fetchVenues()

            for (i in 1 ..text.length) {
                displayedText = text.substring(0, i)
                delay(100)
            }

            delay(500)
            isSplashComplete = true
        }

        LaunchedEffect(isSplashComplete, venues, !sessionstatus) {
            Log.d("Response", "API Response Venue Size: ${venues.size}, isDataLoaded : ${isSplashComplete}, sessionStatus : $sessionstatus")
            if (isSplashComplete && venues.isNotEmpty() && !sessionstatus) {
                val startDestination = if (user == null && token.isNotEmpty()) {
                    Screens.Home.route
                } else {
                    Screens.Landing.route
                }
                onSplashFinished(startDestination, venues)
            }
        }



        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = displayedText,
                fontSize = 45.sp,
                color = secondarycolor,
                fontFamily = Bebas,
                fontWeight = FontWeight.Bold
            )
        }
    }
}