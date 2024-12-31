package com.example.mezbaan.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Brightness3
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.response.NetworkResponse
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.daycolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.navyblue
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.UserViewModel
import com.example.mezbaan.viewmodel.VenueViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.google.accompanist.flowlayout.FlowRow
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun BusinessCard(managername: String, contact: String) {
    val context = LocalContext.current
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                managername,
                fontSize = dimens.fontsize,
            )
            AddHeight(dimens.small1)
            Box (
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundcolor)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Phone Number",
                    fontSize = dimens.buttontext,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$contact")
                        }
                        context.startActivity(intent)
                    },
                    color = secondarycolor
                )
            }
        }
    }
}

@Composable
fun Funca(
    color: Color = secondarycolor,
    text: String,
    icon : ImageVector? = null,
    tcolor: Color = backgroundcolor,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
        .fillMaxWidth(fraction = 0.85f)
        .height(50.dp)
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = color,
            contentColor = tcolor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(icon, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text)
        }
    }
}

@Composable
fun Cardammedity(text : String) {
    Box (
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundcolor)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 15.sp, color = secondarycolor)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Venues(
    venueviewmodel: VenueViewModel = hiltViewModel(),
    userviewmodel: UserViewModel,
    venuee: Int,
    navController: NavController,
    startTime: String,
    endTime: String,
    date: String,
    bill: String,
    edit: Boolean,
    bookingId: Int,
    guestCount: Float
) {
    val venue = venueviewmodel.singleVenue.collectAsState().value
    val stepSize = 25f
    val minValue = 50f
    val pagecount = if(venue?.otherImages?.isNotEmpty() == true) venue.otherImages.size else 1
    val context = LocalContext.current
    val maxValue = venue?.capacity?.toFloat()
    val coroutineScope = rememberCoroutineScope()
    val token = userviewmodel.token.collectAsState()
    val dateDialogState = rememberMaterialDialogState()
    var clicked by remember { mutableStateOf(false) }
    var successful by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var isDay by rememberSaveable { mutableStateOf(startTime != "20:30") }
    val pagerState = rememberPagerState(pageCount = { pagecount })
    var sliderpos by remember { mutableFloatStateOf(guestCount) }
    var requestreceived by remember { mutableStateOf(false) }
    var isSheetopen by rememberSaveable { mutableStateOf(edit) }
    val launchdialogbox by venueviewmodel.isDialogVisible.collectAsState()
    val tcolor = if (!isDay) backgroundcolor else secondarycolor
    val price = if (!isDay) venue?.priceNight else venue?.priceDay
    val butcolor = if (isDay) backgroundcolor else secondarycolor
    val venuebookresult = venueviewmodel.venuebookingresult.observeAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(venuee) {
        venueviewmodel.getsinglevenue(venuee)
    }

    val initialPickedTime = remember {
        try {
            LocalTime.parse(startTime, DateTimeFormatter.ofPattern("hh:mm a"))
        } catch (e: Exception) {
            Log.e("DecoratorInputs", "Invalid startTime format: $startTime", e)
            LocalTime.NOON
        }
    }

    val initialPickedDate = remember {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"))
        } catch (e: Exception) {
            Log.e("DecoratorInputs", "Invalid date format: $date", e)
            LocalDate.now()
        }
    }

    val pickedtime by remember { mutableStateOf(initialPickedTime) }
    var pickeddate by remember { mutableStateOf(initialPickedDate) }

    val formatteddate by remember {
        derivedStateOf { DateTimeFormatter.ofPattern("dd MMM yyyy").format(pickeddate) }
    }

    val endtime by remember {
        derivedStateOf { pickedtime.plusHours(sliderpos.toLong()) }
    }

    val unavailableLocalDates: Set<LocalDate> = venue?.bookings?.mapNotNull { booking ->
        try {
            val dateOnly = booking.date.substringBefore("T")
            LocalDate.parse(dateOnly, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }?.toSet() ?: emptySet()

    val blockedDatesTillToday: Set<LocalDate> = generateSequence(LocalDate.of(1970, 1, 1)) { datee ->
        if (datee.isBefore(LocalDate.now())) datee.plusDays(1) else null
    }.toSet()

    val allBlockedDates = unavailableLocalDates + blockedDatesTillToday

    LaunchedEffect (clicked) {
        if(clicked) {
            val viewbookresults = venue?.let {
                VenueBook(
                    guestCount = sliderpos.toInt(),
                    startTime = if (!isDay) "20:30" else "13:30",
                    endTime = if (!isDay) "00:00" else "17:00",
                    date = formatteddate,
                    venueId = it.id,
                    bill = (price?.toInt() ?:0 ) + ( if (sliderpos.roundToInt() > venue.baseGuestCount) venue.incrementPrice.toString().toInt() * (sliderpos.roundToInt() - venue.baseGuestCount) / 50 else 1).toInt()
                )
            }
            if (!edit) {
                if (viewbookresults != null) {
                    venueviewmodel.bookvenue(viewbookresults, "Bearer ${token.value}")
                }
            }
            else {
                if (viewbookresults != null) {
                    venueviewmodel.editvenue(venuebook = viewbookresults, token = "Bearer ${token.value}", id = bookingId)
                }
            }
            clicked = false
            requestreceived = true
        }
    }

    if (venue != null) {
        LaunchedEffect(venue.otherImages.isNotEmpty()) {
            while (true) {
                yield()
                delay(3000)
                val nextPage = (pagerState.currentPage + 1) % pagecount
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = tween(durationMillis = 1200)
                    )
                }
            }
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConstraintLayout (
                modifier = Modifier
                    .fillMaxSize()
            ) {

                if (venue == null) {
                    Box (
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                else {
                    val (boximages, bottomcolumn) = createRefs()

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(fraction = 0.40f)
                            .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                            .constrainAs(boximages) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        if (venue.otherImages.isNotEmpty()) {
                            HorizontalPager(
                                state = pagerState,
                                key = { venue.otherImages[it] }
                            ) { index ->
                                Image(
                                    painter = rememberAsyncImagePainter(venue.otherImages[index]),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (venue.otherImages.isNotEmpty()) {
                                venue.otherImages.forEachIndexed { pageIndex, _ ->
                                    val isSelected = pageIndex == pagerState.currentPage
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) Color.Black else Color.Gray)
                                            .padding(4.dp)
                                    )
                                    if (pageIndex != venue.otherImages.size - 1) {
                                        AddWidth(8.dp)
                                    }
                                }
                            }
                        }
                    }
                    LazyColumn (
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.9f)
                            .constrainAs(bottomcolumn) {
                                top.linkTo(boximages.bottom, margin = 20.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.percent(0.9f)
                                bottom.linkTo(parent.bottom)
                                height = Dimension.fillToConstraints
                            },
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        item {
                            Column {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        venue.name,
                                        fontSize = dimens.fontsize,
                                    )

                                    Row (
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(30.dp))
                                            .background(backgroundcolor)
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                            .clickable {
                                                val geoUri = Uri.parse("geo:${venue.locationLink}")
                                                val intent = Intent(Intent.ACTION_VIEW, geoUri)
                                                intent.setPackage("com.google.android.apps.maps")
                                                context.startActivity(intent)
                                            }
                                    ) {
                                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = secondarycolor)
                                        Text("Karachi", color = secondarycolor)
                                    }
                                }
                                AddHeight(dimens.small1)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = null,
                                        modifier = Modifier.size(dimens.iconsize),
                                        tint = secondarycolor
                                    )
                                    AddWidth(10.dp)
                                    Text(venue.averageRating.toString(), fontSize = dimens.buttontext)
                                }
                            }
                            AddHeight(dimens.small1)
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth()
                            )
                            AddHeight(dimens.small1)
                            Text(
                                "Amenities",
                                fontSize = dimens.fontsize
                            )
                            AddHeight(dimens.small1)
                            FlowRow(
                                mainAxisSpacing = 10.dp,
                                crossAxisSpacing = 10.dp,
                            ) {
                                venue.amenities.forEach { amenity ->
                                    Cardammedity(amenity.toString())
                                }
                            }
                            AddHeight(dimens.small2)
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth()
                            )
                            AddHeight(dimens.small2)
                            BusinessCard(
                                managername = venue.managerName.toString(),
                                contact = venue.managerNumber
                            )
                            AddHeight(dimens.small2)
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = secondarycolor
                            )
                            AddHeight(dimens.small2)
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(fraction = 0.7f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Money,
                                        contentDescription = null,
                                        tint = secondarycolor
                                    )
                                    if (price != null) {
                                        Text(price)
                                    }
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        tint = secondarycolor
                                    )
                                    Text("${venue.baseGuestCount}")
                                }
                                AddHeight(10.dp)
                                Row(
                                    modifier = Modifier.fillMaxWidth(fraction = 0.7f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Money,
                                        contentDescription = null,
                                        tint = secondarycolor
                                    )
                                    Text(venue.incrementPrice.toString())
                                    Icon(
                                        Icons.Default.GroupAdd,
                                        contentDescription = null,
                                        tint = secondarycolor
                                    )
                                    Text("${venue.incrementStep}")
                                }
                            }
                            AddHeight(dimens.small2)
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = secondarycolor
                            )
                            AddHeight(dimens.small2)
                            Button(
                                onClick = { isSheetopen = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = backgroundcolor,
                                    contentColor = secondarycolor
                                ),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text("Proceed to Booking")
                            }
                            AddHeight(dimens.small2)
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
                    containerColor = if (isDay) daycolor else navyblue
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (!requestreceived) {
                            Row(
                                modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val selectedDayOrNight = if (isDay) "Day" else "Night"
                                Funca(
                                    color = butcolor,
                                    text = formatteddate,
                                    icon = Icons.Default.CalendarMonth,
                                    modifier = Modifier
                                        .fillMaxWidth(fraction = 0.5f)
                                        .height(50.dp),
                                    tcolor = tcolor
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(butcolor)
                                ) {
                                    IconButton(
                                        onClick = { dateDialogState.show() }
                                    ) {
                                        Icon(
                                            Icons.Default.CalendarMonth,
                                            contentDescription = null,
                                            tint = tcolor
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(butcolor)
                                ) {
                                    IconButton(
                                        onClick = {
                                            isDay = !isDay
                                        }
                                    ) {
                                        Icon(
                                            if (selectedDayOrNight == "Day") Icons.Default.Brightness7 else Icons.Default.Brightness3,
                                            contentDescription = null,
                                            tint = tcolor
                                        )
                                    }
                                }
                            }
                            AddHeight(20.dp)
                            Funca(color = butcolor, text = "Guest count ${sliderpos.roundToInt()}", icon = Icons.Default.Person, tcolor = tcolor)
                            AddHeight(20.dp)
                            if (price != null) {
                                if (venue != null) {
                                    Funca(
                                        color = butcolor,
                                        text = "Bill: ${price.toInt() + (if (sliderpos.roundToInt() > venue.baseGuestCount) {
                                            (venue.incrementPrice.toString()).toInt() * (sliderpos.roundToInt() - venue.baseGuestCount) / 50
                                        } else 0).toInt()}",
                                        icon = Icons.Default.Money,
                                        tcolor = tcolor
                                    )
                                }
                            }
                            AddHeight(10.dp)
                            if (maxValue != null) {
                                Slider(
                                    value = sliderpos,
                                    onValueChange = { newValue ->
                                        sliderpos =
                                            ((newValue - minValue) / stepSize).roundToInt() * stepSize + minValue
                                    },
                                    valueRange = minValue..maxValue,
                                    steps = ((maxValue - minValue) / stepSize).toInt() - 1,
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .graphicsLayer {
                                            shape = RoundedCornerShape(8.dp)
                                            clip = true
                                        },
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color.White,
                                        activeTrackColor = if (isDay) navyblue else secondarycolor,
                                        inactiveTrackColor = backgroundcolor
                                    ),
                                    thumb = {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(CircleShape)
                                                .background(Color.White)
                                        )
                                    }
                                )
                            }
                            AddHeight(10.dp)
                            Row(
                                modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Funca(
                                    color = butcolor,
                                    text = if (isDay) "1-30pm" else "8-30pm",
                                    icon = Icons.Default.AccessTimeFilled,
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .height(50.dp),
                                    tcolor = tcolor
                                )
                                Funca(
                                    color = butcolor,
                                    text = if (isDay) "5pm" else "12pm",
                                    icon = Icons.Default.AccessTimeFilled,
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .height(50.dp),
                                    tcolor = tcolor
                                )
                            }

                            AddHeight(30.dp)

                            Button(
                                onClick = { clicked = true },
                                modifier = Modifier
                                    .fillMaxWidth(fraction = 0.85f)
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = butcolor,
                                    contentColor = tcolor
                                ),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text("Confirm Booking")
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
                                if (isLoading) {
                                    CircularProgressIndicator()
                                }
                                else {
                                    if (successful) {
                                        Funca(
                                            icon = Icons.Default.FastForward,
                                            text = "Request Sent"
                                        )
                                    }
                                    else {
                                        Funca(
                                            icon = Icons.Default.Error,
                                            text = "Request Failed"
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
                MaterialDialog (
                    dialogState = dateDialogState,
                    properties = DialogProperties(

                    ),
                    backgroundColor = backgroundcolor,
                    buttons = {
                        positiveButton(
                            "ok",
                            textStyle = TextStyle(color = secondarycolor)
                        )
                    }
                ) {
                    datepicker(
                        initialDate = LocalDate.now(),
                        title = "Pick a date",
                        colors = DatePickerDefaults.colors(
                            headerBackgroundColor = secondarycolor,
                            headerTextColor = backgroundcolor,
                            calendarHeaderTextColor = secondarycolor,
                            dateActiveBackgroundColor = secondarycolor,
                            dateActiveTextColor = backgroundcolor,
                            dateInactiveTextColor = secondarycolor
                        ),
                        allowedDateValidator = { date ->
                            date !in allBlockedDates && date.isAfter(LocalDate.now())
                        }
                    ) {
                        pickeddate = it
                    }
                }
            }

            if(requestreceived) {
                when (venuebookresult.value) {
                    is NetworkResponse.Failure -> {
                        isLoading = false
                        successful = false
                    }
                    NetworkResponse.Loading -> isLoading = true
                    is NetworkResponse.Success -> {
                        isLoading = false
                        isSheetopen = false
                        requestreceived = false
                        successful = true
                    }
                    null -> { }
                }
            }

            if(launchdialogbox) {
                AlertDialog(
                    onDismissRequest = { venueviewmodel.closeDialog() },
                    confirmButton = {
                        Button(
                            onClick = {
                                venueviewmodel.closeDialog()
                                navController.navigate(route = Screens.Home.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = backgroundcolor,
                                contentColor = secondarycolor
                            )
                        ) {
                            Text("Close")
                        }
                    },
                    title = { Text("Venue Booking") },
                    text = { Text("your request is sent to the vendor") },
                    containerColor = backgroundcolor,
                    textContentColor = secondarycolor,
                    titleContentColor = secondarycolor
                )
            }
        }
    }
}