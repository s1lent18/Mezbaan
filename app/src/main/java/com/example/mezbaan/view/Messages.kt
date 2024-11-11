package com.example.mezbaan.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.model.dataprovider.NavigationBarItems
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

@Composable
fun Approvecard(model: String, text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundcolor,
            contentColor = secondarycolor
        ),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Profile picture container
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f) // Occupy 30% of the card's width
                    .aspectRatio(1f)    // Ensure the box is square
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center,
            ) {
                val painter = rememberAsyncImagePainter(model = model)
                val imageState = painter.state
                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (imageState is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            // Text container
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Use remaining width in the Row
                    .padding(start = 8.dp), // Add padding for spacing between image and text
                verticalArrangement = Arrangement.Center // Center text vertically
            ) {
                Text(text = text, color = secondarycolor)
            }
        }
    }
}


@Composable
fun Messages(
    navController: NavController,
    authviewmodel: AuthViewModel = viewModel()
) {
    val navigationBarItems = remember { NavigationBarItems.entries }
    var selectedIndex by remember { mutableIntStateOf(1) }
    val insets = WindowInsets.navigationBars
    val bottomInsetDp = with(LocalDensity.current) { insets.getBottom(LocalDensity.current).toDp() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        bottomBar = {
            AnimatedNavigationBar(
                modifier = Modifier
                    .height(70.dp)
                    .offset(y = -bottomInsetDp),
                selectedIndex = selectedIndex,
                cornerRadius = shapeCornerRadius(20.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = secondarycolor,
                ballColor = secondarycolor
            ) {
                navigationBarItems.forEach { item->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable {
                                selectedIndex = item.ordinal
                                when (item) {
                                    NavigationBarItems.Home -> {
                                        navController.navigate(Screens.Home.route)
                                    }

                                    NavigationBarItems.Msg -> {

                                    }

                                    NavigationBarItems.Logout -> {
                                        authviewmodel.signOut()
                                        navController.navigate(Screens.Login.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }

                                    NavigationBarItems.Account -> {
                                        navController.navigate(route = Screens.Account.route)
                                    }
                                }
                            },

                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(15.dp),
                            contentDescription = null,
                            imageVector = item.icon,
                            tint = backgroundcolor
                        )
                        Text(
                            item.text,
                            fontSize = dimens.buttontext,
                            color = backgroundcolor
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, bottom = 20.dp)
            ) {
                val (name, approvals) = createRefs()

                Row(
                    modifier = Modifier.constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.percent(0.9f)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = secondarycolor,
                            contentColor = backgroundcolor
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Bookings", fontWeight = FontWeight.Bold, fontFamily = Bebas, fontSize = dimens.fontsize)
                        }
                    }
                }

                LazyColumn (
                    modifier = Modifier.constrainAs(approvals) {
                        top.linkTo(name.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 20.dp)
                        width = Dimension.percent(0.9f)
                        height = Dimension.fillToConstraints
                    }
                ) {
                    items(5) {
                        Approvecard(
                            model = "https://drive.google.com/uc?export=view&id=1Gae9YMksmUfU74cgXX0x1ivwOdLb4H4L",
                            text = "Photographer Name"
                        )
                        AddHeight(20.dp)
                    }
                }
            }
        }
    }
}