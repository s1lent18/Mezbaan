package com.example.mezbaan.view

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.google.accompanist.flowlayout.FlowRow
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.round
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
                color = secondarycolor
            )
            AddHeight(dimens.small1)
            Text(
                contact,
                fontSize = dimens.buttontext,
                color = secondarycolor
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
fun Venues() {
    val pics = listOf(
        "https://drive.google.com/uc?export=view&id=1L9yTLvgUau4U_uR8Bmft89GSrIUr6y9v",
        "https://drive.google.com/uc?export=view&id=179mOB6_5ewGP6ZiJjy7DGK9pJRg4WzA-",
        "https://drive.google.com/uc?export=view&id=1TchRSYUcoPdNp_-MGBaFirmmKNTXhTkt"
    )
    var pickeddate by remember { mutableStateOf(LocalDate.now()) }
    val formatteddate by remember { derivedStateOf { DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickeddate) } }
    val dateDialogState = rememberMaterialDialogState()
    val pagerState = rememberPagerState(pageCount = { pics.size })
    val bottomSheetState = rememberModalBottomSheetState()
    var sliderpos by remember { mutableFloatStateOf(50.0f) }
    var isSheetopen by rememberSaveable { mutableStateOf(false) }
    var isDay by rememberSaveable { mutableStateOf(false) }
    val price = if (!isDay) 400000 else (round((400000 / 1.5f) / 10000) * 10000).toInt()
    val butcolor = if (isDay) backgroundcolor else secondarycolor
    val tcolor = if (!isDay) backgroundcolor else secondarycolor
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
                            Text(
                                "Name of the venue",
                                fontSize = dimens.fontsize,
                                color = secondarycolor
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
                    containerColor = if (isDay) Color(0xFFffd07b) else Color(0xFF22223b)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .navigationBarsPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
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
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.85f)
                                .graphicsLayer {
                                    shape = CircleShape
                                    clip = true
                                },
                            colors = SliderDefaults.colors(
                                thumbColor = Color.Blue,
                                activeTrackColor = butcolor,
                                inactiveTrackColor = if (!isDay) Color.LightGray else Color.Black
                            ),
                            thumb = {
                                Box(
                                    modifier = Modifier
                                        .size(15.dp)
                                        .clip(CircleShape)
                                        .background(butcolor)
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
                            onClick = { },
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
            }
        }
    }
}