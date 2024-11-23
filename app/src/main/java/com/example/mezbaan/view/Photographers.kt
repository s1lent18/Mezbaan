package com.example.mezbaan.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUpAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.mezbaan.model.dataclasses.PhotographerBook
import com.example.mezbaan.model.response.NetworkResponse
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.alterblack
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.PhotographerViewModel
import com.example.mezbaan.viewmodel.UserViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun MediaRep(
    image: Any,
    isSelected : Boolean = false,
    onclick: () -> Unit
) {
    FloatingActionButton(
        onClick = onclick,
        modifier = Modifier
            .height(dimens.scroll)
            .width(dimens.scrollwidth),
        containerColor = if (isSelected) backgroundcolor else if(isSystemInDarkTheme()) alterblack else Color.White,
        contentColor = if (isSelected) Color.White else if(isSystemInDarkTheme()) Color.White else Color.Black
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Vendors(
    userviewmodel : UserViewModel,
    photographerviewmodel : PhotographerViewModel = viewModel()
) {
    Surface {
        val stepSize = 1f
        val minValue = 1f
        val itemCount = 5
        val maxValue = 12f
        val today = LocalDate.now()
        val context = LocalContext.current
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        val email = userviewmodel.email.collectAsState()
        val phone = userviewmodel.phone.collectAsState()
        val dateDialogState = rememberMaterialDialogState()
        val timeDialogState = rememberMaterialDialogState()
        val username = userviewmodel.username.collectAsState()
        var clicked by remember { mutableStateOf(false) }
        val selected = remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }
        var selectedoption by remember { mutableStateOf("") }
        val dateList = List(30) { today.plusDays(it.toLong()) }
        var launchdialog by remember { mutableStateOf(false) }
        var pickedtime by remember { mutableStateOf(LocalTime.NOON) }
        var pickeddate by remember { mutableStateOf(LocalDate.now()) }
        var launchdialogbox by remember { mutableStateOf(false) }
        var requestreceived by remember { mutableStateOf(false) }
        val buttext = if (selected.value) "Liked" else "Like"
        var launchhourpicker by remember { mutableStateOf(false) }
        val (address, setaddress) = remember { mutableStateOf("") }
        var isScrollingForward by remember { mutableStateOf(true) }
        var information by rememberSaveable { mutableStateOf(false) }
        var isSheetopen by rememberSaveable { mutableStateOf(false) }
        val butcolor = if (selected.value) secondarycolor else Color.White
        val photographerbookresult = photographerviewmodel.photographerbookingresult.observeAsState()
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        var sliderpos by remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
        var currentIndex by remember { androidx.compose.runtime.mutableIntStateOf(0) }
        val currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val formattedtime by remember { derivedStateOf { DateTimeFormatter.ofPattern("hh:mm a").format(pickedtime) } }
        val formatteddate by remember { derivedStateOf { DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickeddate) } }

        LaunchedEffect(Unit) {
            while (true) {
                delay(2000)
                coroutineScope.launch {
                    currentIndex = if (isScrollingForward) {
                        currentIndex + 1
                    } else {
                        currentIndex - 1
                    }

                    listState.animateScrollToItem(currentIndex)

                    if (currentIndex == itemCount - 2) {
                        isScrollingForward = false
                    } else if (currentIndex == 0) {
                        isScrollingForward = true
                    }
                }
            }
        }

        LaunchedEffect (clicked) {
            if(clicked) {
                val photographerbookresults = PhotographerBook(
                    time = formattedtime,
                    edate = formatteddate,
                    bdate = currentDateTime,
                    username = username.value,
                    email = email.value,
                    phone = phone.value,
                    address = address,
                    bill = (25000 * sliderpos.toInt()),
                    eventtype = selectedoption,
                    hours = sliderpos.toInt(),
                )
                photographerviewmodel.bookphotographer(photographerbookresults)
                clicked = false
                requestreceived = true
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),

        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (heading, pp, name, media) = createRefs()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.35f)
                        .background(backgroundcolor)
                        .constrainAs(heading) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = "https://drive.google.com/uc?export=view&id=1m4vKsUBL3Cnwkb7QS1Yjx8j-N9ktu17j"),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-70).dp)
                        .constrainAs(pp) {
                            top.linkTo(heading.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.9f)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = "https://drive.google.com/uc?export=view&id=1Gae9YMksmUfU74cgXX0x1ivwOdLb4H4L"),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(100.dp)
                                .border(4.dp, Color.White, CircleShape)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        modifier = Modifier
                            .height(50.dp)
                            .offset(y = (20).dp),
                        onClick = {
                            selected.value = !selected.value
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundcolor,
                            contentColor = secondarycolor
                        )
                    ) {
                        Icon(
                            Icons.Default.ThumbUpAlt,
                            contentDescription = null,
                            tint = butcolor
                        )
                        AddWidth(5.dp)
                        Text(buttext)
                    }
                }

                Column (
                    modifier = Modifier
                        .constrainAs(name) {
                        top.linkTo(pp.bottom, margin = (-40).dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.percent(0.9f)
                    },

                ) {
                    Text(
                        text = "Photographer Name",
                        fontFamily = Bebas,
                        fontSize = dimens.heading
                    )
                    AddHeight(20.dp)
                    Text(
                        text = "I am a cool photographer and I take very good pictures",
                        fontFamily = Bebas,
                        fontSize = dimens.fontsize
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .constrainAs(media) {
                            top.linkTo(name.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.9f)
                            bottom.linkTo(parent.bottom, margin = 20.dp)
                            height = Dimension.fillToConstraints
                        },
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = secondarycolor)
                            Icon(Icons.Default.Star, contentDescription = null, tint = secondarycolor)
                            Icon(Icons.Default.Star, contentDescription = null, tint = secondarycolor)
                            Icon(Icons.Default.Star, contentDescription = null, tint = secondarycolor)
                            Icon(Icons.Default.Star, contentDescription = null, tint = secondarycolor)
                            AddWidth(80.dp)
                            Card (
                                colors = CardDefaults.cardColors(
                                    containerColor = backgroundcolor,
                                    contentColor = secondarycolor
                                ),
                                elevation = CardDefaults.cardElevation(10.dp),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text("26 Reviews", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                            }
                        }
                        AddHeight(30.dp)
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.MonetizationOn,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    AddWidth(10.dp)
                                    Text("25000Rs", fontSize = dimens.buttontext)
                                }
                                AddHeight(5.dp)
                                Text("per hour", fontSize = dimens.buttontext)
                            }
                            VerticalDivider(color = secondarycolor, modifier = Modifier.fillMaxHeight(fraction = 0.6f))
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.servicevendor),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    AddWidth(10.dp)
                                    Text("250", fontSize = dimens.buttontext)
                                }
                                AddHeight(5.dp)
                                Text("Events done", fontSize = dimens.buttontext)
                            }
                        }
                        AddHeight(30.dp)
                        Row (
                            modifier = Modifier.fillMaxWidth(fraction = 0.6f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(backgroundcolor)
                            ) {
                                IconButton(onClick = {
                                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("mailto:")
                                        putExtra(Intent.EXTRA_EMAIL, arrayOf("mezbaan30@gmail.com"))
                                        putExtra(Intent.EXTRA_SUBJECT, "Subject Text")
                                    }

                                    if (emailIntent.resolveActivity(context.packageManager) != null) {
                                        context.startActivity(emailIntent)
                                    }

                                    val gmailPackage = "com.google.android.gm"
                                    if (context.packageManager.getLaunchIntentForPackage(gmailPackage) != null) {
                                        emailIntent.setPackage(gmailPackage)
                                    }

                                    if (emailIntent.resolveActivity(context.packageManager) != null) {
                                        context.startActivity(emailIntent)
                                    } else {
                                        context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(R.drawable.mail),
                                        contentDescription = null
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(backgroundcolor)
                            ) {
                                IconButton(onClick = {
                                    val facebookUri = Uri.parse("https://www.facebook.com/iirfanjunejo")
                                    val facebookIntent = Intent(Intent.ACTION_VIEW, facebookUri).apply {
                                        setPackage("com.facebook.katana")
                                    }

                                    if (facebookIntent.resolveActivity(context.packageManager) != null) {
                                        context.startActivity(facebookIntent)
                                    } else {
                                        context.startActivity(Intent(Intent.ACTION_VIEW, facebookUri))
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(R.drawable.facebook),
                                        contentDescription = null
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(backgroundcolor)
                            ) {
                                IconButton(onClick = {
                                    val instaURL = Uri.parse("https://www.instagram.com/irfanjunejo?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw==")
                                    val intent = Intent(Intent.ACTION_VIEW, instaURL).apply {
                                        setPackage("com.instagram.android")
                                    }
                                    if (intent.resolveActivity(context.packageManager) != null) {
                                        context.startActivity(intent)
                                    } else {
                                        val webIntent = Intent(Intent.ACTION_VIEW, instaURL)
                                        context.startActivity(webIntent)
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(R.drawable.instagram),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        AddHeight(30.dp)
                        Text("Samples", fontFamily = Bebas, fontSize = dimens.labeltext)

                        LazyRow(
                            state = listState,
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                        ) {
                            items(5) { index ->
                                MediaRep(
                                    image = "https://cdn.expertphotography.com/wp-content/uploads/2021/08/Become-Professional-Photographer-Colin-Lloyd.jpg",
                                    onclick = {}
                                )
                                if (index < itemCount - 1) {
                                    AddWidth(dimens.scrollspacer)
                                }
                            }
                        }
                        AddHeight(30.dp)
                        Column(
                            modifier = Modifier
                                .height(50.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxSize(),
                                onClick = { isSheetopen = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = secondarycolor,
                                    contentColor = backgroundcolor
                                ),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text("Book")
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
                        if (!requestreceived) {
                            Column (
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
                                        text = "Event Type: $selectedoption",
                                        icon = Icons.Default.Event,
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
                                            onClick = { launchdialog = true }
                                        ) {
                                            Icon(
                                                Icons.Default.Event,
                                                contentDescription = null,
                                                tint = backgroundcolor
                                            )
                                        }
                                    }
                                }
                                AddHeight(20.dp)
                                Funca(
                                    text = "Bill: ${25000 * sliderpos.toInt()}",
                                    icon = Icons.Default.Money,
                                )
                                AddHeight(20.dp)
                                TextField(
                                    label = { Text("Address") },
                                    value = address,
                                    onValueChange = setaddress,
                                    modifier = Modifier.fillMaxWidth(fraction = 0.85f).height(50.dp),
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
                                Button(
                                    onClick = {
                                        clicked = true
                                        information = true
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
                                    Text("Confirm Booking")
                                }
                                AddHeight(20.dp)
                            }
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
                                date in dateList
                            }
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

                    if (launchdialog) {
                        AlertDialog(
                            onDismissRequest = { launchdialog = false },
                            title = { Text("Choose an Option", color = secondarycolor) },
                            text = {
                                Column {
                                    val options = listOf("Wedding", "Birthday", "Corporate Event", "Graduation")
                                    options.forEach { option ->
                                        Text(
                                            text = option,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                                .clickable {
                                                    selectedoption = option
                                                    launchdialog = false
                                                },
                                            color = secondarycolor
                                        )
                                    }

                                    TextField(
                                        label = { Text("Other", fontSize = 14.sp) },
                                        value = selectedoption,
                                        onValueChange = { newValue ->
                                            selectedoption = newValue
                                        },
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            disabledLabelColor = secondarycolor,
                                            unfocusedLabelColor = secondarycolor,
                                            focusedLabelColor = secondarycolor,
                                            focusedTextColor = secondarycolor,
                                            unfocusedTextColor = secondarycolor,
                                            disabledTextColor = secondarycolor
                                        )
                                    )
                                }
                            },
                            confirmButton = {
                                TextButton(onClick = { launchdialog = false }) {
                                    Text("Close", color = secondarycolor)
                                }
                            },
                            containerColor = backgroundcolor
                        )
                    }

                    if (launchhourpicker) {
                        AlertDialog(
                            onDismissRequest = { launchdialog = false },
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

                    if(requestreceived) {
                        when (val request = photographerbookresult.value) {
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
}