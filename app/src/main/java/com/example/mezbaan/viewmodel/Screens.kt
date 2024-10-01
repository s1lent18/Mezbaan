package com.example.mezbaan.viewmodel

sealed class Screens(val route: String) {
    data object Landing: Screens("Landing_Screen")
    data object Login: Screens("Login_Screen")
    data object Signup: Screens("Signup_Screen")
    data object Home: Screens("Home_Screen")
    data object Venues: Screens("Venues_Screen")
}