package com.example.mezbaan.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.model.dataclasses.Ratings
import com.example.mezbaan.model.dataprovider.BookingOptions
import com.example.mezbaan.model.dataprovider.NavigationBarItems
import com.example.mezbaan.model.models.Booking
import com.example.mezbaan.model.response.NetworkResponse
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.navyblue
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.BookingViewModel
import com.example.mezbaan.viewmodel.CateringViewModel
import com.example.mezbaan.viewmodel.DecoratorViewModel
import com.example.mezbaan.viewmodel.OtherServicesViewModel
import com.example.mezbaan.viewmodel.PhotographerViewModel
import com.example.mezbaan.viewmodel.UserViewModel
import com.example.mezbaan.viewmodel.VenueViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Approvecard(
    model: String,
    text: String,
    date: String, time: String,
    status: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
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
                        .clip(RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = when (status) {
                                    "APPROVED" -> Color.Yellow
                                    "REQUESTED" -> navyblue
                                    "CANCELLED" -> Color.Red
                                    "FULFILLED" -> Color.Green
                                    "REVIEWED" -> Color.Green
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Messages(
    userviewmodel : UserViewModel,
    venueviewmodel : VenueViewModel = hiltViewModel(),
    decoratorviewmodel : DecoratorViewModel = hiltViewModel(),
    bookingviewmodel : BookingViewModel = hiltViewModel(),
    cateringviewmodel : CateringViewModel = hiltViewModel(),
    photographerviewmodel : PhotographerViewModel = hiltViewModel(),
    otherservicesviewmodel : OtherServicesViewModel = hiltViewModel(),
    navController: NavController,
    authviewmodel: AuthViewModel = viewModel()
) {
    val insets = WindowInsets.navigationBars
    var id by remember { mutableIntStateOf(0) }
    val token by userviewmodel.token.collectAsState()
    var called by remember { mutableStateOf("") }
    var clicked by remember { mutableStateOf(false) }
    val bookings by bookingviewmodel.bookings.collectAsState()
    var selectedIndex by remember { mutableIntStateOf(1) }
    var requestreceived by remember { mutableStateOf(false) }
    val navigationBarItems = remember { NavigationBarItems.entries }
    val selectedOption = remember { mutableStateOf("Venues") }
    val ratingresult = userviewmodel.ratings.observeAsState()
    var isSheetopen by rememberSaveable { mutableStateOf(false) }
    var selectedRating by remember { mutableIntStateOf(0) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomInsetDp = with(LocalDensity.current) { insets.getBottom(LocalDensity.current).toDp() }

    var answer by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var bookingId by remember { mutableIntStateOf(0) }
    var serviceId by remember { mutableIntStateOf(0) }
    var serviceType by remember { mutableStateOf("") }
    val (comments, setcomments) = remember { mutableStateOf("") }
    var bookingname by remember { mutableStateOf("") }


    val singlevenuebookresult = venueviewmodel.singleVenueBooking.collectAsState()
    val singlecateringbookresult = cateringviewmodel.singleCateringBooking.collectAsState()
    val singledecoratorbookresult = decoratorviewmodel.singleDecoratorBooking.collectAsState()
    val singlephotographerbookresult = photographerviewmodel.singlePhotographerBooking.collectAsState()
    val singleotherservicesbookresult = otherservicesviewmodel.singleOtherServicesBooking.collectAsState()

    val amenitiesJson = Gson().toJson(singledecoratorbookresult.value?.bookedAmenities)
    val bookedPackagesJson = Gson().toJson(singlecateringbookresult.value?.bookedPackages)
    val bookedMenuItemsJson = Gson().toJson(singlecateringbookresult.value?.bookedMenuItems)


    val venues = remember { mutableStateListOf<Booking>() }
    val catering = remember { mutableStateListOf<Booking>() }
    val photography = remember { mutableStateListOf<Booking>() }
    val otherService = remember { mutableStateListOf<Booking>() }
    val decorationService = remember { mutableStateListOf<Booking>() }

    LaunchedEffect(selectedRating > 0) {

        if (selectedRating > 0) {
            val body = Ratings(
                bookingId = bookingId,
                comments = comments,
                rating = selectedRating,
                serviceId = serviceId,
                serviceType = serviceType
            )

            isLoading = true

            userviewmodel.giveRating(token = "Bearer $token", giverating = body)
            delay(3000)
        }
    }

    LaunchedEffect (called == "Decorators" && clicked) {
        if (called == "Decorators" && clicked) {
            decoratorviewmodel.fetchSingleDecoratorBooking(id = id, token = "Bearer $token")
            requestreceived = true
            isSheetopen = true
            clicked = false
        }
    }

    LaunchedEffect (called == "Venues" && clicked) {
        if (called == "Venues" && clicked) {
            venueviewmodel.fetchSingleVenueBooking(id = id, token = "Bearer $token")
            requestreceived = true
            isSheetopen = true
            clicked = false
        }
    }

    LaunchedEffect (called == "Photographer" && clicked) {
        if (called == "Photographer" && clicked) {
            photographerviewmodel.fetchSinglePhotographerBooking(id = id, token = "Bearer $token")
            requestreceived = true
            isSheetopen = true
            clicked = false
        }
    }

    LaunchedEffect (called == "Catering" && clicked) {
        if (called == "Catering" && clicked) {
            cateringviewmodel.fetchSingleCateringBooking(id = id, token = "Bearer $token")
            requestreceived = true
            isSheetopen = true
            clicked = false
        }
    }

    LaunchedEffect (called == "Vendor" && clicked) {
        if (called == "Vendor" && clicked) {
            otherservicesviewmodel.fetchSingleOtherServicesBooking(id = id, token = "Bearer $token")
            requestreceived = true
            isSheetopen = true
            clicked = false
        }
    }

    LaunchedEffect(bookings) {
        venues.clear()
        catering.clear()
        photography.clear()
        decorationService.clear()
        otherService.clear()

        bookings.forEach { booking ->
            when (booking.type) {
                "venue" -> venues.add(booking)
                "photography" -> photography.add(booking)
                "cateringService" -> catering.add(booking)
                "otherService" -> otherService.add(booking)
                "decorationService" -> decorationService.add(booking)
            }
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
                            fontFamily = Bebas,
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
                            text = if (BookingOptions[option].second == "Photographers") "Photo\ngraphers" else if (BookingOptions[option].second == "OtherServices") "Other\nServices" else BookingOptions[option].second,
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
                                items(
                                    items = venues,
                                    key = { it.bookingId }
                                ) { booking ->
                                    val dismissState = rememberSwipeToDismissBoxState(
                                        confirmValueChange = {
                                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                                venues.remove(booking)
                                                venueviewmodel.deleteVenueBooking(id = booking.bookingId, token = "Bearer $token")
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    )
                                    SwipeToDismissBox(
                                        state = dismissState,
                                        backgroundContent = {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .fillMaxWidth()
                                                    .height(100.dp)
                                                    .background(Color.Red)
                                                    .padding(horizontal = 20.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    ) {
                                        Approvecard(
                                            model = booking.cover,
                                            text = booking.serviceName,
                                            date = LocalDate.parse(booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                            time = booking.type,
                                            status = booking.status,
                                            onClick = {
                                                called = "Venues"
                                                id = booking.bookingId
                                                clicked = true
                                                bookingId = booking.bookingId
                                                serviceId = booking.serviceId
                                                serviceType = "VENUE"
                                                bookingname = booking.serviceName
                                            }
                                        )
                                    }
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
                                items(
                                    items = decorationService,
                                    key = { it.bookingId }
                                ) { decorators ->
                                    val dismissState = rememberSwipeToDismissBoxState(
                                        confirmValueChange = {
                                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                                val index = decorationService.indexOf(decorators)
                                                if (index != -1) {
                                                    decorationService.removeAt(index)
                                                    decoratorviewmodel.deleteDecoratorBooking(id = decorators.bookingId, token = "Bearer $token")
                                                }
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    )
                                    SwipeToDismissBox(
                                        state = dismissState,
                                        backgroundContent = {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .fillMaxWidth()
                                                    .height(100.dp)
                                                    .background(Color.Red)
                                                    .padding(horizontal = 20.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    ) {
                                        println("Size: ${decorationService.size}")
                                        Approvecard(
                                            model = decorators.cover,
                                            text = decorators.serviceName,
                                            date = LocalDate.parse(decorators.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                            time = decorators.type,
                                            status = decorators.status,
                                            onClick = {
                                                called = "Decorators"
                                                id = decorators.bookingId
                                                clicked = true
                                                bookingId = decorators.bookingId
                                                serviceId = decorators.serviceId
                                                serviceType = "DECORATIONSERVICE"
                                                bookingname = decorators.serviceName
                                            }
                                        )
                                    }
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
                                items(
                                    items = catering,
                                    key = { it.bookingId }
                                ) { caterer ->
                                    val dismissState = rememberSwipeToDismissBoxState(
                                        confirmValueChange = {
                                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                                catering.remove(caterer)
                                                cateringviewmodel.deleteDecoratorBooking(id = caterer.bookingId, token = "Bearer $token")
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    )
                                    SwipeToDismissBox(
                                        state = dismissState,
                                        backgroundContent = {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .fillMaxWidth()
                                                    .height(100.dp)
                                                    .background(Color.Red)
                                                    .padding(horizontal = 20.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    ) {
                                        Approvecard(
                                            model = caterer.cover,
                                            text = caterer.serviceName,
                                            date = LocalDate.parse(caterer.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                            time = caterer.type,
                                            status = caterer.status,
                                            onClick = {
                                                called = "Catering"
                                                id = caterer.bookingId
                                                clicked = true
                                                bookingId = caterer.bookingId
                                                serviceId = caterer.serviceId
                                                serviceType = "CATERINGSERVICE"
                                                bookingname = caterer.serviceName
                                            }
                                        )
                                    }
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
                                items(
                                    items = photography,
                                    key = { it.bookingId }
                                ) { photographer ->
                                    val dismissState = rememberSwipeToDismissBoxState(
                                        confirmValueChange = {
                                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                                photography.remove(photographer)
                                                photographerviewmodel.deletePhotographerBooking(id = photographer.bookingId, token = "Bearer $token")
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    )
                                    SwipeToDismissBox(
                                        state = dismissState,
                                        backgroundContent = {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .fillMaxWidth()
                                                    .height(100.dp)
                                                    .background(Color.Red)
                                                    .padding(horizontal = 20.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    ) {
                                        Approvecard(
                                            model = photographer.cover,
                                            text = photographer.serviceName,
                                            date = LocalDate.parse(photographer.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                            time = photographer.type,
                                            status = photographer.status,
                                            onClick = {
                                                called = "Photographer"
                                                id = photographer.bookingId
                                                clicked = true
                                                bookingId = photographer.bookingId
                                                serviceId = photographer.serviceId
                                                serviceType = "PHOTOGRAPHER"
                                                bookingname = photographer.serviceName
                                            }
                                        )
                                    }
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
                                items(
                                    items = otherService,
                                    key = { it.bookingId }
                                ) { vendor ->
                                    val dismissState = rememberSwipeToDismissBoxState(
                                        confirmValueChange = {
                                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                                photography.remove(vendor)
                                                otherservicesviewmodel.deleteVendorBooking(id = vendor.bookingId, token = "Bearer $token")
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    )
                                    SwipeToDismissBox(
                                        state = dismissState,
                                        backgroundContent = {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .fillMaxWidth()
                                                    .height(100.dp)
                                                    .background(Color.Red)
                                                    .padding(horizontal = 20.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    ) {
                                        Approvecard(
                                            model = vendor.cover,
                                            text = vendor.serviceName,
                                            date = LocalDate.parse(vendor.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                            time = vendor.type,
                                            status = vendor.status,
                                            onClick = {
                                                called = "Vendor"
                                                id = vendor.bookingId
                                                clicked = true
                                                bookingId = vendor.bookingId
                                                serviceId = vendor.serviceId
                                                serviceType = "OTHERSERVICE"
                                                bookingname = vendor.serviceName
                                            }
                                        )
                                    }
                                    AddHeight(20.dp)
                                }
                            }
                        }
                    }
                }
            }
            if (isSheetopen) {
                ModalBottomSheet(
                    sheetState = bottomSheetState,
                    onDismissRequest = {
                        isSheetopen = false
                    },
                    containerColor = backgroundcolor
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (requestreceived && called == "Decorators") {
                            if(singledecoratorbookresult.value != null) {
                                Funca(
                                    text = LocalDate.parse(singledecoratorbookresult.value!!.booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                    icon = Icons.Default.CalendarMonth
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = "${singledecoratorbookresult.value!!.booking.startTime} - ${singledecoratorbookresult.value!!.booking.endTime}",
                                    icon = Icons.Default.Alarm
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singledecoratorbookresult.value!!.booking.status.lowercase(),
                                    icon = Icons.Default.Warning
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singledecoratorbookresult.value!!.booking.bill,
                                    icon = Icons.Default.Money
                                )
                                AddHeight(20.dp)
                                singledecoratorbookresult.value!!.bookedAmenities.forEach {
                                    Funca(
                                        text = "${it.amenity} count: ${it.count}"
                                    )
                                    AddHeight(20.dp)
                                }
                                Button(
                                    onClick = {
                                        called = "ReviewTime"
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(fraction = 0.85f)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = secondarycolor,
                                        contentColor = backgroundcolor
                                    ),
                                    shape = RoundedCornerShape(5.dp)
                                ) {
                                    Text("Review")
                                }
                                if (singledecoratorbookresult.value!!.booking.status == "REQUESTED") {
                                    Button(
                                        onClick = { navController.navigate(
                                            "Decorators_Screen/" +
                                                    "${singledecoratorbookresult.value!!.decorationService.id}" +
                                                    "?startTime=${singledecoratorbookresult.value!!.booking.startTime}" +
                                                    "&endTime=${singledecoratorbookresult.value!!.booking.endTime}" +
                                                    "&address=${singledecoratorbookresult.value!!.booking.address}" +
                                                    "&bill=${singledecoratorbookresult.value!!.booking.bill}" +
                                                    "&date=${LocalDate.parse(singledecoratorbookresult.value!!.booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}" +
                                                    "&amenities=$amenitiesJson" +
                                                    "&edit=${true}" +
                                                    "&bookingId=${singledecoratorbookresult.value!!.booking.id}"
                                        ) },
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.85f)
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = secondarycolor,
                                            contentColor = backgroundcolor
                                        ),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text("Edit")
                                    }
                                }
                                AddHeight(20.dp)
                            }
                            else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(170.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        else if (requestreceived && called == "Venues") {
                            if(singlevenuebookresult.value != null) {
                                Funca(
                                    text = LocalDate.parse(singlevenuebookresult.value!!.booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                    icon = Icons.Default.CalendarMonth
                                )
                                AddHeight(20.dp)
                                Row(
                                    modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Funca(
                                        text = singlevenuebookresult.value!!.booking.startTime,
                                        icon = Icons.Default.AccessTimeFilled,
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .height(50.dp),
                                    )
                                    Funca(
                                        text = singlevenuebookresult.value!!.booking.endTime,
                                        icon = Icons.Default.AccessTimeFilled,
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .height(50.dp),
                                    )
                                }
                                AddHeight(20.dp)
                                Funca(
                                    text = singlevenuebookresult.value!!.booking.status.lowercase(),
                                    icon = Icons.Default.Warning
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singlevenuebookresult.value!!.booking.bill,
                                    icon = Icons.Default.Money
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singlevenuebookresult.value!!.booking.guestCount.toString(),
                                    icon = Icons.Default.Person
                                )
                                AddHeight(20.dp)
                                if (singlevenuebookresult.value!!.booking.status == "FULFILLED") {
                                    Button(
                                        onClick = {
                                            called = "ReviewTime"
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.85f)
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = secondarycolor,
                                            contentColor = backgroundcolor
                                        ),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text("Review")
                                    }
                                }
                                if (singlevenuebookresult.value!!.booking.status == "REQUESTED") {
                                    Button(
                                        onClick = {
                                            navController.navigate(
                                                "Venues_Screen/" +
                                                "${singlevenuebookresult.value!!.venue.id}" +
                                                "?startTime=${singlevenuebookresult.value!!.booking.startTime}" +
                                                "&endTime=${singlevenuebookresult.value!!.booking.endTime}" +
                                                "&address=${singlevenuebookresult.value!!.booking.address}" +
                                                "&bill=${singlevenuebookresult.value!!.booking.bill}" +
                                                "&date=${
                                                    LocalDate.parse(
                                                        singlevenuebookresult.value!!.booking.date.substring(
                                                            0,
                                                            10
                                                        )
                                                    ).format(
                                                        DateTimeFormatter.ofPattern(
                                                            "dd MMM yyyy"
                                                        )
                                                    )
                                                }" +
                                                "&guestCount=${singlevenuebookresult.value!!.booking.guestCount}" +
                                                "&edit=${true}" +
                                                "&bookingId=${singlevenuebookresult.value!!.booking.id}"
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.85f)
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = secondarycolor,
                                            contentColor = backgroundcolor
                                        ),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text("Edit")
                                    }
                                }
                                AddHeight(20.dp)
                            }
                            else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(170.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        else if (requestreceived && called == "Photographer") {
                            if(singlephotographerbookresult.value != null) {
                                Funca(
                                    text = LocalDate.parse(singlephotographerbookresult.value!!.booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                    icon = Icons.Default.CalendarMonth
                                )
                                AddHeight(20.dp)
                                Row(
                                    modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Funca(
                                        text = singlephotographerbookresult.value!!.booking.startTime,
                                        icon = Icons.Default.AccessTimeFilled,
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .height(50.dp),
                                    )
                                    Funca(
                                        text = singlephotographerbookresult.value!!.booking.endTime,
                                        icon = Icons.Default.AccessTimeFilled,
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .height(50.dp),
                                    )
                                }
                                AddHeight(20.dp)
                                Funca(
                                    text = singlephotographerbookresult.value!!.booking.status.lowercase(),
                                    icon = Icons.Default.Warning
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singlephotographerbookresult.value!!.booking.bill,
                                    icon = Icons.Default.Money
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singlephotographerbookresult.value!!.photographyBooking.eventType,
                                    icon = Icons.Default.Money
                                )
                                AddHeight(20.dp)
                                if (singlephotographerbookresult.value!!.booking.status == "FULFILLED") {
                                    Button(
                                        onClick = {
                                            called = "ReviewTime"
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.85f)
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = secondarycolor,
                                            contentColor = backgroundcolor
                                        ),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text("Review")
                                    }
                                }
                                if (singlephotographerbookresult.value!!.booking.status == "REQUESTED") {
                                    Button(
                                        onClick = { navController.navigate(
                                            "Photographers_Screen/" +
                                                    "${singlephotographerbookresult.value!!.photography.id}" +
                                                    "?startTime=${singlephotographerbookresult.value!!.booking.startTime}" +
                                                    "&endTime=${singlephotographerbookresult.value!!.booking.endTime}" +
                                                    "&address=${singlephotographerbookresult.value!!.booking.address}" +
                                                    "&bill=${singlephotographerbookresult.value!!.booking.bill}" +
                                                    "&date=${LocalDate.parse(singlephotographerbookresult.value!!.booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}" +
                                                    "&eventtype=${singlephotographerbookresult.value!!.photographyBooking.eventType}" +
                                                    "&edit=${true}" +
                                                    "&bookingId=${singlephotographerbookresult.value!!.booking.id}"
                                        ) },
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.85f)
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = secondarycolor,
                                            contentColor = backgroundcolor
                                        ),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text("Edit")
                                    }
                                }
                                AddHeight(20.dp)
                            }
                            else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(170.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        else if (requestreceived && called == "Catering") {
                            if(singlecateringbookresult.value != null) {
                                Funca(
                                    text = LocalDate.parse(singlecateringbookresult.value!!.booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                    icon = Icons.Default.CalendarMonth
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singlecateringbookresult.value!!.booking.startTime,
                                    icon = Icons.Default.AccessTimeFilled,
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singlecateringbookresult.value!!.booking.status.lowercase(),
                                    icon = Icons.Default.Warning
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singlecateringbookresult.value!!.booking.bill,
                                    icon = Icons.Default.Money
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singlecateringbookresult.value!!.cateringBooking.guestCount.toString(),
                                    icon = Icons.Default.Person
                                )
                                AddHeight(20.dp)
                                if (singlecateringbookresult.value!!.booking.status == "FULFILLED") {
                                    Button(
                                        onClick = {
                                            called = "ReviewTime"
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.85f)
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = secondarycolor,
                                            contentColor = backgroundcolor
                                        ),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text("Review")
                                    }
                                }
                                if (singlecateringbookresult.value!!.booking.status == "REQUESTED") {
                                    Button(
                                        onClick = { navController.navigate(
                                            "Caterers_Screen/" +
                                                    "${singlecateringbookresult.value!!.cateringService.id}" +
                                                    "?startTime=${singlecateringbookresult.value!!.booking.startTime}" +
                                                    "&endTime=${singlecateringbookresult.value!!.booking.endTime}" +
                                                    "&address=${singlecateringbookresult.value!!.booking.address}" +
                                                    "&bill=${singlecateringbookresult.value!!.booking.bill}" +
                                                    "&date=${LocalDate.parse(singlecateringbookresult.value!!.booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}" +
                                                    "&edit=${true}" +
                                                    "&bookingId=${singlecateringbookresult.value!!.booking.id}" +
                                                    "&bookedPackages=${bookedPackagesJson}" +
                                                    "&bookedMenuItems=${bookedMenuItemsJson}"
                                        ) },
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.85f)
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = secondarycolor,
                                            contentColor = backgroundcolor
                                        ),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text("Edit")
                                    }
                                }
                                AddHeight(20.dp)
                            }
                            else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(170.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        else if (requestreceived && called == "Vendor") {
                            if(singleotherservicesbookresult.value != null) {
                                Funca(
                                    text = LocalDate.parse(singleotherservicesbookresult.value!!.booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                    icon = Icons.Default.CalendarMonth
                                )
                                AddHeight(20.dp)
                                Row(
                                    modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Funca(
                                        text = singleotherservicesbookresult.value!!.booking.startTime,
                                        icon = Icons.Default.AccessTimeFilled,
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .height(50.dp),
                                    )
                                    Funca(
                                        text = singleotherservicesbookresult.value!!.booking.endTime,
                                        icon = Icons.Default.AccessTimeFilled,
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .height(50.dp),
                                    )
                                }
                                AddHeight(20.dp)
                                Funca(
                                    text = singleotherservicesbookresult.value!!.booking.status.lowercase(),
                                    icon = Icons.Default.Warning
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singleotherservicesbookresult.value!!.booking.bill,
                                    icon = Icons.Default.Money
                                )
                                AddHeight(20.dp)
                                Funca(
                                    text = singleotherservicesbookresult.value!!.otherServiceBooking.serviceCount.toString(),
                                    icon = Icons.Default.Numbers
                                )
                                AddHeight(20.dp)
                                if (singleotherservicesbookresult.value!!.booking.status == "FULFILLED") {
                                    Button(
                                        onClick = {
                                            called = "ReviewTime"
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.85f)
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = secondarycolor,
                                            contentColor = backgroundcolor
                                        ),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text("Review")
                                    }
                                }
                                if (singleotherservicesbookresult.value!!.booking.status == "REQUESTED") {
                                    Button(
                                        onClick = { navController.navigate(
                                            "OtherServices_Screen/" +
                                                    "${singleotherservicesbookresult.value!!.otherService.id}" +
                                                    "?startTime=${singleotherservicesbookresult.value!!.booking.startTime}" +
                                                    "&endTime=${singleotherservicesbookresult.value!!.booking.endTime}" +
                                                    "&address=${singleotherservicesbookresult.value!!.booking.address}" +
                                                    "&bill=${singleotherservicesbookresult.value!!.booking.bill}" +
                                                    "&date=${LocalDate.parse(singleotherservicesbookresult.value!!.booking.date.substring(0, 10)).format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}" +
                                                    "&serviceCount=${singleotherservicesbookresult.value!!.otherServiceBooking.serviceCount}" +
                                                    "&edit=${true}" +
                                                    "&bookingId=${singleotherservicesbookresult.value!!.booking.id}"
                                        ) },
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.85f)
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = secondarycolor,
                                            contentColor = backgroundcolor
                                        ),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text("Edit")
                                    }
                                }
                                AddHeight(20.dp)
                            }
                            else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(170.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        else if (called == "ReviewTime") {
                            Text(bookingname, fontSize = 32.sp, fontFamily = Bebas, color = secondarycolor)
                            AddHeight(30.dp)
                            Text("Rate Our Service and Help Others", fontSize = 25.sp, fontFamily = Bebas, color = secondarycolor)
                            AddHeight(50.dp)
                            if (selectedRating == 0) {
                                Row (
                                    modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    for (i in 1..5) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = if (i <= selectedRating) Color.Yellow else Color.Gray,
                                            modifier = Modifier
                                                .size(25.dp)
                                                .clickable {
                                                    selectedRating = i
                                                }
                                        )
                                    }
                                }
                                AddHeight(50.dp)
                                TextField(
                                    label = { Text("Comment") },
                                    value = comments,
                                    onValueChange = setcomments,
                                    modifier = Modifier
                                        .fillMaxWidth(fraction = 0.85f)
                                        .height(50.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = secondarycolor,
                                        unfocusedContainerColor = secondarycolor,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        disabledLabelColor = backgroundcolor,
                                        unfocusedLabelColor = backgroundcolor,
                                        focusedLabelColor = backgroundcolor,
                                        disabledTextColor = backgroundcolor,
                                        focusedTextColor = backgroundcolor,
                                        unfocusedTextColor = backgroundcolor
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    textStyle = TextStyle(
                                        color = backgroundcolor,
                                        fontSize = dimens.cardfont
                                    )
                                )
                                AddHeight(100.dp)
                            }
                            if(isLoading) {
                                CircularProgressIndicator()
                                AddHeight(100.dp)
                            }
                            else {
                                Text(answer, fontSize = 25.sp, fontFamily = Bebas, color = secondarycolor)
                                AddHeight(100.dp)
                            }
                        }
                    }
                }
            }

            if(selectedRating > 0) {
                when (ratingresult.value) {
                    is NetworkResponse.Failure -> {
                        answer = "Error giving Rating"
                        isLoading = false
                    }
                    NetworkResponse.Loading -> isLoading = true
                    is NetworkResponse.Success -> {
                        isLoading = false
                        isSheetopen = false
                        answer = "Thank You for Your Rating"
                    }
                    else -> {}
                }
            }
        }
    }
}