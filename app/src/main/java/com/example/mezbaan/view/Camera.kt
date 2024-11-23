package com.example.mezbaan.view


import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mezbaan.MainActivity
import com.example.mezbaan.R
import com.example.mezbaan.ui.theme.secondarycolor
import com.example.mezbaan.viewmodel.CameraViewModel
import com.example.mezbaan.viewmodel.UserViewModel

@Composable
fun Camera(
    onDismiss: () -> Unit,
    userviewmodel: UserViewModel,
    cameraviewmodel: CameraViewModel = viewModel()
) {
    val c1 = LocalContext.current as MainActivity
    val capturedBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current.applicationContext
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (capturedBitmap.value == null) {
            CameraPreview(
                controller = controller,
                modifier = Modifier.fillMaxSize()
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(46.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(secondarycolor),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = {
                        controller.cameraSelector = if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else {
                            CameraSelector.DEFAULT_BACK_CAMERA
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Cameraswitch,
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = {
                        c1.takePhoto(
                            controller = controller,
                            onPhotoTaken = { bitmap ->
                                capturedBitmap.value = bitmap
                            }
                        )
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = { onDismiss() },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.servicevendor),
                        contentDescription = null
                    )
                }
            }
        }
         else {
            Image(
                bitmap = capturedBitmap.value!!.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { capturedBitmap.value = null }) {
                    Text("Retake Photo")
                }
                Button(onClick = {
                    capturedBitmap.value?.let { bitmap ->
                        val token = "Bearer " + userviewmodel.token.value
                        Log.d("CameraViewModel", token)
                        if (token.isNotEmpty()) {
                            Log.d("CameraViewModel", "called")
                            cameraviewmodel.uploadimage(
                                image = bitmap,
                                tooken = token,
                                onSuccess = { imageUrl ->
                                    Log.d("CameraViewModel", "success")
                                    userviewmodel.saveImage(imageUrl)
                                }
                            )
                        }
                    }
                    onDismiss()
                }) {
                    Text("Done")
                }
            }
        }
    }
}