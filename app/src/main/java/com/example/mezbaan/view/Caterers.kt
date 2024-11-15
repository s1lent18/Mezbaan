package com.example.mezbaan.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.mezbaan.R
import com.example.mezbaan.model.dataclasses.CartItems
import com.example.mezbaan.model.dataprovider.AppetizersOption
import com.example.mezbaan.model.dataprovider.CaterersOption
import com.example.mezbaan.model.dataprovider.DessertsOption
import com.example.mezbaan.model.dataprovider.DrinksOptions
import com.example.mezbaan.model.dataprovider.MainCourseOptions
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
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

@Composable
fun CartItemRow(item: CartItems, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(60.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        AddWidth(15.dp)

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, fontFamily = Bebas, color = secondarycolor)
            Text("${item.rate}rs per head", fontFamily = Bebas, color = secondarycolor)
        }

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(secondarycolor)
        ) {
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    tint = backgroundcolor
                )
            }
        }
    }

}

@Composable
fun Display(imageUrl: String, title: String, addtocart: () -> Unit) {

    var opendialog by remember { mutableStateOf(false) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(150.dp)
                .clickable {
                    opendialog = true
                },
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(10.dp),
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.mezbaan),
            )
        }
        AddHeight(10.dp)
        Text(title, fontFamily = Bebas, fontSize = 25.sp)

        if(opendialog) {
            DialogFood(
                imageUrl = imageUrl,
                title = title,
                rate = 25,
                onDismissRequest = {
                    opendialog = false
               },
                onAddtoCart = addtocart
            )
        }
    }
}

