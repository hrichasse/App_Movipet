package com.example.uinavegacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

import com.example.uinavegacion.ui.screen.LoginScreen
import com.example.uinavegacion.ui.screen.RegisterScreen
import com.example.uinavegacion.ui.screen.VeterinariasScreen
import com.example.uinavegacion.ui.screen.TravelHistoryScreen
import com.example.uinavegacion.ui.screen.ChatScreen
import com.example.uinavegacion.ui.screen.RatingScreen
import com.example.uinavegacion.ui.screen.TripReceiptScreen
import com.example.uinavegacion.ui.screen.PaymentMethodScreen
import com.example.uinavegacion.ui.screen.PetsScreen
import com.example.uinavegacion.ui.screen.UserMenuScreen
import com.example.uinavegacion.ui.screen.LocationSelectionScreen
import com.example.uinavegacion.ui.screen.PetTypeSelectionScreen
import com.example.uinavegacion.ui.screen.VehicleTypeSelectionScreen
import com.example.uinavegacion.ui.screen.ConfirmationScreen
import com.example.uinavegacion.ui.screen.DriverSearchScreen
import com.example.uinavegacion.ui.screen.DriverSelectionScreen
import com.example.uinavegacion.ui.screen.DriverEnRouteScreen
import com.example.uinavegacion.ui.screen.CameraScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.path,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it / 2 }, animationSpec = tween(250)) + fadeIn(animationSpec = tween(250))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it / 3 }, animationSpec = tween(200)) + fadeOut(animationSpec = tween(200))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it / 3 }, animationSpec = tween(200)) + fadeIn(animationSpec = tween(200))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it / 2 }, animationSpec = tween(250)) + fadeOut(animationSpec = tween(250))
        }
    ) {
        composable(Route.Login.path) {
            LoginScreen(navController)
        }
        composable(Route.Register.path) {
            RegisterScreen(navController)
        }
        composable(Route.Veterinarias.path) {
            VeterinariasScreen(navController)
        }
        composable(Route.TravelHistory.path) {
            TravelHistoryScreen(navController)
        }
        composable(Route.Chat.path) {
            ChatScreen(navController)
        }
        composable(Route.Rating.path) {
            RatingScreen(navController)
        }
        composable(Route.TripReceipt.path) {
            TripReceiptScreen(navController)
        }
        composable(Route.PaymentMethod.path) {
            PaymentMethodScreen(navController)
        }
        composable(Route.Pets.path) {
            PetsScreen(navController)
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
        composable(
            route = Route.Camera.path + "?petId={petId}",
            arguments = listOf(navArgument("petId") { type = NavType.LongType; defaultValue = -1L })
        ) {
            CameraScreen(navController)
        }
    }
}