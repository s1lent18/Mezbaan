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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
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
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = MaterialTheme.dimens.fontsize, color = Color.Black
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
            AddHeight(MaterialTheme.dimens.small3)
            Column (
                modifier = Modifier.fillMaxWidth(fraction = 0.9f)
            ) {
                Text(
                    "Name of the venue",
                    fontSize = MaterialTheme.dimens.fontsize,
                    fontWeight = FontWeight.Bold
                )
                AddHeight(MaterialTheme.dimens.small1)
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text("Rating of the venue", fontSize = MaterialTheme.dimens.fontsize)
                }
                AddHeight(MaterialTheme.dimens.small1)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray
                )
                AddHeight(MaterialTheme.dimens.small1)
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Business Name",
                            fontSize = MaterialTheme.dimens.fontsize,
                            fontWeight = FontWeight.Bold
                        )
                        AddHeight(MaterialTheme.dimens.small1)
                        Text(
                            "Manager Name",
                            fontSize = MaterialTheme.dimens.fontsize,
                            color = Color.Gray
                        )
                    }
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        painter = painterResource(R.drawable.mezbaan),
                        contentDescription = null
                    )
                }
                AddHeight(MaterialTheme.dimens.small1)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray
                )
                AddHeight(MaterialTheme.dimens.small1)
                Text(
                    "Amenities",
                    fontSize = MaterialTheme.dimens.fontsize,
                    fontWeight = FontWeight.Bold
                )
                AddHeight(MaterialTheme.dimens.small1)
                FlowRow(
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp,
                ) {
                    Cardammedity("Wi-fi")
                    Cardammedity("Washing Machine")
                    Cardammedity("Washing Machine")
                    Cardammedity("Washing Machine")
                    Cardammedity("Washing Machine")
                    Cardammedity("Washing Machine")
                }
                AddHeight(MaterialTheme.dimens.small3)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray
                )
                //AddHeight(20.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp)
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
                        Column {
                            Text("18-21 Oct - 3 nights", color = secondarycolor)
                            AddHeight(10.dp)
                            Text("$384",
                                fontSize = MaterialTheme.dimens.fontsize,
                                color = secondarycolor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = secondarycolor,
                                contentColor = backgroundcolor
                            )
                        ) {
                            Text("Book Now", fontSize = MaterialTheme.dimens.fontsize,)
                        }
                    }
                }
            }
        }
    }
}