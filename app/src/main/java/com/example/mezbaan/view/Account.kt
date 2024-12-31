package com.example.mezbaan.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.model.dataprovider.NavigationBarItems
import com.example.mezbaan.model.models.Booking
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.BookingViewModel
import com.example.mezbaan.viewmodel.UserViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatDate(dateTime: String): String {

    return try {
        val parsedDate = ZonedDateTime.parse(dateTime)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        parsedDate.format(formatter)
    } catch (e: Exception) {
        "Invalid date"
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Account(
    navController: NavController,
    authviewmodel: AuthViewModel = viewModel(),
    bookingviewmodel : BookingViewModel = hiltViewModel(),
    userviewmodel: UserViewModel
) {
    val username by userviewmodel.username.collectAsState()
    val token by userviewmodel.token.collectAsState()
    val createdat by userviewmodel.createdAt.collectAsState()
    val user by authviewmodel.user.observeAsState()
    val image by userviewmodel.image.collectAsState()
    val booking by bookingviewmodel.bookings.collectAsState()
    val navigationBarItems = remember { NavigationBarItems.entries }
    var selectedIndex by remember { mutableIntStateOf(2) }
    val insets = WindowInsets.navigationBars
    val bottomInsetDp = with(LocalDensity.current) { insets.getBottom(LocalDensity.current).toDp() }
    var camera by remember { mutableStateOf(false) }

    val acc = remember { mutableStateListOf<Booking>() }
    val rej = remember { mutableStateListOf<Booking>() }

    LaunchedEffect(booking) {

        booking.forEach { booking ->
            when (booking.type) {
                "FULFILLED" -> acc.add(booking)
                "CANCELLED" -> rej.add(booking)
            }
        }
    }

    if (!camera) {
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
                                            navController.navigate(route = Screens.Home.route)
                                        }

                                        NavigationBarItems.Logout -> {
                                            if (user != null) {
                                                authviewmodel.signOut()
                                            }
                                            if (token.isNotEmpty()) {
                                                userviewmodel.logout()
                                            }
                                            navController.navigate(Screens.Login.route) {
                                                popUpTo(Screens.Home.route) { inclusive = true }
                                            }
                                        }

                                        NavigationBarItems.Account -> {

                                        }

                                        NavigationBarItems.Msg -> {
                                            navController.navigate(Screens.Msg.route)
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
                                color = backgroundcolor,
                                fontFamily = Bebas
                            )
                        }
                    }
                }
            }
        ) {
            val name = user?.displayName ?: username
            val pp = user?.photoUrl ?: image

            Surface {

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp)
                ) {
                    val (displaybox, bookingbox, booking1box, datebox) = createRefs()

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                            .background(backgroundcolor)
                            .constrainAs(displaybox) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                height = Dimension.percent(0.46f)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            pp.let {
                                if (image?.isEmpty() == true) {
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(100.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                } else {
                                    val painter = rememberAsyncImagePainter(model = image)

                                    val imageState = painter.state

                                    LaunchedEffect(painter.state) {
                                        when (painter.state) {
                                            is AsyncImagePainter.State.Loading -> Log.d("ImageState", "Loading...")
                                            is AsyncImagePainter.State.Success -> Log.d("ImageState", "Image loaded successfully")
                                            is AsyncImagePainter.State.Error -> Log.d("ImageState", "Error loading image")
                                            AsyncImagePainter.State.Empty -> {}
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .clickable { if (user == null) camera = true }
                                            .size(100.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
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
                                }
                            }

                            AddHeight(dimens.medium3)

                            Text(
                                text = "Hello, $name",
                                fontWeight = FontWeight.Bold,
                                fontSize = dimens.fontsize,
                                color = secondarycolor
                            )
                        }
                    }

                    Funca(
                        text = "Total Bookings: ${booking.size}",
                        icon = Icons.Default.ArrowDownward,
                        modifier = Modifier.constrainAs(bookingbox) {
                            top.linkTo(displaybox.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                            .fillMaxWidth(fraction = 0.85f)
                            .height(50.dp)
                    )

                    Funca(
                        text = if (booking.isNotEmpty()) {
                            "Acceptance Rate: ${(acc.size.toFloat() / booking.size) * 100}%"
                        } else {
                            "Acceptance Rate: N/A"
                        },
                        icon = Icons.Default.Percent,
                        modifier = Modifier.constrainAs(booking1box) {
                            top.linkTo(bookingbox.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                            .fillMaxWidth(fraction = 0.85f)
                            .height(50.dp)
                    )

                    Log.d("Date", createdat)

                    Funca(
                        text = "Creation Date: ${formatDate(createdat)}",
                        icon = Icons.Default.CalendarMonth,
                        modifier = Modifier.constrainAs(datebox) {
                            top.linkTo(booking1box.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                            .fillMaxWidth(fraction = 0.85f)
                            .height(50.dp)
                    )
                }
            }
        }
    } else {
        Camera(
            onDismiss = {
                camera = false
            },
            userviewmodel = userviewmodel
        )
    }
}