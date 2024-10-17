package com.example.mezbaan.model.dataprovider

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationBarItems(val text: String, val icon: ImageVector) {
    Home(text = "Home", icon = Icons.Default.Home),
    Account(text = "Account", icon = Icons.Default.AccountCircle),
    Logout(text = "Logout", icon = Icons.AutoMirrored.Filled.ArrowBack),
}