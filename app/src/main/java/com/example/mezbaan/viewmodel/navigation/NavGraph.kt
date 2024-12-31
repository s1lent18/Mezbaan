package com.example.mezbaan.viewmodel.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mezbaan.model.models.BookedAmenity
import com.example.mezbaan.model.models.BookedMenuItems
import com.example.mezbaan.model.models.BookedPackage
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
import com.example.mezbaan.viewmodel.CateringViewModel
import com.example.mezbaan.viewmodel.DecoratorViewModel
import com.example.mezbaan.viewmodel.OtherServicesViewModel
import com.example.mezbaan.viewmodel.PhotographerViewModel
import com.example.mezbaan.viewmodel.UserViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    venues: List<Data>
) {
    val userviewmodel : UserViewModel = viewModel()
    val cateringviewmodel = hiltViewModel<CateringViewModel>()
    val decoratorviewmodel = hiltViewModel<DecoratorViewModel>()
    val photographerviewmodel = hiltViewModel<PhotographerViewModel>()
    val otherservicesviewmodel = hiltViewModel<OtherServicesViewModel>()
    val catering by cateringviewmodel.menu.collectAsStateWithLifecycle()
    val decorators by decoratorviewmodel.decorators.collectAsStateWithLifecycle()
    val otherservices by otherservicesviewmodel.vendors.collectAsStateWithLifecycle()
    val photographers by photographerviewmodel.photographers.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = startDestination
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
                },
                navArgument("startTime") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("guestCount") {
                    type = NavType.FloatType
                    defaultValue = 50.0f
                    nullable = false
                },
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("bill") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument("address") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("edit") {
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                },
                navArgument("bookingId") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("bookedPackages") {
                    type = NavType.StringType
                    defaultValue = "{}"
                    nullable = true
                },
                navArgument("bookedMenuItems") {
                    type = NavType.StringType
                    defaultValue = "{}"
                    nullable = true
                },
            )
        ) { backStackEntry ->
            val cateringId = backStackEntry.arguments?.getInt("index") ?: -1
            val menu = catering.find { it.id == cateringId }
            val guestCount = backStackEntry.arguments?.getFloat("guestCount") ?: 50.0f
            val startTime = backStackEntry.arguments?.getString("startTime").orEmpty()
            val date = backStackEntry.arguments?.getString("date").orEmpty()
            val bill = backStackEntry.arguments?.getString("bill").orEmpty()
            val address = backStackEntry.arguments?.getString("address").orEmpty()
            val edit = backStackEntry.arguments?.getBoolean("edit") ?: false
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: -1
            val bookedPackagesJson = backStackEntry.arguments?.getString("bookedPackages").orEmpty()
            val bookedMenuItemsJson = backStackEntry.arguments?.getString("bookedMenuItems").orEmpty()

            val bookedPackages = remember {
                try {
                    val type = object : TypeToken<List<BookedPackage>>() {}.type
                    Gson().fromJson<List<BookedPackage>>(bookedPackagesJson, type)
                } catch (e: Exception) {
                    emptyList()
                }
            }

            val bookedMenuItems = remember {
                try {
                    val type = object : TypeToken<List<BookedMenuItems>>() {}.type
                    Gson().fromJson<List<BookedMenuItems>>(bookedMenuItemsJson, type)
                } catch (e: Exception) {
                    emptyList()
                }
            }

            if (menu != null) {
                Caterers(
                    menu = menu,
                    userviewmodel = userviewmodel,
                    navController = navController,
                    startTime = startTime,
                    bill = bill,
                    bookingId = bookingId,
                    bookedPackage = bookedPackages,
                    bookedMenuItems = bookedMenuItems,
                    date = date,
                    edit = edit,
                    addresss = address
                )
            }
        }

        this.composable(
            route = Screens.Venues.route,
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("startTime") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("guestCount") {
                    type = NavType.FloatType
                    defaultValue = 50.0f
                    nullable = false
                },
                navArgument("endTime") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("bill") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument("edit") {
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                },
                navArgument("bookingId") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
            )
        ) { backStackEntry ->
            val venueId = backStackEntry.arguments?.getInt("index") ?: -1
            val venue = venues.find { it.id == venueId }
            val guestCount = backStackEntry.arguments?.getFloat("guestCount") ?: 50.0f
            val startTime = backStackEntry.arguments?.getString("startTime").orEmpty()
            val endTime = backStackEntry.arguments?.getString("endTime").orEmpty()
            val date = backStackEntry.arguments?.getString("date").orEmpty()
            val bill = backStackEntry.arguments?.getString("bill").orEmpty()
            val edit = backStackEntry.arguments?.getBoolean("edit") ?: false
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: -1
            if (venue != null) {
                Venues(
                    userviewmodel = userviewmodel,
                    venuee = venueId,
                    navController = navController,
                    startTime = startTime,
                    endTime = endTime,
                    date = date,
                    edit = edit,
                    bookingId = bookingId,
                    bill = bill,
                    guestCount = guestCount
                )
            }
        }

        this.composable(
            route = Screens.Decorators.route,
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("startTime") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("endTime") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("address") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("bill") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument("amenities") {
                    type = NavType.StringType
                    defaultValue = "{}"
                    nullable = true
                },
                navArgument("edit") {
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                },
                navArgument("bookingId") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
            )
        ) { backStackEntry ->
            val decoratorId = backStackEntry.arguments?.getInt("index") ?: -1
            Log.d("Decorators", " Decorators $decoratorId")
            val decorator = decorators.find { it.id == decoratorId }
            val startTime = backStackEntry.arguments?.getString("startTime").orEmpty()
            val endTime = backStackEntry.arguments?.getString("endTime").orEmpty()
            val address = backStackEntry.arguments?.getString("address").orEmpty()
            val date = backStackEntry.arguments?.getString("date").orEmpty()
            val bill = backStackEntry.arguments?.getString("bill").orEmpty()
            val amenitiesJson = backStackEntry.arguments?.getString("amenities").orEmpty()
            val edit = backStackEntry.arguments?.getBoolean("edit") ?: false
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: -1

            Log.d("Decorators", " Decorators ${startTime}, $endTime ${address}, ${date}, ${bill}, $amenitiesJson ")

            val amenities = remember {
                try {
                    val type = object : TypeToken<List<BookedAmenity>>() {}.type
                    Gson().fromJson<List<BookedAmenity>>(amenitiesJson, type)
                } catch (e: Exception) {
                    emptyList()
                }
            }
            if (decorator != null) {
                Log.d("Decorators", " Decorators ${decorator.name}")
                Decorators(
                    decoratorr = decoratorId,
                    userviewmodel = userviewmodel,
                    navController = navController,
                    startTime = startTime,
                    endTime = endTime,
                    addresss = address,
                    bill = bill,
                    date = date,
                    booked = amenities,
                    edit = edit,
                    bookingId = bookingId
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
                },
                navArgument("startTime") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("endTime") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("address") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("bill") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument("eventtype") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("edit") {
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                },
                navArgument("bookingId") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
            )
        )
        { backStackEntry ->
            val photographerId = backStackEntry.arguments?.getInt("index") ?: -1
            val photographer = photographers.find { it.id == photographerId }
            val startTime = backStackEntry.arguments?.getString("startTime").orEmpty()
            val endTime = backStackEntry.arguments?.getString("endTime").orEmpty()
            val address = backStackEntry.arguments?.getString("address").orEmpty()
            val date = backStackEntry.arguments?.getString("date").orEmpty()
            val bill = backStackEntry.arguments?.getString("bill").orEmpty()
            val eventtype = backStackEntry.arguments?.getString("eventtype").orEmpty()
            val edit = backStackEntry.arguments?.getBoolean("edit") ?: false
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: -1
            if (photographer != null) {
                Photographers(
                    userviewmodel = userviewmodel,
                    id = photographerId,
                    navController = navController,
                    startTime = startTime,
                    endTime = endTime,
                    bill = bill,
                    bookingId = bookingId,
                    date = date,
                    addresss = address,
                    edit = edit,
                    eventtype = eventtype
                )
            }
        }

        this.composable(
            route = Screens.OtherServices.route,
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("startTime") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("endTime") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("address") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("bill") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument("serviceCount") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument("edit") {
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                },
                navArgument("bookingId") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
            )
        ) { backStackEntry ->
            val vendorId = backStackEntry.arguments?.getInt("index") ?: -1
            val vendor = otherservices.find { it.id == vendorId }
            val startTime = backStackEntry.arguments?.getString("startTime").orEmpty()
            val endTime = backStackEntry.arguments?.getString("endTime").orEmpty()
            val address = backStackEntry.arguments?.getString("address").orEmpty()
            val date = backStackEntry.arguments?.getString("date").orEmpty()
            val bill = backStackEntry.arguments?.getString("bill").orEmpty()
            val serviceCount = backStackEntry.arguments?.getString("serviceCount").orEmpty()
            val edit = backStackEntry.arguments?.getBoolean("edit") ?: false
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: -1
            if (vendor != null) {
                OtherServices(
                    navController = navController,
                    vendorr = vendorId,
                    userviewmodel = userviewmodel,
                    startTime = startTime,
                    endTime = endTime,
                    addresss = address,
                    date = date,
                    serviceCount = serviceCount.toInt(),
                    edit = edit,
                    bookingId = bookingId,
                    bill = bill
                )
            }
        }

        this.composable(
            route = Screens.Msg.route
        ) { Messages(
            userviewmodel = userviewmodel,
            navController = navController
        ) }
    }
}