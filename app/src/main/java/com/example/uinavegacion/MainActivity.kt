package com.example.uinavegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.uinavegacion.data.ThemePreferences
import com.example.uinavegacion.navigation.AppNavGraph
import com.example.uinavegacion.notification.NotificationHelper
import com.example.uinavegacion.ui.theme.UINavegacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Crear canal de notificaciones
        NotificationHelper.createNotificationChannel(this)
        
        setContent {
            AppRoot(themePreferences = ThemePreferences(this))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable // Indica que esta función dibuja UI
fun AppRoot(themePreferences: ThemePreferences) { // Raíz de la app para separar responsabilidades
    val isDarkTheme by themePreferences.isDarkTheme.collectAsState(initial = false)
    val navController = rememberNavController() // Controlador de navegación
    UINavegacionTheme(darkTheme = isDarkTheme, dynamicColor = false) { // Provee colores/tipografías Material 3 con tema MoviPet
        AppNavGraph(navController = navController) // Carga el NavHost
    }
}