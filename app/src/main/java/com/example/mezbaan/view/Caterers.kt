package com.example.mezbaan.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.mezbaan.R
import com.example.mezbaan.model.dataprovider.AppetizersOption
import com.example.mezbaan.model.dataprovider.CaterersOption
import com.example.mezbaan.model.dataprovider.DessertsOption
import com.example.mezbaan.model.dataprovider.DrinksOptions
import com.example.mezbaan.model.dataprovider.MainCourseOptions
import com.example.mezbaan.ui.theme.Bebas
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor

@Composable
fun Display(imageUrl: String, title: String) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(150.dp)
                .clickable { },
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
        Text(title)
    }
}

@Composable
fun Caterers() {
    Surface {
        var searchQuery by remember { mutableStateOf("") }
        val selectedOption = remember { mutableStateOf("Appetizers") }
        val filteredItems = if (searchQuery.isNotEmpty()) {
            // Filter based on search query
            when (selectedOption.value) {
                "Appetizers" -> AppetizersOption.filter { it.second.contains(searchQuery, ignoreCase = true) }
                "Main-Course" -> MainCourseOptions.filter { it.second.contains(searchQuery, ignoreCase = true) }
                "Desserts" -> DessertsOption.filter { it.second.contains(searchQuery, ignoreCase = true) }
                "Drinks" -> DrinksOptions.filter { it.second.contains(searchQuery, ignoreCase = true) }
                else -> emptyList()
            }
        } else {
            // Default to selected category items
            when (selectedOption.value) {
                "Appetizers" -> AppetizersOption
                "Main-Course" -> MainCourseOptions
                "Desserts" -> DessertsOption
                "Drinks" -> DrinksOptions
                else -> emptyList()
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
                    .padding(top = 80.dp, bottom = 40.dp)
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
                        top.linkTo(boxes.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }.height(400.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(filteredItems.size) { option ->
                            Display(
                                imageUrl = filteredItems[option].first,
                                title = filteredItems[option].second
                            )
                        }
                    }
                }

                Button(
                    onClick = {  },
                    modifier = Modifier
                        .size(65.dp)
                        .constrainAs(checkoutbutton) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, margin = 16.dp)
                        },
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
            }
        }
    }
}