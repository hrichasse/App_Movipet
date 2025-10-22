package com.example.uinavegacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.uinavegacion.ui.screen.LoginScreen
import com.example.uinavegacion.ui.screen.UserMenuScreen
import com.example.uinavegacion.ui.screen.LocationSelectionScreen
import com.example.uinavegacion.ui.screen.PetTypeSelectionScreen
import com.example.uinavegacion.ui.screen.VehicleTypeSelectionScreen
import com.example.uinavegacion.ui.screen.ConfirmationScreen
import com.example.uinavegacion.ui.screen.DriverSearchScreen
import com.example.uinavegacion.ui.screen.DriverSelectionScreen
import com.example.uinavegacion.ui.screen.DriverEnRouteScreen
import com.example.uinavegacion.ui.screen.DriverProfileScreen
import com.example.uinavegacion.ui.screen.ChatScreen
import com.example.uinavegacion.ui.screen.TripSummaryScreen
import com.example.uinavegacion.ui.screen.RatingScreen
import com.example.uinavegacion.ui.screen.MyTripsScreen
<<<<<<< HEAD
import androidx.compose.foundation.layout.PaddingValues


@Composable
fun AppNavGraph(navController: NavHostController,
                contentPadding: PaddingValues
) {
=======

@Composable
fun AppNavGraph(navController: NavHostController) {
>>>>>>> 7db42d458292e54c947495fafecdf6e3159c32d1
    NavHost(
        navController = navController,
        startDestination = Route.Login.path
    ) {
        composable(Route.Login.path) {
            LoginScreen(navController)
        }
        
        composable(Route.UserMenu.path) {
            UserMenuScreen(navController)
        }
        
        composable(Route.LocationSelection.path) {
            LocationSelectionScreen(navController)
        }
        
        composable(Route.PetTypeSelection.path) {
            PetTypeSelectionScreen(navController)
        }
        
        composable(Route.VehicleTypeSelection.path) {
            VehicleTypeSelectionScreen(navController)
        }
        
        composable(Route.Confirmation.path) {
            ConfirmationScreen(navController)
        }
        
        composable(Route.DriverSearch.path) {
            DriverSearchScreen(navController)
        }
        
        composable(Route.DriverSelection.path) {
            DriverSelectionScreen(navController)
        }
        
        composable(Route.DriverEnRoute.path) {
            DriverEnRouteScreen(navController)
        }

        composable(Route.DriverProfile.path) {
            DriverProfileScreen(navController)
        }

        composable(Route.Chat.path) {
            ChatScreen(navController)
        }

        composable(Route.TripSummary.path) {
            TripSummaryScreen(navController)
        }

        composable(Route.Rating.path) {
            RatingScreen(navController)
        }

        composable(Route.MyTrips.path) {
            MyTripsScreen(navController)
        }
    }
}