package com.example.mezbaan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.navigation.Screens

@Composable
fun Landing(
    navController: NavController
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, bottom = 40.dp)
            ) {
                val (iconbox, welcometext, buttonrow) = createRefs()

                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.9f)
                        .fillMaxHeight(fraction = 0.46f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFE8EAF6))
                        .constrainAs(iconbox) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.mezbaan),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Row (
                    modifier = Modifier.constrainAs(welcometext) {
                        top.linkTo(iconbox.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.percent(0.8f)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your One-Stop Event Management Solution",
                        color = backgroundcolor,
                        fontSize = dimens.heading,
                        textAlign = TextAlign.Center,
                        lineHeight = 35.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        minLines = 2,
                        maxLines = 3,
                        overflow = Ellipsis,
                        fontFamily = Bebas
                    )
                }

                Row (
                    modifier = Modifier.constrainAs(buttonrow) {
                        top.linkTo(welcometext.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 30.dp)
                        width = Dimension.percent(0.9f)
                    },
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            navController.navigate(route = Screens.Login.route)
                        },
                        modifier = Modifier.height(dimens.buttonHeight),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundcolor,
                            contentColor = secondarycolor
                        )
                    ) {
                        Text(
                            "Sign In",
                            fontSize = dimens.buttontext
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate(route = Screens.Signup.route)
                        },
                        modifier = Modifier.height(dimens.buttonHeight),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundcolor,
                            contentColor = secondarycolor
                        )
                    ) {
                        Text(
                            "Register",
                            fontSize = dimens.buttontext
                        )
                    }
                }
            }
        }
    }
}