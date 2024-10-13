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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.google.accompanist.flowlayout.FlowRow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun Cardammedity(text : String) {
    Box (
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 15.sp, color = Color.Black)
    }
}

@Composable
fun BookingCard(month: String) {
    Column {
        Text(text = month, fontSize = 20.sp)
    }
}

@Composable
fun GridOfBoxes() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(6), // Creates a grid with 5 columns
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(30) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(secondarycolor),
            ) {

            }
        }
    }
}

@Composable
fun HorizontalMonthPager() {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 6 }) // 6 months
    val currentDate = LocalDate.now()

    Box (
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 20.dp
        ) { page ->

            val currentMonth = currentDate.plusMonths(page.toLong())
            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
            val formattedDate = currentMonth.format(formatter)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundcolor),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.9f)
                        .fillMaxHeight(fraction = 0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 20.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row {
                            BookingCard(month = formattedDate)
                        }
                        AddHeight(10.dp)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            GridOfBoxes()
                        }
                        AddHeight(10.dp)
                    }
                }
            }
        }

        // Page Indicator (Dots) below the grid
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            repeat(6) { pageIndex ->
                val isSelected = pageIndex == pagerState.currentPage
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) secondarycolor else Color.Blue)
                        .padding(4.dp)
                )
                if (pageIndex != 5) {
                    AddWidth(8.dp)
                }
            }
        }
    }
}

@Preview
@Composable
fun Venues() {
    Surface {
        val pics = listOf(
            R.drawable.mezbaan,
            R.drawable.b1,
            R.drawable.b2,
        )
        val pagerState = rememberPagerState(pageCount = { pics.size })
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.40f)
                    .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
            ) {
                HorizontalPager(
                    state = pagerState,
                    key = { pics[it] }
                ) { index ->
                    Image(
                        painter = painterResource(id = pics[index]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    pics.forEachIndexed { pageIndex, _ ->
                        val isSelected = pageIndex == pagerState.currentPage
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) Color.Black else Color.Gray)
                                .padding(4.dp)
                        )
                        if (pageIndex != pics.size - 1) {
                            AddWidth(8.dp)
                        }
                    }
                }
            }
            AddHeight(dimens.small3)
            LazyColumn (
                modifier = Modifier.fillMaxWidth(fraction = 0.9f)
            ) {
                item{
                    Column {
                        Text(
                            "Name of the venue",
                            fontSize = dimens.fontsize,
                        )
                        AddHeight(dimens.small1)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(dimens.iconsize)
                            )
                            AddWidth(10.dp)
                            Text("Rating of the venue", fontSize = dimens.buttontext)
                        }
                    }
                    AddHeight(dimens.small1)
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray
                    )
                    AddHeight(dimens.small1)
                    Text(
                        "Amenities",
                        fontSize = dimens.fontsize,
                    )
                    AddHeight(dimens.small1)
                    FlowRow(
                        mainAxisSpacing = 10.dp,
                        crossAxisSpacing = 10.dp,
                    ) {
                        Cardammedity("Wi-fi")
                        Cardammedity("Washing Machine")
                        Cardammedity("A/C")
                        Cardammedity("Washing Machine")
                        Cardammedity("Washing Machine")
                        Cardammedity("Washing Machine")
                    }
                    AddHeight(dimens.small2)
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray
                    )
                    HorizontalMonthPager()
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray
                    )
                    AddHeight(dimens.small2)
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Business Name",
                                fontSize = dimens.fontsize,
                            )
                            AddHeight(dimens.small1)
                            Text(
                                "Manager Name",
                                fontSize = dimens.buttontext,
                                color = Color.Gray
                            )
                        }
                        Box (
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(dimens.logoSize)
                                    .clip(CircleShape),
                                painter = painterResource(R.drawable.mezbaan),
                                contentDescription = null
                            )
                        }
                    }
                    AddHeight(dimens.small1)
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray
                    )
                    AddHeight(100.dp)
                }
            }
        }
    }
}