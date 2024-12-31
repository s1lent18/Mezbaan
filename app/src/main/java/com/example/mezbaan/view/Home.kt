package com.example.mezbaan.view

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.model.dataclasses.Ratings
import com.example.mezbaan.model.dataprovider.BookingOptions
import com.example.mezbaan.model.dataprovider.FilteredItem
import com.example.mezbaan.model.dataprovider.NavigationBarItems
import com.example.mezbaan.model.models.Data
import com.example.mezbaan.model.response.NetworkResponse
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.alterblack
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.CateringViewModel
import com.example.mezbaan.viewmodel.DecoratorViewModel
import com.example.mezbaan.viewmodel.OtherServicesViewModel
import com.example.mezbaan.viewmodel.PhotographerViewModel
import com.example.mezbaan.viewmodel.UserViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun Itemstobook(
    image: Any,
    text : String,
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
            modifier = Modifier.padding(5.dp)
        ) {
            if (image is Painter) {
                Icon(
                    painter = image,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else if (isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier.size(dimens.iconsize)
                )
            } else if (image is ImageVector) {
                Icon(
                    imageVector = image,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else if (isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier.size(dimens.iconsize)
                )
            }
            AddHeight(10.dp)
            Text(text = text,
                fontSize = dimens.buttontext,
                maxLines = 2,
                overflow = Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = dimens.scrollwidth - 20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogFilter(
    onDismissRequest: () -> Unit,
    selectedTabIndex: MutableState<Int>,
    tabs: List<String>,
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
                .height(250.dp)
                .background(backgroundcolor)
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex.value,
                edgePadding = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                containerColor = backgroundcolor,
                contentColor = secondarycolor
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        text = {
                            Text(
                                text = title,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    )
                }
            }

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

            Funca(
                text = "Max Price ${priceRange.value.roundToInt()}",
                icon = Icons.Default.Money
            )

        }
    }
}

@Composable
fun Cards(
    text: String,
    onclick: () -> Unit,
    model: String = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYz8zEAFyjZKTNeQW-MRagzdrD-bTFpArsiA&s"
) {
    var textColor by remember { mutableStateOf(Color.White) }
    val painter = rememberAsyncImagePainter(model = model)
    val imageState = painter.state

    if (imageState is AsyncImagePainter.State.Success) {
        val bitmap = (imageState.result.drawable as? BitmapDrawable)?.bitmap
        bitmap?.let {
            Palette.from(it).generate { palette ->
                palette?.let {
                    val dominantColor = palette.dominantSwatch?.rgb ?: Color.White.toArgb()
                    textColor = if (isColorDark(dominantColor)) Color.White else Color.Black
                }
            }
        }
    }

    FloatingActionButton(
        onClick = onclick,
        modifier = Modifier
            .height(dimens.cards)
            .aspectRatio(0.65f),
        containerColor = backgroundcolor
    ) {
        Box (
            contentAlignment = Alignment.Center
        ) {
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text, color = textColor, fontSize = dimens.labeltext, fontFamily = Bebas)
                }
            }
        }
    }
}

