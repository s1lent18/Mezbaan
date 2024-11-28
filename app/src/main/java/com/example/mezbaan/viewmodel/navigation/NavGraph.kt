package com.example.mezbaan.viewmodel.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mezbaan.model.models.Data
import com.example.mezbaan.view.Account
import com.example.mezbaan.view.Caterers
import com.example.mezbaan.view.Decorators
import com.example.mezbaan.view.Login
import com.example.mezbaan.view.Signup
import com.example.mezbaan.view.Home
import com.example.mezbaan.view.Landing
import com.example.mezbaan.view.Messages
import com.example.mezbaan.view.OtherServices
import com.example.mezbaan.view.Photographers
import com.example.mezbaan.view.Venues
import com.example.mezbaan.viewmodel.AuthViewModel
import com.example.mezbaan.viewmodel.CateringViewModel
import com.example.mezbaan.viewmodel.DecoratorViewModel
import com.example.mezbaan.viewmodel.OtherServicesViewModel
import com.example.mezbaan.viewmodel.PhotographerViewModel
import com.example.mezbaan.viewmodel.UserViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    authviewmodel: AuthViewModel = viewModel(),
    venues: List<Data>
) {
    val user by authviewmodel.user.observeAsState()
    val userviewmodel : UserViewModel = viewModel()
    val token by userviewmodel.token.collectAsState()
    val cateringviewmodel = hiltViewModel<CateringViewModel>()
    val decoratorviewmodel = hiltViewModel<DecoratorViewModel>()
    val photographerviewmodel = hiltViewModel<PhotographerViewModel>()
    val otherservicesviewmodel = hiltViewModel<OtherServicesViewModel>()
    val catering by cateringviewmodel.menu.collectAsStateWithLifecycle()
    val startDestination = remember { mutableStateOf<String?>(null) }
    val decorators by decoratorviewmodel.decorators.collectAsStateWithLifecycle()
    val otherservices by otherservicesviewmodel.vendors.collectAsStateWithLifecycle()
    val photographers by photographerviewmodel.photographers.collectAsStateWithLifecycle()

    LaunchedEffect(token) {
        if (user == null && token.isNotEmpty()) {
            startDestination.value = Screens.Home.route
        } else {
            startDestination.value = Screens.Landing.route
        }
    }

    if (startDestination.value != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination.value!!
        ) {

            this.composable(
                route = Screens.Landing.route
            ) { Landing(navController = navController) }

            this.composable(
                route = Screens.Login.route
            ) { Login(
                navController = navController,
                userviewmodel = userviewmodel
            ) }

            this.composable(
                route = Screens.Signup.route
            ) { Signup(
                navController = navController,
            ) }

            this.composable(
                route = Screens.Home.route
            ) { Home(
                navController = navController,
                venues = venues
            ) }

            this.composable (
                route = Screens.Account.route
            ) {
                Account(
                    navController = navController,
                    userviewmodel = userviewmodel,
                )
            }

            this.composable(
                route = Screens.Caterers.route,
                arguments = listOf(
                    navArgument("index") {
                        type = NavType.IntType
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                val cateringId = backStackEntry.arguments?.getInt("index") ?: -1
                val menu = catering.find { it.id == cateringId }
                Log.d("navgraph", "$cateringId")
                if (menu != null) {
                    Caterers(
                        menu = menu,
                        userviewmodel = userviewmodel,
                        navController = navController
                    )
                }
            }

            this.composable(
                route = Screens.Venues.route,
                arguments = listOf(
                    navArgument("index") {
                        type = NavType.IntType
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                val venueId = backStackEntry.arguments?.getInt("index") ?: -1
                val venue = venues.find { it.id == venueId }
                if (venue != null) {
                    Venues(
                        userviewmodel = userviewmodel,
                        venue = venue,
                        navController = navController
                    )
                }
            }

            this.composable(
                route = Screens.Decorators.route,
                arguments = listOf(
                    navArgument("index") {
                        type = NavType.IntType
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                val decoratorId = backStackEntry.arguments?.getInt("index") ?: -1
                Log.d("Decorators", " Decorators $decoratorId")
                val decorator = decorators.find { it.id == decoratorId }
                Log.d("Decorators", " Decorators ${decorators.size}")
                if (decorator != null) {
                    Log.d("Decorators", " Decorators ${decorator.name}")
                    Decorators(
                        decorator = decorator,
                        userviewmodel = userviewmodel,
                        navController = navController
                    )
                } else {
                    Log.d("Decorators", " No Data")
                }
            }

            this.composable(
                route = Screens.Photographers.route,
                arguments = listOf(
                    navArgument("index") {
                        type = NavType.IntType
                        nullable = false
                    }
                )
            )
            { backStackEntry ->
                val photographerId = backStackEntry.arguments?.getInt("index") ?: -1
                val photographer = photographers.find { it.id == photographerId }
                if (photographer != null) {
                    Photographers(
                        userviewmodel = userviewmodel,
                        id = photographerId,
                        navController = navController
                    )
                }
            }

            this.composable(
                route = Screens.OtherServices.route,
                arguments = listOf(
                    navArgument("index") {
                        type = NavType.IntType
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                val vendorId = backStackEntry.arguments?.getInt("index") ?: -1
                val vendor = otherservices.find { it.id == vendorId }
                if (vendor != null) {
                    OtherServices(
                        navController = navController,
                        vendor = vendor,
                        userviewmodel = userviewmodel
                    )
                }
            }

            this.composable(
                route = Screens.Msg.route
            ) { Messages(navController = navController) }
        }
    }
}