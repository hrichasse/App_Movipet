package com.example.uinavegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.uinavegacion.navigation.AppNavGraph
import com.example.uinavegacion.ui.theme.UINavegacionTheme
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.uinavegacion.ui.components.BottomBar
import androidx.compose.material3.Scaffold
import com.example.uinavegacion.ui.components.AppTopBar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppRoot()
        }
    }
}


/*
* En Compose, Surface es un contenedor visual que viene de Material 3.Crea un bloque
*  que puedes personalizar con color, forma, sombra (elevación).
Sirve para aplicar un fondo (color, borde, elevación, forma) siguiendo las guías de diseño
* de Material.
Piensa en él como una “lona base” sobre la cual vas a pintar tu UI.
* Si cambias el tema a dark mode, colorScheme.background
* cambia automáticamente y el Surface pinta la pantalla con el nuevo color.
* */
@Composable // Indica que esta función dibuja UI
fun AppRoot() { // Raíz de la app para separar responsabilidades
    val navController = rememberNavController() // Controlador de navegación
    UINavegacionTheme (// Provee colores/tipografías Material 3 con tema MoviPet
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false // <- importante para mantener el naranjo de marca
    ) {
        Scaffold(
            topBar = { AppTopBar() },
            bottomBar = { BottomBar(navController) }
        ) { innerPadding ->
            // Pasamos el padding del Scaffold al NavHost (AppNavGraph)
            AppNavGraph(
                navController = navController,
                contentPadding = innerPadding
            )
        }
    }
}