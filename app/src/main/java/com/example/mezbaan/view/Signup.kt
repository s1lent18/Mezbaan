package com.example.mezbaan.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
    color: Color,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
    ) {
    TextField(
        modifier = modifier
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
            fontSize = dimens.fontsize
        )
    )
}

@Composable
fun Signup(
    navController: NavController
) {
    Surface {
        val (email, setemail) = remember { mutableStateOf("") }
        val (phonenumber, setphonenumber) = remember { mutableStateOf("") }
        val (username, setusername) = remember { mutableStateOf("") }
        val (password, setpassword) = remember { mutableStateOf("") }
        val color = if(isSystemInDarkTheme()) alterblack else Color.White

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, bottom = 40.dp)
            ) {
                val (headingtext, welcometext, usernameinput, passwordinput,
                    emailinput, phoneinput, registerbutton, dividerrow, socialrow, change) = createRefs()

                Text(
                    "Welcome to Mezbaan!",
                    fontSize = dimens.heading,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(headingtext) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Row (
                    modifier = Modifier
                        .constrainAs(welcometext) {
                            top.linkTo(headingtext.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.6f)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                    "Welcome Back you've been missed!",
                    fontSize = dimens.fontsize,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                    )
                }
                Input(
                    label = "Enter Username",
                    value = username,
                    onValueChange = setusername,
                    color = color,
                    modifier = Modifier.constrainAs(usernameinput) {
                        top.linkTo(welcometext.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Input(
                    label = "Phone Number",
                    value = phonenumber,
                    onValueChange = setphonenumber,
                    color = color,
                    modifier = Modifier.constrainAs(phoneinput) {
                        top.linkTo(usernameinput.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Input(
                    label = "Email",
                    value = email,
                    onValueChange = setemail,
                    color = color,
                    modifier = Modifier.constrainAs(emailinput) {
                        top.linkTo(phoneinput.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Input(
                    label = "Password",
                    value = password,
                    onValueChange = setpassword,
                    trailingIcon = { Icon(
                        painter = painterResource(R.drawable.lock),
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    ) },
                    color = color,
                    modifier = Modifier
                        .constrainAs(passwordinput) {
                        top.linkTo(emailinput.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },

                )
                Button(
                    onClick = {},
                    modifier = Modifier
                        .constrainAs(registerbutton) {
                            top.linkTo(passwordinput.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.8f)
                        }
                        .height(dimens.buttonHeight),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundcolor,
                        contentColor = secondarycolor
                    )
                ) {
                    Text(
                        "Register",
                        fontSize = dimens.buttontext
                    )
                }
                Row(
                    modifier = Modifier.constrainAs(dividerrow) {
                        top.linkTo(registerbutton.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.percent(0.8f)
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Gray)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text("Or Sign up With", fontSize = dimens.fontsize)
                    Spacer(modifier = Modifier.width(20.dp))
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Gray)
                }
                Row(
                    modifier = Modifier.constrainAs(socialrow) {
                        top.linkTo(dividerrow.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.percent(0.8f)
                    },
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedIconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(dimens.buttonWidth + 5.dp, dimens.buttonHeight + 20.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.facebook),
                            contentDescription = "Facebook Logo",
                            tint = Color(0xFF5890FF),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    OutlinedIconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(dimens.buttonWidth + 5.dp, dimens.buttonHeight + 20.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.mail),
                            contentDescription = "Mail Logo",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .constrainAs(change) {
                            top.linkTo(socialrow.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.8f)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Already a Member? ",
                        fontSize = dimens.fontsize
                    )
                    AddWidth(5.dp)
                    Text(
                        "Login now",
                        fontSize = dimens.fontsize,
                        color = backgroundcolor,
                        modifier = Modifier.clickable {
                            navController.navigate(route = Screens.Login.route)
                        }
                    )
                }
            }
        }
    }
}