fun isColorDark(color: Int): Boolean {
    val darkness = 1 - (
            0.299 * ((color shr 16) and 0xFF) +
                    0.587 * ((color shr 8) and 0xFF) +
                    0.114 * (color and 0xFF)
            ) / 255
    return darkness >= 0.5
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    venues: List<Data>,
    navController: NavController,
    authviewmodel: AuthViewModel = viewModel(),
    userviewmodel : UserViewModel = hiltViewModel(),
    cateringviewmodel : CateringViewModel = hiltViewModel(),
    decoratorviewmodel : DecoratorViewModel = hiltViewModel(),
    photographerviewmodel : PhotographerViewModel = hiltViewModel(),
    otherservicesviewmodel : OtherServicesViewModel = hiltViewModel(),
) {
    val insets = WindowInsets.navigationBars
    val venuesp = venues.sortedByDescending { it.averageRating }
    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val selectedTabIndex by remember { mutableIntStateOf(0) }
    val navigationBarItems = remember { NavigationBarItems.entries }
    val selectedOption = remember { mutableStateOf("Venues") }
    val ratingresult = userviewmodel.ratings.observeAsState()
    val token by userviewmodel.token.collectAsState()
    val catering by cateringviewmodel.menu.collectAsStateWithLifecycle()
    val decorators by decoratorviewmodel.decorators.collectAsStateWithLifecycle()
    val vendors by otherservicesviewmodel.vendors.collectAsStateWithLifecycle()
    val venuesh = venues.sortedWith(compareBy<Data> {it.priceOff}.thenBy { it.averageRating })
    val photographers by photographerviewmodel.photographers.collectAsStateWithLifecycle()
    val bottomInsetDp = with(LocalDensity.current) { insets.getBottom(LocalDensity.current).toDp() }

    var answer by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isSheetopen by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedRating by remember { mutableIntStateOf(0) }

    val tabs = listOf("Price Filter", "Tab 2", "Tab 3")

    val filteredItems: List<FilteredItem> = when (selectedOption.value) {
        "Venues" -> venues
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = if (it.coverImages.isNotEmpty()) it.coverImages[0] else "https://shorturl.at/6DXeG"
            )
        }
        "Caterers" -> catering
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = if (it.coverImages.isNotEmpty()) it.coverImages[0] else "https://shorturl.at/6DXeG"
            )
        }
        "Decorators" -> decorators
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = if (it.coverImages.isNotEmpty()) it.coverImages[0] else "https://shorturl.at/6DXeG"
            )
        }
        "Photographers" -> photographers
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = if (it.coverImages.isNotEmpty()) it.coverImages[0] else "https://shorturl.at/6DXeG"
            )
        }
        "OtherServices" -> vendors
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = (if (it.coverImages.isNotEmpty()) it.coverImages[0] else "https://shorturl.at/6DXeG").toString()
            )
            }
        else -> emptyList()
    }

    val minprice by remember(selectedOption.value) {
        derivedStateOf {
            when (selectedOption.value) {
                "Venues" -> venues.minByOrNull { it.priceNight }?.priceNight?.toFloat() ?: 0f
                "Caterers" -> catering.minByOrNull { dataXX -> dataXX.menuItems.sumOf { it.cost ?: 0 } }
                    ?.let { dataXX -> dataXX.menuItems.sumOf { it.cost ?: 0 } }?.toFloat() ?: 0f
                "Decorators" -> decorators.minByOrNull { dataX -> dataX.amenities.sumOf { it?.cost ?: 0 } }
                    ?.let { dataX -> dataX.amenities.sumOf { it?.cost ?: 0 } }?.toFloat() ?: 0f
                "Photographers" -> photographers.minByOrNull { it.cost }?.cost?.toFloat() ?: 0f
                "OtherServices" -> vendors.minByOrNull { it.cost }?.cost?.toFloat() ?: 0f
                else -> 0f
            }
        }
    }

    val maxprice by remember(selectedOption.value) {
        derivedStateOf {
            when (selectedOption.value) {
                "Venues" -> venues.maxByOrNull { it.priceNight }?.priceNight?.toFloat() ?: 0f
                "Caterers" -> catering.maxByOrNull { dataXX -> dataXX.menuItems.sumOf { it.cost ?: 0 } }
                    ?.let { dataXX -> dataXX.menuItems.sumOf { it.cost ?: 0 } }?.toFloat() ?: 0f
                "Decorators" -> decorators.maxByOrNull { dataX -> dataX.amenities.sumOf { it?.cost ?: 0 } }
                    ?.let { dataX -> dataX.amenities.sumOf { it?.cost ?: 0 } }?.toFloat() ?: 0f
                "Photographers" -> photographers.maxByOrNull { it.cost }?.cost?.toFloat() ?: 0f
                "OtherServices" -> vendors.maxByOrNull { it.cost }?.cost?.toFloat() ?: 0f
                else -> 0f
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

    if (showDialog) {
        DialogFilter(
            onDismissRequest = { showDialog = false },
            selectedTabIndex = remember { mutableStateOf(selectedTabIndex) },
            tabs = tabs,
            priceRange = priceRange,
            maxprice = maxprice + 100000f,
            minprice = minprice,
            onPriceChange = { newValue ->
                selectedPrice.value = newValue
            }
        )
    }

    LaunchedEffect(selectedRating > 0) {
        if (selectedRating > 0) {
            val body = Ratings(
                bookingId = 1,
                comments = "Hello",
                rating = selectedRating,
                serviceId = 1,
                serviceType = "hello"
            )

            userviewmodel.giveRating(token = token, giverating = body)
            delay(3000)
            bottomSheetState.hide()
            isSheetopen = false
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
                                        isSheetopen = true
                                    }

                                    NavigationBarItems.Msg -> {
                                        navController.navigate(route = Screens.Msg.route)
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
                            color = backgroundcolor,
                            fontFamily = Bebas
                        )
                    }
                }
            }
        }
    ) {
        var isSearching by remember { mutableStateOf(false) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp)
        ) {
            item {

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 80.dp, bottom = 80.dp)
                ) {
                    val (searchrow, itemstobook, popularheading, hotdealingheading, cards1, card2) = createRefs()

                    Box(
                        modifier = Modifier.constrainAs(searchrow) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.9f)
                        }
                    ) {
                        AnimatedVisibility(
                            visible = !isSearching,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "What do you want to book?",
                                    fontSize = dimens.heading,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 35.sp,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp),
                                    maxLines = 2,
                                    overflow = Ellipsis
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color.Gray)
                                ) {
                                    IconButton(
                                        onClick = { isSearching = !isSearching }
                                    ) {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = null,
                                            tint = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                        AnimatedVisibility(
                            visible = isSearching,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            Column {
                                TextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    placeholder = { Text(text = "Search...") },
                                    modifier = Modifier.fillMaxWidth(),
                                    leadingIcon = {
                                        IconButton(onClick = {
                                            isSearching = false
                                        }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Back"
                                            )
                                        }
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                    ),
                                    trailingIcon = {
                                        IconButton(
                                            onClick = {
                                                showDialog = true
                                            }
                                        ) {
                                            Icon(
                                                Icons.Default.FilterAlt,
                                                contentDescription = null,
                                            )
                                        }
                                    }
                                )

                                if (filteredItems.isNotEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                    ) {
                                        items(filteredItems.size) { suggestions ->
                                            AddHeight(10.dp)
                                            FloatingActionButton(
                                                modifier = Modifier.fillMaxWidth(),
                                                onClick = {
                                                    navController.navigate(
                                                        route = selectedOption.value + "_Screen/${filteredItems[suggestions].id}"
                                                    )
                                                },
                                                containerColor = backgroundcolor
                                            ) {
                                                Row (
                                                    modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Column {
                                                        Text(
                                                            text = filteredItems[suggestions].name,
                                                            color = secondarycolor
                                                        )
                                                        Text(
                                                            text = selectedOption.value,
                                                            color = secondarycolor
                                                        )
                                                    }
                                                    AsyncImage(
                                                        model = filteredItems[suggestions].imageUrl,
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .size(50.dp)
                                                            .padding(4.dp)
                                                            .clip(RoundedCornerShape(20.dp))
                                                            .fillMaxSize(),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    LazyRow(
                        modifier = Modifier.constrainAs(itemstobook) {
                            top.linkTo(searchrow.bottom, margin = 30.dp)
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
                                onclick = {
                                    selectedOption.value = BookingOptions[option].second
                                }
                            )
                            AddWidth(dimens.scrollspacer)
                        }
                    }

                    if (((selectedOption.value == "Decorators") && decorators.isEmpty()) ||
                        ((selectedOption.value == "Venues") && venues.isEmpty()) ||
                        ((selectedOption.value == "Caterers") && catering.isEmpty()) ||
                        ((selectedOption.value == "Photographers") && photographers.isEmpty()) ||
                        ((selectedOption.value == "OtherServices") && vendors.isEmpty())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .constrainAs(popularheading) {
                                    top.linkTo(itemstobook.bottom, margin = 15.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom, margin = 30.dp)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    else {
                        Row(
                            modifier = Modifier
                                .constrainAs(popularheading) {
                                    top.linkTo(itemstobook.bottom, margin = 15.dp)
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Popular " + selectedOption.value,
                                fontSize = dimens.labeltext,
                                fontWeight = FontWeight.Bold
                            )
                            Text("See All",
                                modifier = Modifier.clickable { },
                                color = secondarycolor,
                                fontWeight = FontWeight.Bold,
                                fontSize = dimens.buttontext
                            )
                        }

                        LazyRow(
                            modifier = Modifier.constrainAs(cards1) {
                                top.linkTo(popularheading.bottom, margin = 15.dp)
                            },
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(dimens.scrollspacer)
                        ) {
                            when (selectedOption.value) {
                                "Venues" -> {
                                    val filteredVenues = venuesp.filter { it.priceNight.toFloat() in minprice..selectedPrice.value }
                                    items(filteredVenues.size) {
                                        Cards(
                                            model = if (filteredVenues[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredVenues[it].coverImages[0],
                                            text = filteredVenues[it].name,
                                            onclick = {
                                                navController.navigate("Venues_Screen/${venuesp[it].id}")
                                            }
                                        )
                                        AddWidth(dimens.scrollspacer)
                                    }
                                }
                                "Caterers" -> {
                                    val filteredCaterers = catering.filter {
                                        val totalCost = it.menuItems.sumOf { menu -> menu.cost ?: 0 }
                                        totalCost in (minprice.toInt())..(priceRange.value).toInt()
                                    }
                                    items(filteredCaterers.size) {
                                        Cards(
                                            model = (if (filteredCaterers[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredCaterers[it].coverImages[0]),
                                            text = filteredCaterers[it].name,
                                            onclick = {
                                                navController.navigate(route = "Caterers_Screen/${catering[it].id}")
                                            }
                                        )
                                    }
                                }
                                "Decorators" -> {
                                    val filteredDecorators = decorators.filter {
                                        val totalCost = it.amenities.sumOf { amenity -> amenity?.cost ?: 0 }
                                        totalCost in (minprice.toInt())..(priceRange.value).toInt()
                                    }
                                    items(filteredDecorators.size) {
                                        Cards(
                                            model = (if (filteredDecorators[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredDecorators[it].coverImages[0]),
                                            text = filteredDecorators[it].name,
                                            onclick = {
                                                navController.navigate(route = "Decorators_Screen/${decorators[it].id}?edit=${false}")
                                            }
                                        )
                                    }
                                }
                                "Photographers" -> {
                                    val filteredPhotographers = photographers.filter { it.cost.toFloat() in minprice..selectedPrice.value }
                                    items (filteredPhotographers.size) {
                                        Cards(
                                            model = (if (filteredPhotographers[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredPhotographers[it].coverImages[0]),
                                            text = filteredPhotographers[it].name,
                                            onclick = {
                                                navController.navigate(route = "Photographers_Screen/${photographers[it].id}")
                                            }
                                        )
                                    }
                                }
                                "OtherServices" -> {
                                    val filteredVendors = vendors.filter { it.cost.toFloat() in minprice..selectedPrice.value }
                                    items (filteredVendors.size) {
                                        Cards(
                                            model = ((if (filteredVendors[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredVendors[it].coverImages[0]).toString()),
                                            text = filteredVendors[it].name,
                                            onclick = {
                                                navController.navigate(route = "OtherServices_Screen/${vendors[it].id}")
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Row (
                            modifier = Modifier
                                .constrainAs(hotdealingheading) {
                                    top.linkTo(cards1.bottom, margin = 75.dp)
                                }
                                .padding(horizontal = 18.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Hot Deals",
                                fontSize = dimens.labeltext,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        LazyRow (
                            modifier = Modifier.constrainAs(card2) {
                                top.linkTo(hotdealingheading.bottom, margin = 15.dp)
                            },
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(dimens.scrollspacer)
                        ) {
                            when (selectedOption.value) {
                                "Venues" -> {
                                    val filteredVenues = venuesh.filter { it.priceNight.toFloat() in minprice..selectedPrice.value }
                                    items(filteredVenues.size) {
                                        Cards(
                                            model = if (filteredVenues[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredVenues[it].coverImages[0],
                                            text = filteredVenues[it].name,
                                            onclick = {
                                                navController.navigate("Venues_Screen/${venuesp[it].id}")
                                            }
                                        )
                                        AddWidth(dimens.scrollspacer)
                                    }
                                }
                                "Caterers" -> {
                                    val filteredCaterers = catering.filter {
                                        val totalCost = it.menuItems.sumOf { menu -> menu.cost ?: 0 }
                                        totalCost in (minprice.toInt())..(priceRange.value).toInt()
                                    }
                                    items(filteredCaterers.size) {
                                        Cards(
                                            model = (if (filteredCaterers[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredCaterers[it].coverImages[0]),
                                            text = filteredCaterers[it].name,
                                            onclick = {
                                                navController.navigate(route = "Caterers_Screen/${catering[it].id}")
                                            }
                                        )
                                    }
                                }
                                "Decorators" -> {
                                    val filteredDecorators = decorators.filter {
                                        val totalCost = it.amenities.sumOf { amenity -> amenity?.cost ?: 0 }
                                        totalCost in (minprice.toInt())..(priceRange.value).toInt()
                                    }
                                    items(filteredDecorators.size) {
                                        Cards(
                                            model = (if (filteredDecorators[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredDecorators[it].coverImages[0]),
                                            text = filteredDecorators[it].name,
                                            onclick = {
                                                navController.navigate(route = "Decorators_Screen/${decorators[it].id}?edit=${false}")
                                            }
                                        )
                                    }
                                }
                                "Photographers" -> {
                                    val filteredPhotographers = photographers.filter { it.cost.toFloat() in minprice..selectedPrice.value }
                                    items (filteredPhotographers.size) {
                                        Cards(
                                            model = (if (filteredPhotographers[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredPhotographers[it].coverImages[0]),
                                            text = filteredPhotographers[it].name,
                                            onclick = {
                                                navController.navigate(route = "Photographers_Screen/${photographers[it].id}")
                                            }
                                        )
                                    }
                                }
                                "OtherServices" -> {
                                    val filteredVendors = vendors.filter { it.cost.toFloat() in minprice..selectedPrice.value }
                                    items (filteredVendors.size) {
                                        Cards(
                                            model = ((if (filteredVendors[it].coverImages.isEmpty()) "https://shorturl.at/6DXeG" else filteredVendors[it].coverImages[0]).toString()),
                                            text = filteredVendors[it].name,
                                            onclick = {
                                                navController.navigate(route = "OtherServices_Screen/${vendors[it].id}")
                                            }
                                        )
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
                                .fillMaxHeight(fraction = 0.46f)
                                .navigationBarsPadding(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text("Booking Name", fontSize = 32.sp, fontFamily = Bebas, color = secondarycolor)
                            AddHeight(30.dp)
                            Text("Rate Our Service and Help Others", fontSize = 25.sp, fontFamily = Bebas, color = secondarycolor)
                            AddHeight(100.dp)
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
                            }
                            if (selectedRating > 0) {
                                if(isLoading) {
                                    CircularProgressIndicator()
                                }
                                else {
                                    Text(answer, fontSize = 25.sp, fontFamily = Bebas, color = secondarycolor)
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
}
