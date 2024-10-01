package com.example.mezbaan.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column 
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
            .height(MaterialTheme.dimens.scroll)
            .width(MaterialTheme.dimens.scrollwidth),
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
                modifier = Modifier.size(25.dp)
            )
            //AddHeight(10.dp)
            Text(text, fontSize = MaterialTheme.dimens.fontsize)
        }
    }
}

@Composable
fun Cards(image: Painter, text: String, onclick: () -> Unit) {
    FloatingActionButton(
        onClick = onclick,
        modifier = Modifier
            .height(MaterialTheme.dimens.cards)
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
                tint = Color.White
            )
            AddHeight(10.dp)
            Text(text, color = Color.White)
        }
    }
}


@Composable
fun Home(navController: NavController) {
    Surface {
        val selectedOption = remember { mutableStateOf("Venues") }

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AddHeight(MaterialTheme.dimens.small3)
                Row (
                    modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("What do you want\nto book?",
                        fontSize = MaterialTheme.dimens.heading,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
                AddHeight(MaterialTheme.dimens.small3)

                LazyRow (
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                ) {
                    item {
                        Itemstobook(
                            image = painterResource(R.drawable.banquet),
                            text = "Venues",
                            isSelected = selectedOption.value == "Venues",
                            onclick = { selectedOption.value = "Venues" }
                        )
                        AddWidth(12.dp)
                        Itemstobook(
                            image = painterResource(R.drawable.food),
                            text = "Caterers",
                            isSelected = selectedOption.value == "Caterers",
                            onclick = { selectedOption.value = "Caterers" }
                        )
                        AddWidth(18.dp)
                        Itemstobook(
                            image = painterResource(R.drawable.decor),
                            text = "Decorators",
                            isSelected = selectedOption.value == "Decorators",
                            onclick = { selectedOption.value = "Decorators" }
                        )
                        AddWidth(18.dp)
                        Itemstobook(
                            image = painterResource(R.drawable.servicevendor),
                            text = "Vendors",
                            isSelected = selectedOption.value == "Vendors",
                            onclick = { selectedOption.value = "Vendors" }
                        )
                        AddWidth(18.dp)
                    }
                }

                AddHeight(MaterialTheme.dimens.small2)

                Row (
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Popular " + selectedOption.value,
                        fontSize = MaterialTheme.dimens.heading,
                        fontWeight = FontWeight.Bold
                    )
                    Text("See All",
                        modifier = Modifier.clickable {  },
                        color = secondarycolor,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.dimens.fontsize
                    )
                }

                LazyRow (
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
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

                AddHeight(MaterialTheme.dimens.small2)

                Row (
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Hot Deals",
                        fontSize = MaterialTheme.dimens.heading,
                        fontWeight = FontWeight.Bold
                    )
                }

                LazyRow (
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
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
                            AddWidth(18.dp)
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
                AddHeight(MaterialTheme.dimens.small3)
            }
        }
    }
}

