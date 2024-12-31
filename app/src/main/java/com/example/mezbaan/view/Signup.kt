@file:Suppress("DEPRECATION")

package com.example.mezbaan.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mezbaan.R
import com.example.mezbaan.model.requests.SignUpReq
import com.example.mezbaan.model.response.NetworkResponse
import com.example.mezbaan.ui.theme.alterblack
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.SignupViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.delay

fun validateEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()
    return email.matches(emailRegex)
}

@Composable
fun Input(
    label : String,
    value : String,
    onValueChange: (String) -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
    color: Color,
    visualTransformation: VisualTransformation = VisualTransformation.None,
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
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
    navController: NavController,
    authviewmodel : AuthViewModel = viewModel(),
    signupviewmodel: SignupViewModel = viewModel()
) {
    Surface {

        val (email, setemail) = remember { mutableStateOf("") }
        val (phonenumber, setphonenumber) = remember { mutableStateOf("") }
        var passwordvisibility by remember { mutableStateOf(false) }
        val (username, setusername) = remember { mutableStateOf("") }
        val (password, setpassword) = remember { mutableStateOf("") }
        val color = if(isSystemInDarkTheme()) alterblack else Color.White
        val token = stringResource(R.string.client_id)
        val context = LocalContext.current
        val signupresult = signupviewmodel.signupresult.observeAsState()
        var requestreceived by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }
        val icon = if (passwordvisibility) painterResource(id = R.drawable.eye) else painterResource(id = R.drawable.lock)
        var clicked by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val phonevalid = phonenumber.matches(Regex("^[0-9]+$"))

        val launcher = rememberfirebaselauncher(
            onAuthComplete = { result ->
                authviewmodel.setUser(result)
            },
            onAuthError = {
                authviewmodel.setUser(null)
            }
        )

        LaunchedEffect (clicked) {
            if(clicked) {
                val signuprequest = SignUpReq(
                    username = username,
                    password = password,
                    email = email,
                    phone = phonenumber
                )
                signupviewmodel.signup(signuprequest)
                clicked = false
                requestreceived = true
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, bottom = 60.dp)
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
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordvisibility = !passwordvisibility
                            }
                        ) {
                            Icon(
                                painter = icon,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    },
                    visualTransformation = if (passwordvisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    color = color,
                    modifier = Modifier
                        .constrainAs(passwordinput) {
                            top.linkTo(emailinput.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },

                )

                if (!isLoading) {
                    Button(
                        onClick = {
                            if (
                                username.isNotEmpty() &&
                                password.isNotEmpty() &&
                                password.length >= 8 &&
                                phonenumber.isNotEmpty() &&
                                phonenumber.length == 11 &&
                                email.isNotEmpty() &&
                                phonevalid &&
                                validateEmail(email)
                            ) {
                                clicked = true
                                keyboardController?.hide()
                            }
                            else if (username.isEmpty()) {
                                Toast.makeText(context, "Enter username", Toast.LENGTH_SHORT).show()
                            }
                            else if (password.isEmpty()) {
                                Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show()
                            }
                            else if (email.isEmpty()) {
                                Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show()
                            }
                            else if (phonenumber.isEmpty()) {
                                Toast.makeText(context, "Enter phonenumber", Toast.LENGTH_SHORT).show()
                            }
                            else if (password.length < 8) {
                                Toast.makeText(context, "Small Length of password", Toast.LENGTH_LONG).show()
                            }
                            else if (phonenumber.length < 11) {
                                Toast.makeText(context, "Small Length Of phone number", Toast.LENGTH_LONG).show()
                            }
                            else if (!phonevalid) {
                                Toast.makeText(context, "Phone number should only contain numbers", Toast.LENGTH_LONG).show()
                            }
                            else if (!validateEmail(email)) {
                                Toast.makeText(context, "Invalid Format", Toast.LENGTH_LONG).show()
                            }
                        },
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
                }
                else {
                    Box(
                        modifier = Modifier
                            .constrainAs(registerbutton) {
                                top.linkTo(passwordinput.bottom, margin = 30.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.percent(0.8f)
                            }
                            .height(dimens.buttonHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    }
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
                    CustomFacebookSignInButton(
                        onSignedIn = {
                            navController.navigate(route = Screens.Home.route)
                        },
                        onSignInFailed = {

                        }
                    )

                    OutlinedIconButton(
                        onClick = {
                            val gso = GoogleSignInOptions
                                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()

                            val googleSignInClient = getClient(context, gso)
                            launcher.launch(googleSignInClient.signInIntent)
                        },
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

                if(
                    username.isNotEmpty() &&
                    password.isNotEmpty() &&
                    email.isNotEmpty() &&
                    phonenumber.isNotEmpty() &&
                    requestreceived
                ) {
                    when (signupresult.value) {
                        is NetworkResponse.Failure -> {
                            isLoading = false
                            Toast.makeText(context, "User Already Exists", Toast.LENGTH_LONG).show()
                        }
                        NetworkResponse.Loading -> {
                            isLoading = true
                        }
                        is NetworkResponse.Success -> {
                            isLoading = false
                            LaunchedEffect(Unit) {
                                Toast.makeText(context, "Account created successfully!", Toast.LENGTH_LONG).show()
                                delay(3000)
                                navController.navigate(route = Screens.Login.route)
                            }
                        }
                        null -> { }
                    }
                }
            }
        }
    }
}