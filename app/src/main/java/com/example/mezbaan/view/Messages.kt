package com.example.mezbaan.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.model.dataprovider.BookingOptions
import com.example.mezbaan.model.dataprovider.NavigationBarItems
import com.example.mezbaan.model.models.Booking
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.navyblue
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.BookingViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Approvecard(model: String, text: String, date: String, time: String, status: String) {
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
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight()
                    .aspectRatio(1f)
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

            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = text, color = secondarycolor, fontSize = dimens.cardfont)
                Text(text = date, color = secondarycolor, fontSize = dimens.cardfont)
                Text(text = time, color = secondarycolor, fontSize = dimens.cardfont)
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = when (status) {
                                    "APPROVED" -> Color.Green
                                    "REQUESTED" -> navyblue
                                    "CANCELLED" -> Color.Red
                                    else -> Color.Gray
                                },
                                shape = CircleShape
                            )
                    )
                    AddWidth(4.dp)
                    Text(
                        text = when (status) {
                            "APPROVED" -> "Approved"
                            "REQUESTED" -> "Requested"
                            "CANCELLED" -> "Rejected"
                            "FULFILLED" -> "Fulfilled"
                            "REVIEWED" -> "reviewed"
                            else -> "Unknown status"
                        },
                        color = secondarycolor,
                        fontSize = dimens.cardfont
                    )
                }
            }
        }
    }
}


@Composable
fun Messages(
    bookingviewmodel : BookingViewModel = hiltViewModel(),
    navController: NavController,
    authviewmodel: AuthViewModel = viewModel()
) {
    val navigationBarItems = remember { NavigationBarItems.entries }
    var selectedIndex by remember { mutableIntStateOf(1) }
    val insets = WindowInsets.navigationBars
    val bottomInsetDp = with(LocalDensity.current) { insets.getBottom(LocalDensity.current).toDp() }
    val selectedOption = remember { mutableStateOf("Venues") }
    val bookings by bookingviewmodel.bookings.collectAsState()

    val venues = mutableListOf<Booking>()
    val catering = mutableListOf<Booking>()
    val photography = mutableListOf<Booking>()
    val decorationService = mutableListOf<Booking>()
    val otherService = mutableListOf<Booking>()

    bookings.forEach { booking ->
        when (booking.type) {
            "venue" -> venues.add(booking)
            "cateringService" -> catering.add(booking)
            "photography" -> photography.add(booking)
            "decorationService" -> decorationService.add(booking)
            "otherService" -> otherService.add(booking)
        }
    }

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
                val (name, approvals, itemstobook) = createRefs()

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

                LazyRow(
                    modifier = Modifier.constrainAs(itemstobook) {
                        top.linkTo(name.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                ) {
                    items(BookingOptions.size) { option ->
                        Itemstobook(
                            image = painterResource(BookingOptions[option].first),
                            text = if (BookingOptions[option].second == "Photographers") "Photo\ngraphers" else BookingOptions[option].second,
                            isSelected = selectedOption.value == BookingOptions[option].second,
                            onclick = { selectedOption.value = BookingOptions[option].second }
                        )
                        AddWidth(dimens.scrollspacer)
                    }
                }

                if (bookings.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                else {
                    when (selectedOption.value) {
                        "Venues" -> {
                            LazyColumn (
                                modifier = Modifier.constrainAs(approvals) {
                                    top.linkTo(itemstobook.bottom, margin = 20.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom, margin = 50.dp)
                                    width = Dimension.percent(0.9f)
                                    height = Dimension.fillToConstraints
                                }
                            ) {
                                items(venues.size) {
                                    Approvecard(
                                        model = "https://drive.google.com/uc?export=view&id=1Gae9YMksmUfU74cgXX0x1ivwOdLb4H4L",
                                        text = venues[it].serviceName,
                                        date = LocalDate.parse(venues[it].date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                        time = venues[it].type,
                                        status = venues[it].status
                                    )
                                    AddHeight(20.dp)
                                }
                            }
                        }
                        "Decorators" -> {
                            LazyColumn (
                                modifier = Modifier.constrainAs(approvals) {
                                    top.linkTo(itemstobook.bottom, margin = 20.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom, margin = 50.dp)
                                    width = Dimension.percent(0.9f)
                                    height = Dimension.fillToConstraints
                                }
                            ) {
                                items(decorationService.size) {
                                    Approvecard(
                                        model = "https://drive.google.com/uc?export=view&id=1Gae9YMksmUfU74cgXX0x1ivwOdLb4H4L",
                                        text = decorationService[it].serviceName,
                                        date = LocalDate.parse(decorationService[it].date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                        time = decorationService[it].type,
                                        status = decorationService[it].status
                                    )
                                    AddHeight(20.dp)
                                }
                            }
                        }
                        "Caterers" -> {
                            LazyColumn (
                                modifier = Modifier.constrainAs(approvals) {
                                    top.linkTo(itemstobook.bottom, margin = 20.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom, margin = 50.dp)
                                    width = Dimension.percent(0.9f)
                                    height = Dimension.fillToConstraints
                                }
                            ) {
                                items(catering.size) {
                                    Approvecard(
                                        model = "https://drive.google.com/uc?export=view&id=1Gae9YMksmUfU74cgXX0x1ivwOdLb4H4L",
                                        text = catering[it].serviceName,
                                        date = LocalDate.parse(catering[it].date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                        time = catering[it].type,
                                        status = catering[it].status
                                    )
                                    AddHeight(20.dp)
                                }
                            }
                        }
                        "Photographers" -> {
                            LazyColumn (
                                modifier = Modifier.constrainAs(approvals) {
                                    top.linkTo(itemstobook.bottom, margin = 20.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom, margin = 50.dp)
                                    width = Dimension.percent(0.9f)
                                    height = Dimension.fillToConstraints
                                }
                            ) {
                                items(photography.size) {
                                    Approvecard(
                                        model = "https://drive.google.com/uc?export=view&id=1Gae9YMksmUfU74cgXX0x1ivwOdLb4H4L",
                                        text = photography[it].serviceName,
                                        date = LocalDate.parse(photography[it].date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                        time = photography[it].type,
                                        status = photography[it].status
                                    )
                                    AddHeight(20.dp)
                                }
                            }
                        }
                        "OtherServices" -> {
                            LazyColumn (
                                modifier = Modifier.constrainAs(approvals) {
                                    top.linkTo(itemstobook.bottom, margin = 20.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom, margin = 50.dp)
                                    width = Dimension.percent(0.9f)
                                    height = Dimension.fillToConstraints
                                }
                            ) {
                                items(otherService.size) {
                                    Approvecard(
                                        model = "https://drive.google.com/uc?export=view&id=1Gae9YMksmUfU74cgXX0x1ivwOdLb4H4L",
                                        text = otherService[it].serviceName,
                                        date = LocalDate.parse(otherService[it].date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                        time = otherService[it].type,
                                        status = otherService[it].status
                                    )
                                    AddHeight(20.dp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}