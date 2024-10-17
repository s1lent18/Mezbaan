package com.example.mezbaan.viewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mezbaan.view.Account
import com.example.mezbaan.view.Login
import com.example.mezbaan.view.Signup
import com.example.mezbaan.view.Home
import com.example.mezbaan.view.Landing
import com.example.mezbaan.view.Venues
import com.example.mezbaan.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {
    val owner = LocalViewModelStoreOwner.current
    val authviewmodel = ViewModelProvider(owner!!)[AuthViewModel::class.java]

    NavHost(navController = navController, startDestination = Screens.Landing.route) {

        this.composable(
            route = Screens.Landing.route
        ) { Landing(navController = navController) }

        this.composable(
            route = Screens.Login.route
        ) { Login(
            navController = navController,
            authviewmodel = authviewmodel
        ) }

        this.composable(
            route = Screens.Signup.route
        ) { Signup(navController = navController) }

        this.composable(
            route = Screens.Home.route
        ) { Home(navController = navController) }

        this.composable(
            route = Screens.Venues.route
        ) { Venues(/*navController = navController*/) }

        this.composable (
            route = Screens.Account.route
        ) {
            Account()
        }

    }
}