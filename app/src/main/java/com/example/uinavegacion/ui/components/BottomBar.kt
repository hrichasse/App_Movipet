package com.example.uinavegacion.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


private data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("${com.example.uinavegacion.navigation.Route.UserMenu.path}", Icons.Filled.Home, "Home"),
        BottomNavItem("${com.example.uinavegacion.navigation.Route.DriverSearch.path}", Icons.Filled.Search, "Buscar"),
        BottomNavItem("${com.example.uinavegacion.navigation.Route.UserMenu.path}", Icons.Filled.Person, "Perfil")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}
