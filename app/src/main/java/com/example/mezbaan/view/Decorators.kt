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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.CounterButton
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.google.accompanist.flowlayout.FlowRow
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Decorators() {
    Surface {
        var launchhourpicker by remember { mutableStateOf(false) }
        val timeDialogState = rememberMaterialDialogState()
        var pickedtime by remember { mutableStateOf(LocalTime.NOON) }
        val formattedtime by remember { derivedStateOf { DateTimeFormatter.ofPattern("hh:mm a").format(pickedtime) } }
        var pickeddate by remember { mutableStateOf(LocalDate.now()) }
        val formatteddate by remember { derivedStateOf { DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickeddate) } }
        val dateDialogState = rememberMaterialDialogState()
        var isSheetopen by remember { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        var sliderpos by remember { mutableFloatStateOf(0f) }
        val valueCounter = remember { mutableStateListOf(0, 0, 0, 0, 0) }
        val stepSize = 1f
        val minValue = 1f
        val maxValue = 24f

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConstraintLayout (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (boximage, bottomcolumn, confirmbutton) = createRefs()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.40f)
                        .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                        .constrainAs(boximage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    val painter = rememberAsyncImagePainter(model = "https://drive.google.com/uc?export=view&id=1qzCG3vIVY9uyP-g5ZsGJFfMcP36cHuJi")
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

                Column (
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.9f)
                        .constrainAs(bottomcolumn) {
                            top.linkTo(boximage.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.9f)
                            bottom.linkTo(confirmbutton.top, margin = 20.dp)
                            height = Dimension.fillToConstraints
                        },
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Name of the Decorators",
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
                        Cardammedity("Tents")
                        Cardammedity("Fans")
                        Cardammedity("A/C")
                        Cardammedity("Cutlery")
                        Cardammedity("Waiters")
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
                }
                Button(
                    onClick = { isSheetopen = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .constrainAs(confirmbutton) {
                            //top.linkTo(bottomcolumn.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.9f)
                            bottom.linkTo(parent.bottom, margin = 30.dp)
                        }
                    ,
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
                        Row (
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Funca(
                                text = "Tents",
                                icon = Icons.Default.AccountBalance,
                                modifier = Modifier.fillMaxWidth(fraction = 0.5f).height(50.dp)
                            )
                            CounterButton(
                                value = valueCounter[0].toString(),
                                onValueIncreaseClick = {
                                    valueCounter[0] += 1
                                },
                                onValueDecreaseClick = {
                                    valueCounter[0] = maxOf(valueCounter[0] - 1, 0)
                                },
                                onValueClearClick = {
                                    valueCounter[0] = 0
                                }
                            )
                            AddWidth(20.dp)
                        }
                        AddHeight(20.dp)
                        Row (
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Funca(
                                text = "Cutlery",
                                icon = Icons.Default.Restaurant,
                                modifier = Modifier.fillMaxWidth(fraction = 0.5f).height(50.dp)
                            )
                            CounterButton(
                                value = valueCounter[1].toString(),
                                onValueIncreaseClick = {
                                    valueCounter[1] += 1
                                },
                                onValueDecreaseClick = {
                                    valueCounter[1] = maxOf(valueCounter[1] - 1, 0)
                                },
                                onValueClearClick = {
                                    valueCounter[1] = 0
                                }
                            )
                            AddWidth(20.dp)
                        }
                        AddHeight(20.dp)
                        Row (
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Funca(
                                text = "Fans",
                                icon = Icons.Default.Air,
                                modifier = Modifier.fillMaxWidth(fraction = 0.5f).height(50.dp)
                            )
                            CounterButton(
                                value = valueCounter[2].toString(),
                                onValueIncreaseClick = {
                                    valueCounter[2] += 1
                                },
                                onValueDecreaseClick = {
                                    valueCounter[2] = maxOf(valueCounter[2] - 1, 0)
                                },
                                onValueClearClick = {
                                    valueCounter[2] = 0
                                }
                            )
                            AddWidth(20.dp)
                        }
                        AddHeight(20.dp)
                        Row (
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Funca(
                                text = "A/C",
                                icon = Icons.Default.AcUnit,
                                modifier = Modifier.fillMaxWidth(fraction = 0.5f).height(50.dp)
                            )
                            CounterButton(
                                value = valueCounter[3].toString(),
                                onValueIncreaseClick = {
                                    valueCounter[3] += 1
                                },
                                onValueDecreaseClick = {
                                    valueCounter[3] = maxOf(valueCounter[3] - 1, 0)
                                },
                                onValueClearClick = {
                                    valueCounter[3] = 0
                                }
                            )
                            AddWidth(20.dp)
                        }
                        AddHeight(20.dp)
                        Row (
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Funca(
                                text = "Waiters",
                                icon = Icons.Default.Person,
                                modifier = Modifier.fillMaxWidth(fraction = 0.5f).height(50.dp)
                            )
                            CounterButton(
                                value = valueCounter[4].toString(),
                                onValueIncreaseClick = {
                                    valueCounter[4] += 1
                                },
                                onValueDecreaseClick = {
                                    valueCounter[4] = maxOf(valueCounter[4] - 1, 0)
                                },
                                onValueClearClick = {
                                    valueCounter[4] = 0
                                }
                            )
                            AddWidth(20.dp)
                        }
                        AddHeight(20.dp)
                        Funca(
                            text = "Bill: ${(
                                     1000 * valueCounter[0]) + 
                                     120 * (valueCounter[1]) + 
                                     500 * (valueCounter[2]) +
                                     1500 * (valueCounter[3]) + 
                                     2500 * (valueCounter[4])
                            }",
                            icon = Icons.Default.Money
                        )
                        AddHeight(20.dp)
                        Row (
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Funca(
                                text = "$formattedtime - ${sliderpos.toInt()} hours",
                                icon = Icons.Default.AccessTime,
                                modifier = Modifier
                                    .fillMaxWidth(fraction = 0.65f)
                                    .height(50.dp)
                            )
                            Box (
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(secondarycolor)
                            ) {
                                IconButton(
                                    onClick = { timeDialogState.show() }
                                ) {
                                    Icon(
                                        Icons.Default.AccessTime,
                                        contentDescription = null,
                                        tint = backgroundcolor
                                    )
                                }
                            }
                            Box (
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(secondarycolor)
                            ) {
                                IconButton(
                                    onClick = { launchhourpicker = true }
                                ) {
                                    Icon(
                                        Icons.Default.AlarmAdd,
                                        contentDescription = null,
                                        tint = backgroundcolor
                                    )
                                }
                            }
                        }
                        AddHeight(20.dp)
                        Row (
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Funca(
                                text = formatteddate,
                                icon = Icons.Default.CalendarMonth,
                                modifier = Modifier
                                    .fillMaxWidth(fraction = 0.8f)
                                    .height(50.dp)
                            )
                            Box (
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(secondarycolor)
                            ) {
                                IconButton(
                                    onClick = { dateDialogState.show() }
                                ) {
                                    Icon(
                                        Icons.Default.CalendarMonth,
                                        contentDescription = null,
                                        tint = backgroundcolor
                                    )
                                }
                            }
                        }
                        AddHeight(20.dp)
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.85f)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = secondarycolor,
                                contentColor = backgroundcolor
                            ),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text("Confirm Booking")
                        }
                        AddHeight(20.dp)
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
                MaterialDialog (
                    dialogState = timeDialogState,
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
                    timepicker(
                        initialTime = LocalTime.NOON,
                        title = "Pick a time",
                        colors = TimePickerDefaults.colors(
                            headerTextColor = secondarycolor,
                            selectorTextColor = backgroundcolor,
                            selectorColor = secondarycolor,
                            activeBackgroundColor = secondarycolor,
                            activeTextColor = backgroundcolor,
                            inactiveTextColor = secondarycolor
                        ),
                        is24HourClock = true
                    ) {
                        pickedtime = it
                    }
                }

                if (launchhourpicker) {
                    AlertDialog(
                        onDismissRequest = { launchhourpicker = false },
                        title = { Text("Choose Number of Hours", color = secondarycolor, fontSize = dimens.fontsize) },
                        text = {
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Value: ${sliderpos.toInt()}",
                                    color = secondarycolor,
                                    fontSize = dimens.buttontext
                                )
                                AddHeight(dimens.medium2)
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
                                        activeTrackColor = secondarycolor,
                                        inactiveTrackColor = Color(0xFF023047)
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
                        },
                        confirmButton = {
                            TextButton(onClick = { launchhourpicker = false }) {
                                Text("Close", color = secondarycolor)
                            }
                        },
                        containerColor = backgroundcolor
                    )
                }
            }
        }
    }
}