package com.example.mezbaan.view

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mezbaan.R
import com.example.mezbaan.model.dataprovider.BookingOptions
import com.example.mezbaan.model.dataprovider.NavigationBarItems
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.alterblack
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
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
fun Cards(text: String, onclick: () -> Unit, model: String = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYz8zEAFyjZKTNeQW-MRagzdrD-bTFpArsiA&s") {
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
            val painter = rememberAsyncImagePainter(model = model)
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
                    Text(text, color = Color.White, fontSize = dimens.labeltext, fontFamily = Bebas)
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    navController: NavController,
    authviewmodel: AuthViewModel = viewModel()
) {
    val navigationBarItems = remember { NavigationBarItems.entries }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val insets = WindowInsets.navigationBars
    val bottomInsetDp = with(LocalDensity.current) { insets.getBottom(LocalDensity.current).toDp() }

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
        val selectedOption = remember { mutableStateOf("Venues") }
        var isSearching by remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }
        val suggestions = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Honeydew")

        val filteredSuggestions = remember(searchQuery) {
            if (searchQuery.isNotEmpty()) {
                suggestions.filter { it.contains(searchQuery, ignoreCase = true) }
            } else {
                emptyList()
            }
        }

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
                                    )
                                )

                                if (filteredSuggestions.isNotEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                    ) {
                                        items(filteredSuggestions.size) { suggestions ->
                                            AddHeight(10.dp)
                                            FloatingActionButton(
                                                modifier = Modifier.fillMaxWidth(),
                                                onClick = {},
                                                containerColor = backgroundcolor
                                            ) {
                                                Row (
                                                    modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Column {
                                                        Text(
                                                            text = filteredSuggestions[suggestions],
                                                            color = secondarycolor
                                                        )
                                                        Text(
                                                            text = "Venues",
                                                            color = secondarycolor
                                                        )
                                                    }
                                                    Image(
                                                        painter = painterResource(id = R.drawable.mezbaan),
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
                                text = if (BookingOptions[option].second == "Photographers") "Photo\ngraphers" else BookingOptions[option].second,
                                isSelected = selectedOption.value == BookingOptions[option].second,
                                onclick = { selectedOption.value = BookingOptions[option].second }
                            )
                            AddWidth(dimens.scrollspacer)
                        }
                    }

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
                                item {
                                    Cards(
                                        model = "https://drive.google.com/uc?export=view&id=1-M2iriYaun_6875iCrDpAaRCE1ojbnpT",
                                        text = "The Corum",
                                        onclick = {
                                            navController.navigate(route = Screens.Venues.route)
                                        }
                                    )
                                }
                            }
                            "Caterers" -> {
                                item {
                                    Cards(
                                        model = "https://drive.google.com/uc?export=view&id=1hS4R5zDqGr2aMiPY8SSzjy-1isAqUgBq",
                                        text = "Lal Qila",
                                        onclick = {
                                            navController.navigate(route = Screens.Caterers.route)
                                        }
                                    )
                                }
                            }
                            "Decorators" -> {
                                item {
                                    Cards(
                                        model = "https://drive.google.com/uc?export=view&id=1qzCG3vIVY9uyP-g5ZsGJFfMcP36cHuJi",
                                        text = "Indo",
                                        onclick = {
                                            navController.navigate(route = Screens.Decorators.route)
                                        }
                                    )
                                }
                            }
                            "Photographers" -> {
                                item {
                                    Cards(
                                        model = "https://drive.google.com/uc?export=view&id=1Gae9YMksmUfU74cgXX0x1ivwOdLb4H4L",
                                        text = "Irfan Junejo",
                                        onclick = {
                                            navController.navigate(route = Screens.Vendors.route)
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
                        if (selectedOption.value == "Venues") {
                            item {
                                Cards(
                                    model = "https://drive.google.com/uc?export=view&id=1-M2iriYaun_6875iCrDpAaRCE1ojbnpT",
                                    text = "Santorini",
                                    onclick = {
                                        navController.navigate(route = Screens.Venues.route)
                                    }
                                )
                                AddWidth(dimens.scrollspacer)
                                Cards(
                                    model = "https://drive.google.com/uc?export=view&id=1-M2iriYaun_6875iCrDpAaRCE1ojbnpT",
                                    text = "Santorini",
                                    onclick = {
                                        navController.navigate(route = Screens.Venues.route)
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
