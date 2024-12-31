package com.example.mezbaan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.model.dataclasses.DecoratorBook
import com.example.mezbaan.model.models.AmenityX
import com.example.mezbaan.model.models.BookedAmenity
import com.example.mezbaan.model.response.NetworkResponse
import com.example.mezbaan.ui.theme.CounterButton
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.DecoratorViewModel
import com.example.mezbaan.viewmodel.UserViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
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

fun calculateBill(amenitiesWithCount: List<Pair<AmenityX, Int>>): Double {
    return amenitiesWithCount.sumOf { (amenity, count) ->
        (amenity.cost) * count
    }.toDouble()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Decorators(
    userviewmodel: UserViewModel,
    decoratorr: Int,
    decoratorviewmodel : DecoratorViewModel = hiltViewModel(),
    navController: NavController,
    startTime: String,
    endTime: String,
    date: String,
    bill: String,
    addresss: String,
    booked: List<BookedAmenity>,
    edit: Boolean,
    bookingId: Int
) {
    Surface {
        val decorator = decoratorviewmodel.singleDecorator.collectAsState().value
        val stepSize = 1f
        val minValue = 1f
        val maxValue = 24f
        val listState = rememberLazyListState()
        val token by userviewmodel.token.collectAsState()
        val dateDialogState = rememberMaterialDialogState()
        val timeDialogState = rememberMaterialDialogState()
        var clicked by remember { mutableStateOf(false) }
        var isSheetopen by remember { mutableStateOf(edit) }
        var sliderpos by remember { mutableFloatStateOf(
            calculateHoursDifference(startTime, endTime)
        ) }
        var isLoading by remember { mutableStateOf(false) }
        val decoratorbookresult = decoratorviewmodel.decoratorbookresult.observeAsState()
        val editdecoratorbookresult = decoratorviewmodel.editbookingresult.observeAsState()
        val (address, setaddress) = remember { mutableStateOf(addresss) }
        var launchhourpicker by remember { mutableStateOf(false) }
        var requestreceived by remember { mutableStateOf(false) }
        val launchdialogbox by decoratorviewmodel.isDialogVisible.collectAsState()
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        LaunchedEffect(decoratorr) {
            decoratorviewmodel.getsingledecoration(decoratorr)
        }

        val valueCounter = remember(decorator?.amenities) {
            mutableStateListOf<Pair<AmenityX?, Int>>().apply {
                decorator?.amenities?.forEach { amenity ->
                    val count = booked.find { it.decorationAmenityId == amenity.id }?.count ?: 0
                    add(Pair(amenity, count))
                }
            }
        }

        val initialPickedTime = remember {
            try {
                LocalTime.parse(startTime, DateTimeFormatter.ofPattern("hh:mm a"))
            } catch (e: Exception) {
                LocalTime.NOON
            }
        }

        val initialPickedDate = remember {
            try {
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"))
            } catch (e: Exception) {
                LocalDate.now()
            }
        }

        var pickedtime by remember { mutableStateOf(initialPickedTime) }
        var pickeddate by remember { mutableStateOf(initialPickedDate) }

        val formattedtime by remember {
            derivedStateOf { DateTimeFormatter.ofPattern("hh:mm a").format(pickedtime) }
        }

        val formatteddate by remember {
            derivedStateOf { DateTimeFormatter.ofPattern("dd MMM yyyy").format(pickeddate) }
        }

        val endtime by remember {
            derivedStateOf { pickedtime.plusHours(sliderpos.toLong()) }
        }

        val formattedEndTime by remember {
            derivedStateOf { DateTimeFormatter.ofPattern("hh:mm a").format(endtime) }
        }

        val selectedAmenities = remember { mutableStateListOf<com.example.mezbaan.model.dataclasses.Amenity>() }

        LaunchedEffect (clicked) {
            if(clicked) {
                val decoratorbookresults = decorator?.let { it ->
                    DecoratorBook(
                        address = address,
                        bill = calculateBill(valueCounter.filter { it.first != null }.map { it.first!! to it.second }),
                        date = formatteddate,
                        decorationServiceId = it.id,
                        startTime = formattedtime,
                        endTime = formattedEndTime,
                        amenities = selectedAmenities
                    )
                }
                if (!edit) {
                    if (decoratorbookresults != null) {
                        decoratorviewmodel.bookdecorator(decoratorbookresults, "Bearer $token")
                    }
                }
                else {
                    if (decoratorbookresults != null) {
                        decoratorviewmodel.editdecorator(decoratorbook = decoratorbookresults, token = "Bearer $token", id = bookingId)
                    }
                }
                clicked = false
                requestreceived = true
            }
        }

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

                if (decorator == null) {
                    Box (
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                else {
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
                        val painter = if (decorator.coverImages.isNotEmpty()) {
                            rememberAsyncImagePainter(model = decorator.coverImages[0])
                        } else {
                            rememberAsyncImagePainter(model = "https://shorturl.at/6DXeG")
                        }
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
                                decorator.name,
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
                        FlowRow(
                            mainAxisSpacing = 10.dp,
                            crossAxisSpacing = 10.dp,
                        ) {
                            decorator.amenities.forEach { amenity ->
                                Cardammedity(amenity.amenity)
                            }
                        }
                        LazyRow(
                            state = listState,
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                        ) {
                            items(decorator.otherImages.size) { index ->
                                MediaRep(
                                    image = decorator.otherImages[index],
                                    onclick = {}
                                )
                                if (index < decorator.otherImages.size - 1) {
                                    AddWidth(dimens.scrollspacer)
                                }
                            }
                        }
                        AddHeight(dimens.small2)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = secondarycolor
                        )
                        AddHeight(dimens.small2)
                        BusinessCard(
                            managername = decorator.managerName,
                            contact = decorator.managerNumber
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

                        if (!requestreceived) {
                            decorator?.amenities?.forEachIndexed { index, amenity ->
                                Row (
                                    modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Funca(
                                        text = amenity.amenity,
                                        modifier = Modifier.fillMaxWidth(fraction = 0.5f).height(50.dp)
                                    )
                                    CounterButton(
                                        value = valueCounter[index].second.toString(),
                                        onValueIncreaseClick = {
                                            valueCounter[index] = valueCounter[index].copy(second = valueCounter[index].second + 1)
                                            val amenityx = valueCounter[index].first

                                            val existingIndex = selectedAmenities.indexOfFirst {
                                                it.decorationAmenityId == (amenityx?.id ?: 0)
                                            }
                                            if (existingIndex >= 0) {
                                                selectedAmenities[existingIndex] = selectedAmenities[existingIndex].copy(
                                                    count = valueCounter[index].second
                                                )
                                            } else {
                                                selectedAmenities.add(
                                                    com.example.mezbaan.model.dataclasses.Amenity(
                                                        count = valueCounter[index].second,
                                                        decorationAmenityId = amenity.id
                                                    )
                                                )
                                            }
                                        },
                                        onValueDecreaseClick = {
                                            val newCount = maxOf(valueCounter[index].second - 1, 0)
                                            valueCounter[index] = valueCounter[index].copy(second = newCount)
                                            val updatedCount = valueCounter[index].second
                                            val amenityx = valueCounter[index].first
                                            val existingIndex = selectedAmenities.indexOfFirst {
                                                it.decorationAmenityId == (amenityx?.id ?: 0)
                                            }
                                            if (existingIndex >= 0) {
                                                if (updatedCount > 0) {
                                                    selectedAmenities[existingIndex] = selectedAmenities[existingIndex].copy(count = updatedCount)
                                                } else {
                                                    selectedAmenities.removeAt(existingIndex)
                                                }
                                            }
                                        },
                                        onValueClearClick = {
                                            valueCounter[index] = valueCounter[index].copy(second = 0)
                                            val amenityx = valueCounter[index].first
                                            if (amenityx != null) {
                                                selectedAmenities.removeAll { it.decorationAmenityId == amenityx.id }
                                            }
                                        }
                                    )
                                    AddWidth(20.dp)
                                }
                                AddHeight(20.dp)
                            }

                            if (decorator != null) {
                                Funca(
                                    text = "Bill: ${(
                                            decorator.amenities
                                                .mapIndexed { index, amenity ->
                                                    amenity.cost * valueCounter[index].second
                                                }
                                                .sum()
                                            )
                                    }",
                                    icon = Icons.Default.Money
                                )
                            }
                            AddHeight(20.dp)
                            TextField(
                                label = { Text("Address") },
                                value = address,
                                onValueChange = setaddress,
                                modifier = Modifier
                                    .fillMaxWidth(fraction = 0.85f)
                                    .height(50.dp),
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Home,
                                        contentDescription = null,
                                        tint = backgroundcolor
                                    )
                                },
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
                                onClick = { clicked = true },
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
            if(requestreceived && !edit) {
                when (decoratorbookresult.value) {
                    is NetworkResponse.Failure -> isLoading = false
                    NetworkResponse.Loading -> isLoading = true
                    is NetworkResponse.Success -> {
                        isLoading = false
                        isSheetopen = false
                        requestreceived = false
                    }
                    null -> { }
                }
            }
            if(requestreceived && edit) {
                when (editdecoratorbookresult.value) {
                    is NetworkResponse.Failure -> isLoading = false
                    NetworkResponse.Loading -> isLoading = true
                    is NetworkResponse.Success -> {
                        isLoading = false
                        isSheetopen = false
                        requestreceived = false
                    }
                    null -> { }
                }
            }

            if(launchdialogbox) {
                AlertDialog(
                    onDismissRequest = { decoratorviewmodel.closeDialog() },
                    confirmButton = {
                        Button(
                            onClick = { navController.navigate(route = Screens.Home.route) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = backgroundcolor,
                                contentColor = secondarycolor
                            )
                        ) {
                            Text("Close")
                        }
                    },
                    title = { Text("Decorator Booking") },
                    text = { Text( if(!edit) "your request is sent to the vendor" else "your edit request is sent to the vendor") },
                    containerColor = backgroundcolor,
                    textContentColor = secondarycolor,
                    titleContentColor = secondarycolor
                )
            }
        }
    }
}