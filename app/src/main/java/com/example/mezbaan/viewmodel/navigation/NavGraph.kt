package com.example.mezbaan.viewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mezbaan.view.Account
import com.example.mezbaan.view.Caterers
import com.example.mezbaan.view.Decorators
import com.example.mezbaan.view.Login
import com.example.mezbaan.view.Signup
import com.example.mezbaan.view.Home
import com.example.mezbaan.view.Landing
import com.example.mezbaan.view.Messages
import com.example.mezbaan.view.Vendors
import com.example.mezbaan.view.Venues
import com.example.mezbaan.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    authviewmodel: AuthViewModel = viewModel()
) {
    val user by authviewmodel.user.observeAsState()
    NavHost(
        navController = navController,
        startDestination = if (user == null) Screens.Landing.route else Screens.Home.route
    ) {

        this.composable(
            route = Screens.Landing.route
        ) { Landing(navController = navController) }

        this.composable(
            route = Screens.Login.route
        ) { Login(
            navController = navController,
        ) }

        this.composable(
            route = Screens.Signup.route
        ) { Signup(
            navController = navController,
        ) }

        this.composable(
            route = Screens.Home.route
        ) { Home(navController = navController) }

        this.composable(
            route = Screens.Venues.route
        ) { Venues() }

        this.composable(
            route = Screens.Caterers.route
        ) { Caterers(
            navController = navController
        ) }

        this.composable (
            route = Screens.Account.route
        ) {
            Account(
                navController = navController,
            )
        }

        this.composable(
            route = Screens.Decorators.route
        ) {
            Decorators()
        }

        this.composable(
            route = Screens.Vendors.route
        ) { Vendors() }

        this.composable(
            route = Screens.Msg.route
        ) { Messages(navController = navController) }
    }
}