package com.example.mezbaan.view

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.alterblack
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.google.android.gms.auth.api.signin.GoogleSignIn.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
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
    onAuthComplete: (AuthResult) -> Unit,
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
                onAuthComplete(authResult)
            } catch (e: ApiException) {
                onAuthError(e)
            } catch (_: Exception) {

            }
        }
    }
}

@Composable
fun Login(
    navController: NavController,
    authviewmodel: AuthViewModel
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val (username, setUsername) = remember { mutableStateOf("") }
        val (password, setPassword) = remember { mutableStateOf("") }
        val color = if (isSystemInDarkTheme()) alterblack else Color.White
        val token = stringResource(R.string.client_id)
        val user by authviewmodel.user.observeAsState()
        val context = LocalContext.current

        val launcher = rememberfirebaselauncher(
            onAuthComplete = { result ->
                authviewmodel.setUser(result.user)
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

                    // Welcome back text
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

                    // Username input field
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

                    // Password input field
                    Input(
                        label = "Password",
                        value = password,
                        onValueChange = setPassword,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.lock),
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        },
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

                    Button(
                        onClick = {
                            navController.navigate(route = Screens.Home.route)
                        },
                        modifier = Modifier.constrainAs(signInButton) {
                            top.linkTo(recoverPassword.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.percent(0.8f)
                        }.height(dimens.buttonHeight),
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

                    // Divider row with "Or Continue With" text
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

                    // Social media login buttons (Facebook & Email)
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
                        OutlinedIconButton(
                            onClick = {

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

                    // Register row
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