@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.mezbaan.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mezbaan.R
import com.example.mezbaan.model.dataclasses.CateringBook
import com.example.mezbaan.model.dataprovider.CaterersOption
import com.example.mezbaan.model.models.BookedMenuItems
import com.example.mezbaan.model.models.BookedPackage
import com.example.mezbaan.model.models.DataXX
import com.example.mezbaan.model.models.MenuItem
import com.example.mezbaan.model.models.Package
import com.example.mezbaan.model.response.NetworkResponse
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.CateringViewModel
import com.example.mezbaan.viewmodel.UserViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.google.accompanist.flowlayout.FlowRow
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CateringDialogFilter(
    onDismissRequest: () -> Unit,
    priceRange: MutableState<Float>,
    minprice: Float,
    maxprice: Float,
    onPriceChange: (Float) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column (
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth(fraction = 0.8f)
                .height(250.dp)
                .background(backgroundcolor)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column (
                modifier = Modifier.fillMaxSize(fraction = 0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Price Filter", color = secondarycolor)

                AddHeight(20.dp)

                Slider(
                    value = priceRange.value,
                    onValueChange = { newValue ->
                        val adjustedValue = ((newValue - minprice) / 5).roundToInt() * 5 + minprice
                        priceRange.value = adjustedValue.coerceIn(minprice, maxprice)
                        onPriceChange(priceRange.value)
                    },
                    valueRange = minprice..maxprice,
                    steps = ((maxprice - minprice) / 5).toInt() - 1,
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

                AddHeight(20.dp)

                Funca(
                    text = "Max Price ${priceRange.value.roundToInt()}",
                    icon = Icons.Default.Money,
                )
            }
        }
    }
}

@Composable
fun CartPackageRow(item: Package, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AddWidth(15.dp)

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, fontFamily = Bebas, color = secondarycolor)
            Text("${item.price}rs per head", fontFamily = Bebas, color = secondarycolor)
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
fun CartItemRow(item: MenuItem, onClick: () -> Unit) {
    val image = when (item.type) {
        "Appetizer" -> R.drawable.food
        "Main Course" -> Icons.Default.Restaurant
        "Dessert" -> Icons.Default.Cake
        else -> Icons.Default.LocalBar
    }
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (image is Painter) {
                    Icon(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(fraction = 0.45f)
                    )
                } else if (image is ImageVector) {
                    Icon(
                        imageVector = image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(fraction = 0.45f)
                    )
                }
            }
        }

        AddWidth(15.dp)

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, fontFamily = Bebas, color = secondarycolor)
            Text("${item.cost}rs per head", fontFamily = Bebas, color = secondarycolor)
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
fun PackageCard(
    title: String,
    items: List<MenuItem?>,
    price: Int,
    addtopackagecart: () -> Unit,
) {
    var opendialog by remember { mutableStateOf(false) }

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { opendialog = true },
        colors = CardDefaults.cardColors(
            containerColor = backgroundcolor,
            contentColor = secondarycolor
        )
    ) {
        Column (
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(title, fontFamily = Bebas, fontSize = 22.sp)
            AddHeight(5.dp)
            FlowRow(
                mainAxisSpacing = 10.dp,
                crossAxisSpacing = 10.dp,
            ) {
                items.forEach { amenity ->
                    if (amenity != null) {
                        Text(amenity.name, fontSize = 12.sp)
                    }
                }
            }
            AddHeight(5.dp)
            Text(price.toString(), fontFamily = Bebas, fontSize = 20.sp)
        }
    }

    if(opendialog) {
        DialogPackage(
            title = title,
            onDismissRequest = {
                opendialog = false
            },
            onAddtoCart = addtopackagecart
        )
    }
}

@Composable
fun DialogPackage(
    title: String,
    onDismissRequest: () -> Unit,
    onAddtoCart: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Text("Add $title to Cart?")
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
        },
        containerColor = backgroundcolor,
        textContentColor = secondarycolor,
        titleContentColor = secondarycolor
    )
}

