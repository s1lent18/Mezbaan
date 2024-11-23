package com.example.mezbaan.viewmodel.navigation

sealed class Screens(val route: String) {
    data object Msg: Screens("Msg_Screen")
    data object Home: Screens("Home_Screen")
    data object Login: Screens("Login_Screen")
    data object Signup: Screens("Signup_Screen")
    data object Venues: Screens("Venues_Screen/{index}")
    data object Account: Screens("Account_Screen")
    data object Vendors: Screens("Vendors_Screen")
    data object Landing: Screens("Landing_Screen")
    data object Caterers: Screens("Caterers_Screen")
    data object Decorators: Screens("Decorators_Screen/{index}")
}