@Composable
fun DialogFood(
    imageUrl: String,
    title: String,
    rate: Int,
    onDismissRequest: () -> Unit,
    onAddtoCart: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    fontSize = dimens.heading,
                    fontFamily = Bebas
                )

                AddHeight(20.dp)

                Card(
                    modifier = Modifier.size(150.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.mezbaan)
                    )
                }

                AddHeight(10.dp)

                Text(
                    text = "$rate per head",
                    fontSize = dimens.fontsize,
                    fontFamily = Bebas
                )
            }
        },
        confirmButton = {
            IconButton(onClick = {
                onAddtoCart()
                onDismissRequest()
            }) {
                Icon(
                    imageVector = Icons.Default.AddShoppingCart,
                    contentDescription = "Add to Cart",
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Close")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Caterers() {
    Surface {
        val context = LocalContext.current
        val dateDialogState = rememberMaterialDialogState()
        val timeDialogState = rememberMaterialDialogState()
        var pickeddate by remember { mutableStateOf(LocalDate.now()) }
        var pickedtime by remember { mutableStateOf(LocalTime.NOON) }
        val formatteddate by remember { derivedStateOf { DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickeddate) } }
        val formattedtime by remember { derivedStateOf { DateTimeFormatter.ofPattern("hh:mm").format(pickedtime) } }
        val stepSize = 50f
        val minValue = 50f
        val maxValue = 3000f
        var sliderpos by remember { mutableFloatStateOf(50.0f) }
        var information by rememberSaveable { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        var isSheetopen by rememberSaveable { mutableStateOf(false) }
        var cartItems by remember { mutableStateOf(listOf<CartItems>()) }
        var searchQuery by remember { mutableStateOf("") }
        val selectedOption = remember { mutableStateOf("Appetizers") }
        val filteredItems = if (searchQuery.isNotEmpty()) {
            when (selectedOption.value) {
                "Appetizers" -> AppetizersOption.filter { it.second.contains(searchQuery, ignoreCase = true) }
                "Main\nCourse" -> MainCourseOptions.filter { it.second.contains(searchQuery, ignoreCase = true) }
                "Desserts" -> DessertsOption.filter { it.second.contains(searchQuery, ignoreCase = true) }
                "Drinks" -> DrinksOptions.filter { it.second.contains(searchQuery, ignoreCase = true) }
                else -> emptyList()
            }
        } else {
            when (selectedOption.value) {
                "Appetizers" -> AppetizersOption
                "Main\nCourse" -> MainCourseOptions
                "Desserts" -> DessertsOption
                "Drinks" -> DrinksOptions
                else -> emptyList()
            }
        }

        fun addToCart(item: CartItems) {
            cartItems = cartItems.toMutableList().apply {
                add(item)
            }
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, bottom = 20.dp)
            ) {
                val (searchrow, catheading, boxes, fooditems, checkoutbutton) = createRefs()

                Row(
                    modifier = Modifier.constrainAs(catheading) {
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
                            Text("Caterers Name", fontWeight = FontWeight.Bold, fontFamily = Bebas, fontSize = dimens.fontsize)
                        }
                    }
                }

                Box(
                    modifier = Modifier.constrainAs(searchrow) {
                        top.linkTo(catheading.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.percent(0.9f)
                    }
                ) {
                    Column {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text(text = "Search...") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            )
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier.constrainAs(boxes) {
                        top.linkTo(searchrow.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                ) {
                    items(CaterersOption.size) { index ->
                        val (image, text) = CaterersOption[index]

                        Itemstobook(
                            image = if (image is Int) painterResource(image) else image as ImageVector,
                            text = text,
                            isSelected = selectedOption.value == text,
                            onclick = { selectedOption.value = text }
                        )

                        AddWidth(dimens.scrollspacer)
                    }
                }

                Box(
                    modifier = Modifier.constrainAs(fooditems) {
                        top.linkTo(boxes.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(checkoutbutton.top, margin = 5.dp)
                        height = Dimension.percent(0.45f)
                    }
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(filteredItems.size) { option ->
                            Display(
                                imageUrl = filteredItems[option].first,
                                title = filteredItems[option].second,
                                addtocart = { addToCart(
                                    CartItems(imageUrl = filteredItems[option].first,
                                        title = filteredItems[option].second,
                                        rate = 100
                                    )
                                ) }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .size(85.dp)
                        .clip(CircleShape)
                        .constrainAs(checkoutbutton) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, margin = 16.dp)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (cartItems.isNotEmpty()) {
                                isSheetopen = true
                            } else {
                                Toast.makeText(context, "No Items Selected", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .size(65.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondarycolor,
                            contentColor = backgroundcolor
                        )
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    if (cartItems.isNotEmpty()) {
                        Text(
                            text = cartItems.size.toString(),
                            color = backgroundcolor,
                            fontSize = dimens.buttontext,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-28).dp, y = (18).dp)
                                .background(Color.Transparent)
                        )
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
                    if (!information) {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {

                            AddHeight(20.dp)

                            LazyColumn (
                                modifier = Modifier.fillMaxWidth(fraction = 0.9f)
                            ) {
                                items(cartItems.size) { item ->
                                    CartItemRow(cartItems[item], onClick = {
                                        cartItems = cartItems.toMutableList().apply {
                                            removeAt(item)
                                        }
                                        if (cartItems.isEmpty()) {
                                            isSheetopen = false
                                        }
                                    })
                                }
                            }

                            AddHeight(30.dp)

                            Button(
                                onClick = { information = true },
                                modifier = Modifier
                                    .fillMaxWidth(fraction = 0.85f)
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = secondarycolor,
                                    contentColor = backgroundcolor
                                ),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text("Proceed for event information")
                            }
                            AddHeight(20.dp)
                        }
                    } else {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Funca(
                                    text = "${(sliderpos.roundToInt()) * cartItems.sumOf { it.rate }} ",
                                    icon = Icons.Default.Money,
                                    modifier = Modifier
                                        .fillMaxWidth(fraction = 0.8f)
                                        .height(50.dp)
                                )
                                Box (
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(secondarycolor)
                                ){
                                    IconButton(
                                        onClick = { information = false }
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
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
                            Row (
                                modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Funca(
                                    text = formattedtime,
                                    icon = Icons.Default.AccessTime,
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
                                        onClick = { timeDialogState.show() }
                                    ) {
                                        Icon(
                                            Icons.Default.AccessTime,
                                            contentDescription = null,
                                            tint = backgroundcolor
                                        )
                                    }
                                }
                            }
                            AddHeight(20.dp)
                            Funca(text ="Guest count ${sliderpos.roundToInt()}", icon = Icons.Default.Person)
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

                            AddHeight(10.dp)

                            Button(
                                onClick = { information = true },
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
            }
        }
    }
}