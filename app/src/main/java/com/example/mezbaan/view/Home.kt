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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import com.example.mezbaan.model.dataprovider.BookingOptions
import com.example.mezbaan.model.dataprovider.FilteredItem
import com.example.mezbaan.model.dataprovider.NavigationBarItems
import com.example.mezbaan.model.models.Data
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
import com.example.mezbaan.viewmodel.navigation.Screens
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

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

@Composable
fun DialogFilter(
    onDismissRequest: () -> Unit,
    selectedTabIndex: MutableState<Int>,
    tabs: List<String>,
    priceRange: MutableState<ClosedFloatingPointRange<Float>>,
    minprice: Float,
    maxprice: Float
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column (
            modifier = Modifier.height(250.dp).background(backgroundcolor)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex.value,
                edgePadding = 0.dp,
                modifier = Modifier.padding(bottom = 16.dp),
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
                            )
                        }
                    )
                }
            }

            RangeSlider(
                value = priceRange.value,
                valueRange = minprice..maxprice,
                onValueChange = { priceRange.value = it },
                steps = 5,
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    venues: List<Data>,
    navController: NavController,
    authviewmodel: AuthViewModel = viewModel(),
    cateringviewmodel : CateringViewModel = hiltViewModel(),
    decoratorviewmodel : DecoratorViewModel = hiltViewModel(),
    photographerviewmodel : PhotographerViewModel = hiltViewModel(),
    otherservicesviewmodel : OtherServicesViewModel = hiltViewModel(),
) {
    val insets = WindowInsets.navigationBars
    val venuesp = venues.sortedByDescending { it.rating }
    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val selectedTabIndex by remember { mutableIntStateOf(0) }
    val navigationBarItems = remember { NavigationBarItems.entries }
    val selectedOption = remember { mutableStateOf("Venues") }
    val catering by cateringviewmodel.menu.collectAsStateWithLifecycle()
    val decorators by decoratorviewmodel.decorators.collectAsStateWithLifecycle()
    val vendors by otherservicesviewmodel.vendors.collectAsStateWithLifecycle()
    val venuesh = venues.sortedWith(compareBy<Data> {it.priceOff}.thenBy { it.rating })
    val photographers by photographerviewmodel.photographers.collectAsStateWithLifecycle()
    val bottomInsetDp = with(LocalDensity.current) { insets.getBottom(LocalDensity.current).toDp() }

    val tabs = listOf("Price Filter", "Tab 2", "Tab 3")

    val filteredItems: List<FilteredItem> = when (selectedOption.value) {
        "Venues" -> venues
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = if (it.images.isNotEmpty()) it.images[0] else "https://shorturl.at/6DXeG"
            )
        }
        "Caterers" -> catering
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = if (it.images.isNotEmpty()) it.images[0] else "https://shorturl.at/6DXeG"
            )
        }
        "Decorators" -> decorators
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = if (it.images.isNotEmpty()) it.images[0] else "https://shorturl.at/6DXeG"
            )
        }
        "Photographers" -> photographers
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = if (it.images.isNotEmpty()) it.images[0] else "https://shorturl.at/6DXeG"
            )
        }
        "OtherServices" -> vendors
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .map { FilteredItem(
                name = it.name,
                id = it.id,
                imageUrl = (if (it.images.isNotEmpty()) it.images[0] else "https://shorturl.at/6DXeG").toString()
            )
            }
        else -> emptyList()
    }

    val minprice by remember {
        mutableStateOf(
            when (selectedOption.value) {
                "Venues" -> {
                    venues.minByOrNull { it.priceNight }?.priceNight?.toFloat() ?: 0f
                }
                "Caterers" -> {
                    catering.minByOrNull { dataXX -> dataXX.menuItems.sumOf { it.cost ?: 0 } }?.let { dataXX -> dataXX.menuItems.sumOf { it.cost
                        ?: 0 } } ?: 0
                }
                "Decorators" -> {
                    decorators.minByOrNull { dataX -> dataX.amenities.sumOf { it?.cost ?: 0 } }?.let { dataX -> dataX.amenities.sumOf { it?.cost ?: 0 } } ?: 0
                }
                "Photographers" -> {
                    photographers.minByOrNull { it.cost }?.cost?.toFloat() ?: 0f
                }
                "OtherServices" -> {
                    vendors.minByOrNull { it.cost }?.cost?.toFloat() ?: 0f
                }
                else -> 0f
            }
        )
    }

    val maxprice by remember {
        mutableStateOf(
            when (selectedOption.value) {
                "Venues" -> {
                    venues.maxByOrNull { it.priceNight }?.priceNight?.toFloat() ?: 0f
                }
                "Caterers" -> {
                    catering.maxByOrNull { dataXX -> dataXX.menuItems.sumOf { it.cost ?: 0 } }?.let { dataXX -> dataXX.menuItems.sumOf { it.cost
                        ?: 0 } } ?: 0
                }
                "Decorators" -> {
                    decorators.maxByOrNull { dataX -> dataX.amenities.sumOf { it?.cost ?: 0 } }?.let { dataX -> dataX.amenities.sumOf { it?.cost ?: 0 } } ?: 0
                }
                "Photographers" -> {
                    photographers.maxByOrNull { it.cost }?.cost?.toFloat() ?: 0f
                }
                "OtherServices" -> {
                    vendors.maxByOrNull { it.cost }?.cost?.toFloat() ?: 0f
                }
                else -> 0f
            }
        )
    }

    val priceRange = remember { mutableStateOf(minprice.toFloat()..maxprice.toFloat()) }

    if (showDialog) {
        DialogFilter(
            onDismissRequest = { showDialog = false },
            selectedTabIndex = remember { mutableIntStateOf(selectedTabIndex) },
            tabs = tabs,
            priceRange = priceRange,
            maxprice = maxprice.toFloat(),
            minprice = minprice.toFloat()
        )
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
                            color = backgroundcolor
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

                    if (selectedOption.value == "Decorators" && decorators.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize().constrainAs(popularheading) {
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
                                    items(venuesp.size) {
                                        Cards(
                                            model = if (venuesp[it].images.isEmpty()) "https://shorturl.at/6DXeG" else venuesp[it].images[0],
                                            text = venuesp[it].name,
                                            onclick = {
                                                navController.navigate("Venues_Screen/${venuesp[it].id}")
                                            }
                                        )
                                        AddWidth(dimens.scrollspacer)
                                    }
                                }
                                "Caterers" -> {
                                    items(catering.size) {
                                        Cards(
                                            model = "https://shorturl.at/6DXeG",
                                            text = catering[it].name,
                                            onclick = {
                                                navController.navigate(route = "Caterers_Screen/${catering[it].id}")
                                            }
                                        )
                                    }
                                }
                                "Decorators" -> {
                                    items(decorators.size) {
                                        Cards(
                                            model = (if (decorators[it].images.isEmpty()) "https://shorturl.at/6DXeG" else decorators[it].images[0]),
                                            text = decorators[it].name,
                                            onclick = {
                                                navController.navigate(route = "Decorators_Screen/${decorators[it].id}")
                                            }
                                        )
                                    }
                                }
                                "Photographers" -> {
                                    items (photographers.size) {
                                        Cards(
                                            model = (if (photographers[it].images.isEmpty()) "https://shorturl.at/6DXeG" else photographers[it].images[0]),
                                            text = photographers[it].name,
                                            onclick = {
                                                navController.navigate(route = "Photographers_Screen/${photographers[it].id}")
                                            }
                                        )
                                    }
                                }
                                "OtherServices" -> {
                                    items (vendors.size) {
                                        Cards(
                                            model = ((if (vendors[it].images.isEmpty()) "https://shorturl.at/6DXeG" else vendors[it].images[0]).toString()),
                                            text = vendors[it].name,
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
                                    items(venuesh.size) {
                                        Cards(
                                            model = if (venuesh[it].images.isEmpty()) "https://shorturl.at/6DXeG" else venuesh[it].images[0],
                                            text = venuesh[it].name,
                                            onclick = {
                                                navController.navigate("Venues_Screen/${it}")
                                            }
                                        )
                                        AddWidth(dimens.scrollspacer)
                                    }
                                }
                                "Catering" -> {
                                    items(catering.size) {
                                        Cards(
                                            model = "https://shorturl.at/6DXeG",
                                            text = catering[it].name,
                                            onclick = {
                                                navController.navigate(route = "Caterers_Screen/${catering[it].id}")
                                            }
                                        )
                                    }
                                }
                                "Decorators" -> {
                                    items(decorators.size) {
                                        Cards(
                                            model = (if (decorators[it].images.isEmpty()) "https://shorturl.at/6DXeG" else decorators[it].images[0]),
                                            text = decorators[it].name,
                                            onclick = {
                                                navController.navigate(route = "Decorators_Screen/${decorators[it].id}")
                                            }
                                        )
                                    }
                                }
                                "Photographers" -> {
                                    items (photographers.size) {
                                        Cards(
                                            model = (if (photographers[it].images.isEmpty()) "https://shorturl.at/6DXeG" else photographers[it].images[0]),
                                            text = photographers[it].name,
                                            onclick = {

                                                navController.navigate(route = "Photographers_Screen/${photographers[it].id}")
                                            }
                                        )
                                    }
                                }
                                "OtherServices" -> {
                                    items (vendors.size) {
                                        Cards(
                                            model = ((if (vendors[it].images.isEmpty()) "https://shorturl.at/6DXeG" else vendors[it].images[0]).toString()),
                                            text = vendors[it].name,
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
            }
        }
    }
}
