package com.example.mezbaan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.MezbaanTheme
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MezbaanTheme {
                SplashScreen {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    @Composable
    private fun SplashScreen(onSplashFinished: () -> Unit) {
        val  text = "Mezbaan..."
        var displayedText by remember { mutableStateOf("") }

        LaunchedEffect (Unit) {
            for (i in 1 ..text.length) {
                displayedText = text.substring(0, i)
                delay(100)
            }
            delay(1500)
            onSplashFinished()
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = displayedText,
                fontSize = dimens.heading,
                color = secondarycolor,
                fontFamily = Bebas,
                fontWeight = FontWeight.Bold
            )
        }
    }
}