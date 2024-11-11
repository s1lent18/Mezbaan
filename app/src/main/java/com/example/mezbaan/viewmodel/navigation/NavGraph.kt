package com.example.mezbaan.viewmodel.navigation

import androidx.compose.runtime.Composable
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

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screens.Landing.route) {

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
        ) { Caterers() }

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