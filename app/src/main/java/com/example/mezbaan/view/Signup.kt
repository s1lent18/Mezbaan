package com.example.mezbaan.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun Input(
    label : String,
    value : String,
    onValueChange: (String) -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
    color: Color
    ) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.8f),
        label = {
            Text(
                label,

            )
        },
        value = value,
        onValueChange = onValueChange,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = color,
            unfocusedContainerColor = color,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Gray
        ),
        shape = RoundedCornerShape(10.dp),
        textStyle = TextStyle(
            fontSize = MaterialTheme.dimens.fontsize
        )
    )
}

@Composable
fun Signup(
    //navController: NavController
) {
    Surface {
        val (email, setemail) = remember { mutableStateOf("") }
        val (phonenumber, setphonenumber) = remember { mutableStateOf("") }
        val (username, setusername) = remember { mutableStateOf("") }
        val (password, setpassword) = remember { mutableStateOf("") }
        val color = if(isSystemInDarkTheme()) alterblack else Color.White

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AddHeight(MaterialTheme.dimens.medium1)
                Text(
                    "Welcome to Mezbaan!",
                    fontSize = MaterialTheme.dimens.heading,
                    fontWeight = FontWeight.Bold
                )
                AddHeight(MaterialTheme.dimens.small3)
                Row (
                    modifier = Modifier.fillMaxWidth(fraction = 0.6f)
                ) { Text(
                    "Welcome Back you've been missed!",
                    fontSize = MaterialTheme.dimens.fontsize,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ) }

                AddHeight(MaterialTheme.dimens.medium3)
                Input(
                    label = "Enter Username",
                    value = username,
                    onValueChange = setusername,
                    color = color
                )
                AddHeight(MaterialTheme.dimens.small3)
                Input(
                    label = "Phone Number",
                    value = phonenumber,
                    onValueChange = setphonenumber,
                    color = color
                )
                AddHeight(MaterialTheme.dimens.small3)
                Input(
                    label = "Email",
                    value = email,
                    onValueChange = setemail,
                    color = color
                )

                AddHeight(MaterialTheme.dimens.small3)
                Input(
                    label = "Password",
                    value = password,
                    onValueChange = setpassword,
                    trailingIcon = { Icon(
                        painter = painterResource(R.drawable.lock),
                        contentDescription = null
                    ) },
                    color = color
                )

                AddHeight(MaterialTheme.dimens.medium1)

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.8f)
                        .height(MaterialTheme.dimens.buttonHeight),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundcolor,
                        contentColor = secondarycolor
                    )
                ) {
                    Text(
                        "Register",
                        fontSize = MaterialTheme.dimens.buttontext
                    )
                }
                AddHeight(MaterialTheme.dimens.medium1)
                Row (
                    modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                    AddWidth(20.dp)
                    Text(
                        "Or Sign Up With",
                        fontSize = MaterialTheme.dimens.fontsize
                    )
                    AddWidth(20.dp)
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                }
                AddHeight(MaterialTheme.dimens.medium1)
                Row(
                    modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedIconButton (
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(MaterialTheme.dimens.buttonHeight + 10.dp)
                            .width(MaterialTheme.dimens.buttonWidth)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.facebook),
                            contentDescription = "Facebook Logo",
                            tint = Color(0xFF5890FF)
                        )

                    }
                    OutlinedIconButton (
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(MaterialTheme.dimens.buttonHeight + 10.dp)
                            .width(MaterialTheme.dimens.buttonWidth)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.mail),
                            contentDescription = "Mail Logo"
                        )

                    }
                }
                AddHeight(MaterialTheme.dimens.medium3)
                Row(
                    modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Already a Member? ",
                        fontSize = MaterialTheme.dimens.fontsize
                    )
                    AddWidth(5.dp)
                    Text(
                        "Login now",
                        fontSize = MaterialTheme.dimens.fontsize,
                        color = backgroundcolor,
                        modifier = Modifier.clickable {
                            //navController.navigate(route = Screens.Signup.route)
                        }
                    )
                }
                AddHeight(MaterialTheme.dimens.small3)
            }
        }
    }
}