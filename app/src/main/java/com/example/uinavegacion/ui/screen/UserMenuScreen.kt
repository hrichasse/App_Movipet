package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.ui.theme.MoviPetLightGray

@Composable
fun UserMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviPetLightGray)
    ) {
        // Header con logo MoviPet
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MoviPetOrange)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MoviPetWhite
                )
            }
            
            // Logo MoviPet
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(MoviPetTeal, androidx.compose.foundation.shape.CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🚗", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "MoviPet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MoviPetWhite
                )
            }
            
            IconButton(onClick = { /* Settings */ }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MoviPetWhite
                )
            }
        }
        
        // Contenido del menú
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Card del menú
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MoviPetWhite)
            ) {
                Column {
                    MenuItem(
                        icon = Icons.Default.History,
                        text = "Historial de viajes",
                        onClick = { navController.navigate(com.example.uinavegacion.navigation.Route.TravelHistory.path) },
                        showArrow = true
                    )

                    Divider()

                    MenuItem(
                        icon = Icons.Default.Person,
                        text = "Perfil de usuario",
                        onClick = { /* Perfil */ }
                    )
                    
                    Divider()
                    
                    MenuItem(
                        icon = Icons.Default.Pets,
                        text = "Mis mascotas",
                        onClick = { navController.navigate(com.example.uinavegacion.navigation.Route.Pets.path) }
                    )
                    
                    Divider()
                    
                    MenuItem(
                        icon = Icons.Default.Notifications,
                        text = "Notificaciones",
                        onClick = { /* Notificaciones */ },
                        showArrow = true
                    )
                    
                    Divider()
                    
                    // Toggle de Modo Oscuro
                    com.example.uinavegacion.ui.components.ThemeToggleMenuItem()
                    
                    Divider()
                    
                    MenuItem(
                        icon = Icons.Default.Description,
                        text = "Términos y condiciones",
                        onClick = { /* Términos */ }
                    )
                    
                    Divider()
                    
                    MenuItem(
                        icon = Icons.Default.Lock,
                        text = "Privacidad",
                        onClick = { /* Privacidad */ }
                    )
                    
                    Divider()
                    
                    MenuItem(
                        icon = Icons.Default.Description,
                        text = "Términos y condiciones",
                        onClick = { /* Términos */ }
                    )
                    
                    Divider()
                    
                    MenuItem(
                        icon = Icons.Default.Logout,
                        text = "Cerrar sesión",
                        onClick = { navController.navigate(Route.Login.path) }
                    )
                }
            }
        }
        
        // Footer naranja
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(MoviPetOrange)
        )
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    showArrow: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = text,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        
        if (showArrow) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Arrow",
                tint = MoviPetOrange,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
