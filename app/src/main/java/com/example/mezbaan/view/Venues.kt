package com.example.mezbaan.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.R
import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.response.NetworkResponse
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.daycolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.navyblue
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.UserViewModel
import com.example.mezbaan.viewmodel.VenueViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun BusinessCard(managername: String, contact: String, pic: Painter) {
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
                color = secondarycolor
            )
            AddHeight(dimens.small1)
            Text(
                contact,
                fontSize = dimens.buttontext,
                color = secondarycolor,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$contact")
                    }
                    context.startActivity(intent)
                }
            )
        }
        Box (
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(dimens.logoSize)
                    .clip(CircleShape),
                painter = pic,
                contentDescription = null
            )
        }
    }
}

@Composable
fun Funca(
    color: Color = secondarycolor,
    text: String,
    icon : ImageVector,
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
            Icon(icon, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
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
@Preview
@Composable
fun Venues(
    venueviewmodel: VenueViewModel = viewModel(),
    userviewmodel: UserViewModel = viewModel()
) {
    val pics = listOf(
        "https://drive.google.com/uc?export=view&id=1L9yTLvgUau4U_uR8Bmft89GSrIUr6y9v",
        "https://drive.google.com/uc?export=view&id=179mOB6_5ewGP6ZiJjy7DGK9pJRg4WzA-",
        "https://drive.google.com/uc?export=view&id=1TchRSYUcoPdNp_-MGBaFirmmKNTXhTkt"
    )
    val stepSize = 25f
    val minValue = 50f
    val maxValue = 1200f
    val latitude = 24.8824115
    val longitude = 67.0585993
    val pagecount = pics.size
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dateDialogState = rememberMaterialDialogState()
    var isLoading by remember { mutableStateOf(false) }
    var launchdialogbox by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var pickeddate by remember { mutableStateOf(LocalDate.now()) }
    val pagerState = rememberPagerState(pageCount = { pagecount })
    var sliderpos by remember { mutableFloatStateOf(50.0f) }
    var clicked by remember { mutableStateOf(false) }
    val venuebookresult = venueviewmodel.venuebookingresult.observeAsState()
    val username = userviewmodel.username.collectAsState()
    val email = userviewmodel.email.collectAsState()
    val phone = userviewmodel.phone.collectAsState()
    var requestreceived by remember { mutableStateOf(false) }
    var isSheetopen by rememberSaveable { mutableStateOf(false) }
    var isDay by rememberSaveable { mutableStateOf(false) }
    val butcolor = if (isDay) backgroundcolor else secondarycolor
    val tcolor = if (!isDay) backgroundcolor else secondarycolor
    val price = if (!isDay) 400000 else (round((400000 / 1.5f) / 10000) * 10000).toInt()
    val formatteddate by remember { derivedStateOf { DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickeddate) } }
    val currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    LaunchedEffect (clicked) {
        if(clicked) {
            val viewbookresults = VenueBook(
                guestcount = sliderpos.toInt(),
                starttime = "8.30pm",
                endtime = "12:00am",
                edate = formatteddate,
                bdate = currentDateTime,
                locationname = "Corum",
                locationid = 1,
                username = username.value,
                email = email.value,
                phone = phone.value
            )
            venueviewmodel.bookvenue(viewbookresults)
            clicked = false
            requestreceived = true
        }
    }

    LaunchedEffect(Unit) {
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
                    HorizontalPager(
                        state = pagerState,
                        key = { pics[it] }
                    ) { index ->
                        Image(
                            painter = rememberAsyncImagePainter(pics[index]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        pics.forEachIndexed { pageIndex, _ ->
                            val isSelected = pageIndex == pagerState.currentPage
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color.Black else Color.Gray)
                                    .padding(4.dp)
                            )
                            if (pageIndex != pics.size - 1) {
                                AddWidth(8.dp)
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
                                    "Name of the venue",
                                    fontSize = dimens.fontsize,
                                    color = secondarycolor
                                )

                                Row (
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(30.dp))
                                        .background(backgroundcolor)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                        .clickable {
                                            val geoUri = Uri.parse("geo:$latitude,$longitude")
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
                                Text("4.5", fontSize = dimens.buttontext, color = secondarycolor)
                            }
                        }
                        AddHeight(dimens.small1)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = secondarycolor
                        )
                        AddHeight(dimens.small1)
                        Text(
                            "Amenities",
                            fontSize = dimens.fontsize,
                            color = secondarycolor
                        )
                        AddHeight(dimens.small1)
                        FlowRow(
                            mainAxisSpacing = 10.dp,
                            crossAxisSpacing = 10.dp,
                        ) {
                            Cardammedity("Wi-fi")
                            Cardammedity("Washing Machine")
                            Cardammedity("A/C")
                            Cardammedity("Washing Machine")
                            Cardammedity("Washing Machine")
                            Cardammedity("Washing Machine")
                        }
                        AddHeight(dimens.small2)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = secondarycolor
                        )
                        AddHeight(dimens.small2)
                        BusinessCard(
                            managername = "Ali",
                            contact = "+92 1345676789",
                            pic = painterResource(R.drawable.mezbaan)
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
                            Text("Price: 400000 up to 800 Guests", color = secondarycolor)

                            Text("Additional 10000 for adding 50 guests", color = secondarycolor)
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
                            Funca(color = butcolor, text = "Bill: ${price + ( if (sliderpos.roundToInt() > 800) 10000 * (sliderpos.roundToInt() - 800) / 50 else 0)}", icon = Icons.Default.Money, tcolor = tcolor)
                            AddHeight(10.dp)
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
                                onClick = { requestreceived = true },
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
                                    Funca(
                                        icon = Icons.Default.Error,
                                        text = "Request Failed"
                                    )
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
                        )
                    ) {
                        pickeddate = it
                    }
                }

                if(requestreceived) {
                    when (val request = venuebookresult.value) {
                        is NetworkResponse.Failure -> isLoading = false
                        NetworkResponse.Loading -> isLoading = true
                        is NetworkResponse.Success -> {
                            isLoading = false
                            if (request.data.result) {
                                isSheetopen = false
                                launchdialogbox = true
                            }
                        }
                        null -> { }
                    }
                }

                if(launchdialogbox) {
                    AlertDialog(
                        onDismissRequest = {launchdialogbox = false},
                        confirmButton = {
                            Button(
                                onClick = {launchdialogbox = false}
                            ) {
                                Text("Close")
                            }
                        },
                        title = { Text("Venue Booking") },
                        text = { Text("your request is sent to the vendor") }
                    )
                }
            }
        }
    }
}