@Composable
fun Display(
    title: String,
    addtocart: () -> Unit,
    rate: Int,
    type: String
) {
    val imageUrl = when (type) {
        "Appetizer" -> R.drawable.food
        "Main Course" -> Icons.Default.Restaurant
        "Dessert" -> Icons.Default.Cake
        else -> Icons.Default.LocalBar
    }
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl is Painter) {
                    Icon(
                        painter = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(fraction = 0.45f)
                    )
                } else if (imageUrl is ImageVector) {
                    Icon(
                        imageVector = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(fraction = 0.45f)
                    )
                }
            }
        }
        AddHeight(10.dp)
        Text(title, fontFamily = Bebas, fontSize = 25.sp)

        if(opendialog) {
            DialogFood(
                imageUrl = imageUrl,
                title = title,
                rate = rate,
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
    imageUrl: Any,
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (imageUrl is Painter) {
                            Icon(
                                painter = imageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(fraction = 0.45f)
                            )
                        } else if (imageUrl is ImageVector) {
                            Icon(
                                imageVector = imageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(fraction = 0.45f)
                            )
                        }
                    }
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
fun Caterers(
    userviewmodel: UserViewModel,
    cateringviewmodel : CateringViewModel = hiltViewModel(),
    menu: DataXX,
    navController: NavController,
    startTime: String,
    date: String,
    bill: String,
    edit: Boolean,
    bookingId: Int,
    addresss: String,
    bookedPackage: List<BookedPackage>,
    bookedMenuItems: List<BookedMenuItems>
) {
    Surface {
        val guestCount = (bill.toFloat() / ((bookedPackage.sumOf { it.price.toInt() }) + (bookedMenuItems.sumOf { it.cost.toInt() })))
        val stepSize = 50f
        val minValue = 50f
        val maxValue = 3000f
        val context = LocalContext.current
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        val token by userviewmodel.token.collectAsState()
        val dateDialogState = rememberMaterialDialogState()
        val timeDialogState = rememberMaterialDialogState()
        var clicked by remember { mutableStateOf(false) }
        var expanded by remember { mutableStateOf(false) }
        var selected by remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }
        val drinks = menu.menuItems.filter { it.type == "Drink" }
        var isLoading by remember { mutableStateOf(false) }
        var showDialog by remember { mutableStateOf(false) }
        val desserts = menu.menuItems.filter { it.type == "Dessert" }
        var sliderpos by remember { mutableFloatStateOf(guestCount) }
        val checkdialogbox by cateringviewmodel.isDialogVisible.collectAsState()
        var requestreceived by remember { mutableStateOf(false) }
        var cartItems by remember { mutableStateOf(listOf<MenuItem>()) }
        var cartPackageItems by remember { mutableStateOf(listOf<Package>())}
        val (address, setaddress) = remember { mutableStateOf(addresss) }
        var isScrollingForward by remember { mutableStateOf(true) }
        val appetizers = menu.menuItems.filter { it.type == "Appetizer" }
        var information by rememberSaveable { mutableStateOf(false) }
        var isSheetopen by rememberSaveable { mutableStateOf(edit) }
        val maincourse = menu.menuItems.filter { it.type == "Main Course" }
        val selectedOption = remember { mutableStateOf("Appetizers") }
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        var currentIndex by remember { androidx.compose.runtime.mutableIntStateOf(0) }
        val cateringbookresult = cateringviewmodel.cateringbookresult.observeAsState()
        val editcateringbookresult = cateringviewmodel.editbookingresult.observeAsState()

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

        val formatteddate by remember {
            derivedStateOf { DateTimeFormatter.ofPattern("dd MMM yyyy").format(pickeddate) }
        }

        val formattedtime by remember {
            derivedStateOf { DateTimeFormatter.ofPattern("hh:mm a").format(pickedtime) }
        }

        val endtime by remember {
            derivedStateOf { pickedtime.plusHours(sliderpos.toLong()) }
        }

        val minprice by remember(selectedOption.value) {
            derivedStateOf {
                if (selected) {
                    menu.menuItems.minByOrNull { it.cost!! }?.cost?.toFloat() ?: 0f
                }
                else {
                    menu.packages.minByOrNull { it.price }?.price?.toFloat() ?: 0f
                }
            }
        }

        val maxprice by remember(selectedOption.value) {
            derivedStateOf {
                if (selected) {
                    menu.menuItems.maxByOrNull { it.cost!! }?.cost?.toFloat() ?: 0f
                }
                else {
                    menu.packages.maxByOrNull { it.price }?.price?.toFloat() ?: 0f
                }
            }
        }

        val priceRange = remember {
            mutableStateOf(minprice)
        }
        val selectedPrice = remember { mutableStateOf(maxprice) }

        LaunchedEffect(minprice, maxprice) {
            priceRange.value = priceRange.value.coerceIn(minprice, maxprice)
        }

        val filteredItems = if (searchQuery.isNotEmpty()) {
            when (selectedOption.value) {
                "Appetizer" -> appetizers.filter { it.name.contains(searchQuery, ignoreCase = true) }
                "Main\nCourse" -> maincourse.filter { it.name.contains(searchQuery, ignoreCase = true) }
                "Dessert" -> desserts.filter { it.name.contains(searchQuery, ignoreCase = true) }
                "Drink" -> drinks.filter { it.name.contains(searchQuery, ignoreCase = true) }
                else -> emptyList()
            }
        } else {
            when (selectedOption.value) {
                "Appetizer" -> appetizers
                "Main\nCourse" -> maincourse
                "Dessert" -> desserts
                "Drink" -> drinks
                else -> emptyList()
            }
        }

        if (showDialog) {
            CateringDialogFilter(
                onDismissRequest = { showDialog = false },
                priceRange = priceRange,
                maxprice = maxprice + 100000f,
                minprice = minprice,
                onPriceChange = { newValue ->
                    selectedPrice.value = newValue
                }
            )
        }

        val itemid = remember { mutableStateListOf<Int>() }
        val packageid = remember { mutableStateListOf<Int>() }

        LaunchedEffect (clicked) {
            if(clicked) {
                val cateringbookresults = CateringBook(
                    address = address,
                    bill = ((sliderpos.roundToInt()) * cartItems.sumOf { it.cost ?: 0 } + (sliderpos.roundToInt() * cartPackageItems.sumOf { it.price })).toDouble(),
                    cateringServiceId = menu.id,
                    date = formatteddate,
                    guestCount = sliderpos.roundToInt(),
                    startTime = formattedtime,
                    menuItemIds = itemid,
                    endTime = null,
                    packageIds = packageid
                )
                if (!edit) {
                    cateringviewmodel.bookcatering(cateringbookresults, "Bearer $token")
                }
                else {
                    cateringviewmodel.editdecorator(cateringbook = cateringbookresults, token = "Bearer $token", id = bookingId)
                }
                clicked = false
                requestreceived = true
            }
        }

        LaunchedEffect(Unit) {
            cartItems = bookedMenuItems.map { menuItem ->
                MenuItem(
                    id = menuItem.id,
                    name = menuItem.name,
                    cost = menuItem.cost.toIntOrNull(),
                    type = menuItem.type
                )
            }

            itemid.addAll(bookedMenuItems.map { it.id })

            cartPackageItems = bookedPackage.map { packageItem ->
                Package(
                    id = packageItem.id,
                    name = packageItem.name,
                    price = packageItem.price.toInt(),
                    menuItems = null
                )
            }

            packageid.addAll(bookedPackage.map { it.id })
        }

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

                    if (currentIndex == menu.packages.size - 2) {
                        isScrollingForward = false
                    } else if (currentIndex == 0) {
                        isScrollingForward = true
                    }
                }
            }
        }

        fun addToCart(item: MenuItem) {
            cartItems = cartItems.toMutableList().apply {
                add(item)
            }
            itemid.add(item.id)
        }

        fun addPackageToCart(pack: Package) {
            cartPackageItems = cartPackageItems.toMutableList().apply {
                add(pack)
            }
            packageid.add(pack.id)
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
                val (searchrow, catheading, boxes, expandable, fooditems, checkoutbutton) = createRefs()

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
                            Text(menu.name, fontWeight = FontWeight.Bold, fontFamily = Bebas, fontSize = dimens.fontsize)
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
                    if (selected) {
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
                }

                Row(
                    modifier = Modifier
                        .constrainAs(expandable) {
                            top.linkTo(boxes.bottom, margin = 10.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.9f)
                        }
                        .height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(secondarycolor)
                        ) {
                            IconButton(
                                onClick = { expanded = !expanded }
                            ) {
                                Icon(
                                    imageVector = if (selected) Icons.AutoMirrored.Filled.List else Icons.Default.CreditCard,
                                    contentDescription = null,
                                    tint = backgroundcolor
                                )
                            }
                        }

                        if (expanded) {
                            Box (
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(backgroundcolor)
                            ) {
                                IconButton(
                                    onClick = {
                                        expanded = !expanded
                                        selected = true
                                    }
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.List,
                                        contentDescription = null,
                                        tint = secondarycolor
                                    )
                                }
                            }

                            Box (
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(backgroundcolor)
                            ) {
                                IconButton(
                                    onClick = {
                                        expanded = !expanded
                                        selected = false
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.CreditCard,
                                        contentDescription = null,
                                        tint = secondarycolor
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(backgroundcolor)
                    ) {
                        IconButton (
                            onClick = {
                                showDialog = true
                            },
                        ) {
                            Icon(
                                Icons.Default.FilterAlt,
                                contentDescription = null,
                                tint = secondarycolor
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier
                    .height(30.dp)
                    .constrainAs(createRef()) {
                        top.linkTo(expandable.bottom)
                        start.linkTo(parent.start)
                    })

                Box(
                    modifier = Modifier.constrainAs(fooditems) {
                        top.linkTo(expandable.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(checkoutbutton.top, margin = 5.dp)
                        height = Dimension.fillToConstraints
                    },
                    contentAlignment = Alignment.Center
                ) {
                    if (selected) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            items(filteredItems.size) { option ->
                                    filteredItems[option].cost?.let { it ->
                                        Display(
                                            title = filteredItems[option].name,
                                            rate = it,
                                            addtocart = {
                                                filteredItems[option].cost?.let {
                                                    MenuItem(
                                                        name = filteredItems[option].name,
                                                        cost = filteredItems[option].cost,
                                                        id = filteredItems[option].id,
                                                        type = filteredItems[option].type
                                                    )
                                                }?.let {
                                                    addToCart(
                                                        it
                                                    )
                                                }
                                            },
                                            type = filteredItems[option].type
                                        )
                                    }
                                }
                        }
                    }
                    else {
                        LazyColumn (
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(menu.packages.size) {
                                menu.packages[it].menuItems?.let { it1 ->
                                    PackageCard(
                                        title = menu.packages[it].name,
                                        items = it1,
                                        price = menu.packages[it].price,
                                        addtopackagecart = {
                                            addPackageToCart(
                                                Package(
                                                    id = menu.packages[it].id,
                                                    name = menu.packages[it].name,
                                                    menuItems = menu.packages[it].menuItems,
                                                    price = menu.packages[it].price
                                                )
                                            )
                                        }
                                    )
                                }
                                AddHeight(10.dp)
                            }
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
                            if (cartItems.isNotEmpty() || cartPackageItems.isNotEmpty()) {
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

                    if (cartItems.isNotEmpty() || cartPackageItems.isNotEmpty()) {
                        Text(
                            text = (cartItems.size + cartPackageItems.size).toString(),
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
                    if (!requestreceived) {
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

                                    items(cartPackageItems.size) { item ->
                                        CartPackageRow(cartPackageItems[item], onClick = {
                                            cartPackageItems = cartPackageItems.toMutableList().apply {
                                                removeAt(item)
                                            }
                                            if (cartPackageItems.isEmpty()) {
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
                                        text = "${(sliderpos.roundToInt()) * cartItems.sumOf { it.cost ?: 0 } + (sliderpos.roundToInt() * cartPackageItems.sumOf { it.price })} ",
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

            if(requestreceived && !edit) {
                when (cateringbookresult.value) {
                    is NetworkResponse.Failure -> isLoading = false
                    NetworkResponse.Loading -> isLoading = true
                    is NetworkResponse.Success -> {
                        isLoading = false
                        isSheetopen = false
                        requestreceived = false
                    }
                    else -> {}
                }
            }
            if(requestreceived && edit) {
                when (editcateringbookresult.value) {
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

            if(checkdialogbox) {
                AlertDialog(
                    onDismissRequest = { cateringviewmodel.closeDialog() },
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
                    title = { Text("Catering Booking") },
                    text = { Text( if(!edit) "your request is sent to the vendor" else "your edit request is sent to the vendor") },
                    containerColor = backgroundcolor,
                    textContentColor = secondarycolor,
                    titleContentColor = secondarycolor
                )
            }
        }
    }
}