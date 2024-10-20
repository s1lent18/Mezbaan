@file:Suppress("DEPRECATION")

package com.example.mezbaan.view

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.alterblack
import com.example.mezbaan.ui.theme.backgroundcolor
import com.example.mezbaan.ui.theme.dimens
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.navigation.Screens
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun ImagePickerDialog(
    onDismissRequest: () -> Unit, // Controls dismiss behavior
    showDialog: Boolean,          // State of whether the dialog is shown or not
    onShowDialogChange: (Boolean) -> Unit, // Callback to control the showDialog state
    onImageSelected: (String?) -> Unit, // Callback for when an image is selected
    onCameraSelected: () -> Unit // Callback for when camera is selected
) {
    var selectedImageUri by remember { mutableStateOf<String?>(null) }
    var showCollection by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri.toString()
                onImageSelected(selectedImageUri) // Notify when an image is selected
            }
            onShowDialogChange(false) // Dismiss the dialog after selection
        }
    )

    val sampleImages = listOf(
        "android.resource://your.package.name/drawable/sample1",
        "android.resource://your.package.name/drawable/sample2",
        "android.resource://your.package.name/drawable/sample3",
        "android.resource://your.package.name/drawable/sample4",
        "android.resource://your.package.name/drawable/sample5",
        "android.resource://your.package.name/drawable/sample6"
    )

    // Image Picker Dialog
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Choose Image Source")

                    Spacer(modifier = Modifier.height(8.dp))

                    // Choose from collection
                    Text(
                        text = "Choose from collection",
                        modifier = Modifier.clickable {
                            showCollection = true
                        }.padding(8.dp)
                    )

                    // Choose from gallery
                    Text(
                        text = "Choose from gallery",
                        modifier = Modifier.clickable {
                            galleryLauncher.launch("image/*")
                        }.padding(8.dp)
                    )

                    // Choose from camera
                    Text(
                        text = "Choose from camera",
                        modifier = Modifier.clickable {
                            onShowDialogChange(false)
                            onCameraSelected()
                        }.padding(8.dp)
                    )


                    selectedImageUri?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = rememberImagePainter(it), // Use a library like Coil for image loading
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }

    if (showCollection) {
        Dialog(onDismissRequest = { showCollection = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Choose from Collection")

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(sampleImages.size) { index ->
                            Image(
                                painter = painterResource(id = index),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(4.dp)
                                    .clickable {
                                        selectedImageUri = sampleImages[index]
                                        onImageSelected(selectedImageUri)
                                        showCollection = false
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
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
    authviewmodel : AuthViewModel = viewModel()
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
        val icon = if (passwordvisibility) painterResource(id = R.drawable.eye) else painterResource(id = R.drawable.lock)
        var showDialog by remember { mutableStateOf(false) }
        var camera by remember { mutableStateOf(false) }

        val launcher = rememberfirebaselauncher(
            onAuthComplete = { result ->
                authviewmodel.setUser(result)
            },
            onAuthError = {
                authviewmodel.setUser(null)
            }
        )

        ImagePickerDialog(
            onDismissRequest = { showDialog = false },
            showDialog = showDialog,
            onShowDialogChange = { showDialog = it },
            onCameraSelected = {
                camera = true
            },
            onImageSelected =  {
                //selectedImageUri = uri
            },
        )
        if (camera) {
            Camera(onDismiss = {camera = false})
        } else {
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
                    val (headingtext, welcometext, usernameinput, passwordinput, dpoption,
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

                    Row(
                        modifier = Modifier
                            .clickable {
                                showDialog = !showDialog
                            }
                            .constrainAs(dpoption) {
                                top.linkTo(passwordinput.bottom, margin = 20.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.percent(0.8f)
                            },

                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Add Display Picture",
                            fontSize = dimens.buttontext,
                        )
                        AddWidth(10.dp)
                        Icon(
                            painter = painterResource(R.drawable.servicevendor),
                            contentDescription = null
                        )
                    }

                    Button(
                        onClick = {
                            navController.navigate(route = Screens.Home.route)
                        },
                        modifier = Modifier
                            .constrainAs(registerbutton) {
                                top.linkTo(dpoption.bottom, margin = 20.dp)
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
                }
            }
        }
    }
}