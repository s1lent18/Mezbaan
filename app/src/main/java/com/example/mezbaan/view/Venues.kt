package com.example.mezbaan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun Cardammedity(text : String) {
    Box (
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = dimens.buttontext, color = Color.Black
        )
    }

}

@Preview
@Composable
fun Venues() {
    Surface {
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
                Image(
                    painter = painterResource(R.drawable.mezbaan),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
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
                            fontWeight = FontWeight.Bold
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
                            Text("Rating of the venue", fontSize = dimens.fontsize)
                        }
                    }
                    AddHeight(dimens.small1)
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray
                    )
                    AddHeight(dimens.small1)
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Business Name",
                                fontSize = dimens.fontsize,
                                fontWeight = FontWeight.Bold
                            )
                            AddHeight(dimens.small1)
                            Text(
                                "Manager Name",
                                fontSize = dimens.fontsize,
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
                    AddHeight(dimens.small1)
                    Text(
                        "Amenities",
                        fontSize = dimens.fontsize,
                        fontWeight = FontWeight.Bold
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimens.cardsize)
                            .padding(vertical = dimens.small2)
                            .clip(RoundedCornerShape(10.dp))
                            .background(backgroundcolor),
                        contentAlignment = Alignment.Center
                    ) {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.9f)
                                .fillMaxHeight(fraction = 0.9f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column (
                                modifier = Modifier.height(dimens.buttonHeight + 10.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    "18-21 Oct - 3 nights",
                                    fontSize = dimens.fontsize,
                                    color = secondarycolor
                                )
                                Text("$384",
                                    fontSize = dimens.fontsize,
                                    color = secondarycolor,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = secondarycolor,
                                    contentColor = backgroundcolor
                                ),
                                modifier = Modifier.height(dimens.buttonHeight)
                            ) {
                                Text("Book Now", fontSize = dimens.buttontext)
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray
                    )
                    AddHeight(dimens.small2)
                }
            }
        }
    }
}