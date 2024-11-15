@file:Suppress("DEPRECATION")

package com.example.mezbaan.view

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mezbaan.R
import com.example.mezbaan.model.dataclasses.LoginReq
import com.example.mezbaan.model.response.NetworkResponse
import com.example.mezbaan.ui.theme.alterblack
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.LoginViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AddHeight(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun AddWidth(weight: Dp) {
    Spacer(modifier = Modifier.width(weight))
}

@Composable
fun rememberfirebaselauncher(
    onAuthComplete: (FirebaseUser?) -> Unit,
    onAuthError: (ApiException) -> Unit
) : ManagedActivityResultLauncher<Intent, ActivityResult> {

    val scope = rememberCoroutineScope()

    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = getSignedInAccountFromIntent(result.data)
        scope.launch {
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                onAuthComplete(authResult.user)
            } catch (e: ApiException) {
                onAuthError(e)
            } catch (_: Exception) {

            }
        }
    }
}

@Composable
fun CustomFacebookSignInButton(
    onSignInFailed: (Exception) -> Unit,
    onSignedIn: () -> Unit
) {
    val context = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val callbackManager = remember { CallbackManager.Factory.create() }


    val facebookLogin = {
        val loginManager = LoginManager.getInstance()
        loginManager.logInWithReadPermissions(context, listOf("public_profile"))

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {
                onSignInFailed(error)
            }

            override fun onSuccess(result: LoginResult) {
                scope.launch {
                    try {
                        val token = result.accessToken.token
                        val credential = FacebookAuthProvider.getCredential(token)
                        val authResult = Firebase.auth.signInWithCredential(credential).await()
                        if (authResult.user != null) {
                            onSignedIn()
                        } else {
                            onSignInFailed(RuntimeException("User authentication failed"))
                        }
                    } catch (e: Exception) {
                        onSignInFailed(e)
                    }
                }
            }
        })
    }


    OutlinedIconButton(
        onClick = {
            facebookLogin()
        },
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
}


@Composable
fun Login(
    navController: NavController,
    authviewmodel: AuthViewModel = viewModel(),
    loginviewmodel: LoginViewModel = viewModel()
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val (username, setUsername) = remember { mutableStateOf("") }
        val (password, setPassword) = remember { mutableStateOf("") }
        var passwordvisibility by remember { mutableStateOf(false) }
        val color = if (isSystemInDarkTheme()) alterblack else Color.White
        val token = stringResource(R.string.client_id)
        val user by authviewmodel.user.observeAsState()
        val context = LocalContext.current
        val icon = if (passwordvisibility) painterResource(id = R.drawable.eye) else painterResource(id = R.drawable.lock)
        var clicked by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val loginresult = loginviewmodel.loginresult.observeAsState()
        var requestreceived by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }

        LaunchedEffect (clicked) {
            if(clicked) {
                val loginrequest = LoginReq(username, password)
                loginviewmodel.login(loginrequest)
                clicked = false
                requestreceived = true
            }
        }

        val launcher = rememberfirebaselauncher(
            onAuthComplete = { result ->
                authviewmodel.setUser(result)
            },
            onAuthError = {
                authviewmodel.setUser(null)
            }
        )

        if (user == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 80.dp, bottom = 40.dp)
                ) {
                    val (greeting, welcomeBack, inputUsername, inputPassword, recoverPassword, signInButton,
                        dividerRow, socialRow, registerRow) = createRefs()

                    Text(
                        "Hello Again!",
                        fontSize = dimens.heading,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.constrainAs(greeting) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )

                    Text(
                        "Welcome Back you've been missed!",
                        fontSize = dimens.fontsize,
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.constrainAs(welcomeBack) {
                            top.linkTo(greeting.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.8f)
                        }
                    )

                    Input(
                        label = "Enter Username",
                        value = username,
                        onValueChange = setUsername,
                        color = color,
                        modifier = Modifier.constrainAs(inputUsername) {
                            top.linkTo(welcomeBack.bottom, margin = 40.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.8f)
                        }
                    )

                    Input(
                        label = "Password",
                        value = password,
                        onValueChange = setPassword,
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
                        modifier = Modifier.constrainAs(inputPassword) {
                            top.linkTo(inputUsername.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.8f)
                        }
                    )

                    Row(
                        modifier = Modifier.constrainAs(recoverPassword) {
                            top.linkTo(inputPassword.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.8f)
                        },
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Recover Password",
                            fontSize = dimens.buttontext,
                        )
                    }

                    if (!isLoading ) {
                        Button(
                            onClick = {
                                if (username.isNotEmpty() && password.isNotEmpty()) {
                                    clicked = true
                                    keyboardController?.hide()
                                }
                            },
                            modifier = Modifier
                                .constrainAs(signInButton) {
                                    top.linkTo(recoverPassword.bottom, margin = 20.dp)
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
                                "Sign In",
                                fontSize = dimens.buttontext
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                            .constrainAs(signInButton) {
                                top.linkTo(recoverPassword.bottom, margin = 20.dp)
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
                        modifier = Modifier.constrainAs(dividerRow) {
                            top.linkTo(signInButton.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.8f)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Gray)
                        Spacer(modifier = Modifier.width(20.dp))
                        Text("Or Continue With", fontSize = dimens.fontsize)
                        Spacer(modifier = Modifier.width(20.dp))
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Gray)
                    }

                    Row(
                        modifier = Modifier.constrainAs(socialRow) {
                            top.linkTo(dividerRow.bottom, margin = 40.dp)
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
                        modifier = Modifier.constrainAs(registerRow) {
                            top.linkTo(socialRow.bottom, margin = 50.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Not a Member? ", fontSize = dimens.fontsize)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            "Register now",
                            color = backgroundcolor,
                            modifier = Modifier.clickable {
                                navController.navigate(route = Screens.Signup.route)
                            },
                            fontSize = dimens.fontsize
                        )
                    }
                }
                AddHeight(40.dp)
                if(username.isNotEmpty() && password.isNotEmpty() && requestreceived) {
                    when (val request = loginresult.value) {
                        is NetworkResponse.Failure -> isLoading = false
                        NetworkResponse.Loading -> isLoading = true
                        is NetworkResponse.Success -> {
                            isLoading = false
                            if (request.data.result) {
                                navController.navigate(route = Screens.Home.route)
                            }
                        }
                        null -> { }
                    }
                }
            }
        } else {
            LaunchedEffect(Unit) {
                navController.navigate(route = Screens.Home.route) {
                    popUpTo(Screens.Landing.route) { inclusive = true }
                }
            }
        }
    }
}