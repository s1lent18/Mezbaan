package com.example.mezbaan.viewmodel.navigation

sealed class Screens(val route: String) {
    data object Msg: Screens("Msg_Screen")
    data object Home: Screens("Home_Screen")
    data object Login: Screens("Login_Screen")
    data object Signup: Screens("Signup_Screen")
    data object Account: Screens("Account_Screen")
    data object Landing: Screens("Landing_Screen")
    data object Venues: Screens("Venues_Screen/{index}?guestCount={guestCount}&startTime={startTime}&endTime={endTime}&date={date}&bill={bill}&edit={edit}&bookingId={bookingId}")
    data object Decorators : Screens("Decorators_Screen/{index}?startTime={startTime}&endTime={endTime}&address={address}&bill={bill}&date={date}&amenities={amenities}&edit={edit}&bookingId={bookingId}")
    data object Photographers: Screens("Photographers_Screen/{index}?startTime={startTime}&endTime={endTime}&address={address}&bill={bill}&date={date}&eventtype={eventtype}&edit={edit}&bookingId={bookingId}")
    data object OtherServices: Screens("OtherServices_Screen/{index}?startTime={startTime}&endTime={endTime}&address={address}&bill={bill}&date={date}&edit={edit}&bookingId={bookingId}&serviceCount={serviceCount}")
    data object Caterers: Screens("Caterers_Screen/{index}?startTime={startTime}&address={address}&bill={bill}&date={date}&edit={edit}&bookingId={bookingId}&bookedPackages={bookedPackages}&bookedMenuItems={bookedMenuItems}")
}