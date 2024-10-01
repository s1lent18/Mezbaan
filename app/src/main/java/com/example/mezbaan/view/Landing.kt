package com.example.mezbaan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.Screens

@Composable
fun Landing(
    navController: NavController
) {
    Surface {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .fillMaxHeight(fraction = 0.46f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE8EAF6)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.mezbaan), // Your image resource
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            AddHeight(MaterialTheme.dimens.medium1) // 30.dp

            Row(
                modifier = Modifier.fillMaxWidth(fraction = 0.8f)
            ) {
                Text(
                    text = "Your One-Stop Event Management Solution",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.dimens.heading,
                    textAlign = TextAlign.Center,
                    color = backgroundcolor
                )
            }



            AddHeight(MaterialTheme.dimens.medium1) // 30.dp

            Row (
                modifier = Modifier.fillMaxWidth(fraction = 0.6f)
            ) {
                Text(
                    text = "Easily organize, promote, and execute seamless events from start to finish with our intuitive and feature-rich app",
                    color = backgroundcolor,
                    fontSize = MaterialTheme.dimens.fontsize,
                    textAlign = TextAlign.Center
                )
            }

            AddHeight(MaterialTheme.dimens.medium3)

            Row (
                modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        navController.navigate(route = Screens.Login.route)
                    },
                    modifier = Modifier.height(MaterialTheme.dimens.buttonHeight),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundcolor,
                        contentColor = secondarycolor
                    )
                ) {
                    Text(
                        "Sign In",
                        fontSize = MaterialTheme.dimens.fontsize
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(route = Screens.Signup.route)
                    },
                    modifier = Modifier.height(MaterialTheme.dimens.buttonHeight),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundcolor,
                        contentColor = secondarycolor
                    )
                ) {
                    Text(
                        "Register",
                        fontSize = MaterialTheme.dimens.fontsize
                    )
                }
            }
            AddHeight(MaterialTheme.dimens.small3)
        }
    }
}