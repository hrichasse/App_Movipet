package com.example.uinavegacion.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
<<<<<<< HEAD
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
=======
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
>>>>>>> 7db42d458292e54c947495fafecdf6e3159c32d1
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
<<<<<<< HEAD
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite

// Clase privada con los datos de cada botón de la barra inferior
private data class BottomNavItem(
    val route: String,        // ruta a navegar
    val icon: ImageVector,    // ícono
    val label: String         // texto visible
)

@Composable
fun BottomBar(navController: NavController) {

    // Lista de ítems que aparecen en la barra inferior
    val items = listOf(
        BottomNavItem(
            route = com.example.uinavegacion.navigation.Route.UserMenu.path,
            icon = Icons.Filled.Home,
            label = "Home"
        ),
        BottomNavItem(
            route = com.example.uinavegacion.navigation.Route.DriverSearch.path,
            icon = Icons.Filled.Search,
            label = "Buscar"
        ),
        BottomNavItem(
            route = com.example.uinavegacion.navigation.Route.UserMenu.path,
            icon = Icons.Filled.Person,
            label = "Perfil"
        )
    )

    // Detecta la pantalla actual para marcar el botón activo
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // --- TIP OPCIONAL ---
    // Ocultamos la barra inferior en pantallas específicas (por ejemplo Login o Splash)
    val showBottomBar = currentRoute !in listOf("login", "splash")

    if (showBottomBar) {
        NavigationBar(
            containerColor = MoviPetOrange, // Fondo naranjo de marca
            contentColor = MoviPetWhite     // Color base de íconos/textos
        ) {
            // Recorremos los ítems de la barra y los dibujamos uno por uno
            items.forEach { item ->
                val selected = currentRoute == item.route // ¿Es la pantalla actual?

                NavigationBarItem(
                    // Ícono con color según si está activo o no
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = item.label,
                            tint = if (selected)
                                MoviPetWhite
                            else
                                MoviPetWhite.copy(alpha = 0.6f) // más tenue si no está seleccionado
                        )
                    },
                    // Texto debajo del ícono
                    label = {
                        Text(
                            text = item.label,
                            color = if (selected)
                                MoviPetWhite
                            else
                                MoviPetWhite.copy(alpha = 0.7f)
                        )
                    },
                    selected = selected, // Indica si este botón está activo
                    onClick = {
                        // Navega solo si no estás ya en esa pantalla
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                // evita duplicar pantallas en el back stack
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    },
                    alwaysShowLabel = true // mantiene visible el texto bajo el ícono
                )
            }
=======


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
>>>>>>> 7db42d458292e54c947495fafecdf6e3159c32d1
        }
    }
}
