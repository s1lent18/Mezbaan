package com.example.mezbaan.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column 
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.constraintlayout.compose.Dimension
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.alterblack
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.Screens

@Composable
fun Itemstobook(
    image: Painter,
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
            Icon(
                painter = image,
                contentDescription = null,
                tint = if (isSelected) Color.White else if(isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier.size(dimens.iconsize)
            )
            //AddHeight(10.dp)
            Text(text, fontSize = dimens.buttontext)
        }
    }
}

@Composable
fun Cards(image: Painter, text: String, onclick: () -> Unit) {
    FloatingActionButton(
        onClick = onclick,
        modifier = Modifier
            .height(dimens.cards)
            .aspectRatio(0.65f),
        containerColor = backgroundcolor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = image,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(dimens.iconsize)
            )
            AddHeight(10.dp)
            Text(text, color = Color.White, fontSize = dimens.buttontext)
        }
    }
}


@Composable
fun Home(navController: NavController) {
    Surface {
        val selectedOption = remember { mutableStateOf("Venues") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, bottom = 40.dp)
            ) {
                val (searchrow, itemstobook, popularheading, hotdealingheading, cards1, card2) = createRefs()

                Row (
                    modifier = Modifier
                        .constrainAs(searchrow) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.9f)
                        },
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
                        modifier = Modifier.clip(CircleShape).background(Color.Gray)
                    ) {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                }

                LazyRow (
                    modifier = Modifier.constrainAs(itemstobook) {
                        top.linkTo(searchrow.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                ) {
                    item {
                        Itemstobook(
                            image = painterResource(R.drawable.banquet),
                            text = "Venues",
                            isSelected = selectedOption.value == "Venues",
                            onclick = { selectedOption.value = "Venues" }
                        )
                        AddWidth(dimens.scrollspacer)
                        Itemstobook(
                            image = painterResource(R.drawable.food),
                            text = "Caterers",
                            isSelected = selectedOption.value == "Caterers",
                            onclick = { selectedOption.value = "Caterers" }
                        )
                        AddWidth(dimens.scrollspacer)
                        Itemstobook(
                            image = painterResource(R.drawable.decor),
                            text = "Decorators",
                            isSelected = selectedOption.value == "Decorators",
                            onclick = { selectedOption.value = "Decorators" }
                        )
                        AddWidth(dimens.scrollspacer)
                        Itemstobook(
                            image = painterResource(R.drawable.servicevendor),
                            text = "Vendors",
                            isSelected = selectedOption.value == "Vendors",
                            onclick = { selectedOption.value = "Vendors" }
                        )
                        AddWidth(dimens.scrollspacer)
                    }
                }

                Row (
                    modifier = Modifier
                        .constrainAs(popularheading) {
                            top.linkTo(itemstobook.bottom, margin = 15.dp)
                            //start.linkTo(parent.start)
                            //end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Popular " + selectedOption.value,
                        fontSize = dimens.heading,
                        fontWeight = FontWeight.Bold
                    )
                    Text("See All",
                        modifier = Modifier.clickable {  },
                        color = secondarycolor,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimens.buttontext
                    )
                }

                LazyRow (
                    modifier = Modifier.constrainAs(cards1) {
                        top.linkTo(popularheading.bottom, margin = 15.dp)
                    },
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(dimens.scrollspacer)
                ) {
                    if (selectedOption.value == "Venues") {
                        item {
                            Cards(
                                image = painterResource(R.drawable.hotel),
                                text = "Santorini",
                                onclick = {
                                    navController.navigate(route = Screens.Venues.route)
                                }
                            )
                        }
                    }
                }

                Row (
                    modifier = Modifier.constrainAs(hotdealingheading) {
                        top.linkTo(cards1.bottom, margin = 15.dp)
                    }
                        .padding(horizontal = 18.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Hot Deals",
                        fontSize = dimens.heading,
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
                                image = painterResource(R.drawable.hotel),
                                text = "Santorini",
                                onclick = {
                                    navController.navigate(route = Screens.Venues.route)
                                }
                            )
                            AddWidth(dimens.scrollspacer)
                            Cards(
                                image = painterResource(R.drawable.hotel),
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