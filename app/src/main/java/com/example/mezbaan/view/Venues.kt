package com.example.mezbaan.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Brightness3
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.google.accompanist.flowlayout.FlowRow
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun BusinessCard(managername: String, contact: String, pic: Painter) {
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
            Text(
                contact,
                fontSize = dimens.buttontext,
                color = Color.Gray
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
    text: String,
    icon : ImageVector,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxWidth(fraction = 0.85f).height(50.dp)
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = secondarycolor,
            contentColor = backgroundcolor
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
fun tailSwitch(
    onSwitchChange: (Boolean) -> Unit,
    first: String,
    second: String
): Boolean {
    var check by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(8.dp) // Reduced padding
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Switch(
                checked = check,
                onCheckedChange = {
                    check = it
                    onSwitchChange(it)
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = secondarycolor,
                    checkedThumbColor = backgroundcolor,
                    uncheckedTrackColor = secondarycolor,
                    uncheckedThumbColor = backgroundcolor
                ),
                modifier = Modifier.scale(0.8f) // Scaled down switch
            )
            Spacer(modifier = Modifier.width(4.dp)) // Reduced space
            Text(
                text = if (check) first else second,
                fontSize = 14.sp // Smaller text size
            )
        }
    }
    return check
}


@Composable
fun Cardammedity(text : String) {
    Box (
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 15.sp, color = Color.Black)
    }
}

@Composable
fun BookingCard(month: String) {
    Column {
        Text(text = month, fontSize = 20.sp)
    }
}

@Composable
fun GridOfBoxes(year: Int, month: Int, isday: Boolean, onDateClick: (Int, Int, Int, Boolean) -> Unit) {
    val days = YearMonth.of(year, month).lengthOfMonth()

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(days) { day ->
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(secondarycolor)
                    .clickable { onDateClick(day + 1, month, year, isday) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    (day + 1).toString(),
                    color = backgroundcolor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Venues() {
    val pics = listOf(
        R.drawable.mezbaan,
        R.drawable.b1,
        R.drawable.b2,
    )
    val pagerState = rememberPagerState(pageCount = { pics.size })
    val pagerState1 = rememberPagerState(initialPage = 0, pageCount = { 6 })
    val currentDate = LocalDate.now()
    val bottomSheetState = rememberModalBottomSheetState()
    var sliderpos by remember { mutableFloatStateOf(50.0f) }
    var isSheetopen by rememberSaveable { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Int?>(null) }
    var selectedMonth by remember { mutableStateOf<Int?>(null) }
    var selectedYear by remember { mutableStateOf<Int?>(null) }
    var isDay by rememberSaveable { mutableStateOf(false) }
    val stepSize = 25f
    val minValue = 50f
    val maxValue = 1200f

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
                            painter = painterResource(id = pics[index]),
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
                        }
                ) {
                    item {
                        Column {
                            Text(
                                "Name of the venue",
                                fontSize = dimens.fontsize,
                            )
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
                                    modifier = Modifier.size(dimens.iconsize)
                                )
                                AddWidth(10.dp)
                                Text("Rating of the venue", fontSize = dimens.buttontext)
                            }
                        }
                        AddHeight(dimens.small1)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Gray
                        )
                        AddHeight(dimens.small1)
                        Text(
                            "Amenities",
                            fontSize = dimens.fontsize,
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
                            color = Color.Gray
                        )
                        Box (
                            modifier = Modifier.padding(vertical = 20.dp)
                        ) {
                            HorizontalPager(
                                state = pagerState1,
                                pageSpacing = 20.dp
                            ) { page ->

                                val currentMonth = currentDate.plusMonths(page.toLong())
                                val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
                                val formattedDate = currentMonth.format(formatter)

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(backgroundcolor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = 0.9f)
                                            .fillMaxHeight(fraction = 0.9f),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(bottom = 30.dp),
                                            verticalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Row (
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                BookingCard(month = formattedDate)
                                                tailSwitch(
                                                    onSwitchChange = { isDay = it },
                                                    first = "Day",
                                                    second = "Night"
                                                )
                                            }
                                            //AddHeight(10.dp)
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(200.dp)
                                            ) {
                                                GridOfBoxes(
                                                    year = currentMonth.year,
                                                    month = currentMonth.monthValue,
                                                    isday = isDay,
                                                    onDateClick = { day, month, year, isday ->
                                                        isSheetopen = true
                                                        selectedDate = day
                                                        selectedMonth = month
                                                        selectedYear = year
                                                        isDay = isday
                                                    }
                                                )
                                            }
                                            AddHeight(10.dp)
                                        }
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 20.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {

                                repeat(6) { pageIndex ->
                                    val isSelected = pageIndex == pagerState1.currentPage
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) secondarycolor else Color.Blue)
                                            .padding(4.dp)
                                    )
                                    if (pageIndex != 5) {
                                        AddWidth(8.dp)
                                    }
                                }
                            }
                            AddHeight(20.dp)
                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Gray
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
                            color = Color.Gray
                        )
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
                    containerColor = backgroundcolor
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        selectedDate?.let { day ->
                            Row (
                                modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val selectedDayOrNight = if (isDay) "Day" else "Night"
                                Funca("Date: $day/$selectedMonth/$selectedYear", Icons.Default.CalendarMonth)
                                Icon(
                                    if (selectedDayOrNight == "Day") Icons.Default.Brightness7 else Icons.Default.Brightness3,
                                    contentDescription = null,
                                    tint = Color.Yellow
                                )
                            }
                        }
                        AddHeight(20.dp)
                        Funca("Guest count ${sliderpos.roundToInt()}", Icons.Default.Person)
                        AddHeight(20.dp)
                        Slider(
                            value = sliderpos,
                            onValueChange = { newValue ->
                                sliderpos = ((newValue - minValue) / stepSize).roundToInt() * stepSize + minValue
                            },
                            valueRange = minValue..maxValue,
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f)
                                .graphicsLayer {
                                    shape = CircleShape
                                    clip = true
                                },
                            colors = SliderDefaults.colors(
                                thumbColor = Color.Blue,
                                activeTrackColor = Color.Green,
                                inactiveTrackColor = Color.LightGray
                            ),
                            thumb = {
                                Box(
                                    modifier = Modifier
                                        .size(15.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF004b23))
                                )
                            }
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Funca(
                                text = if (isDay) "1-30pm" else "8-30pm",
                                Icons.Default.AccessTimeFilled,
                                modifier = Modifier
                                    .weight(0.5f)
                                    .height(50.dp)
                            )
                            Funca(
                                if (isDay) "5pm" else "12pm",
                                Icons.Default.AccessTimeFilled,
                                modifier = Modifier
                                    .weight(0.5f)
                                    .height(50.dp)
                            )
                        }

                        AddHeight(30.dp)

                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.85f).height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = secondarycolor,
                                contentColor = backgroundcolor
                            ),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text("Confirm Booking")
                        }
                    }
                }
            }
        }
    }
}