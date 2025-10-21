package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun DriverEnRouteScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviPetTeal)
    ) {
        // Header con logo MoviPet
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MoviPetTeal)
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
                        .background(MoviPetOrange, androidx.compose.foundation.shape.CircleShape),
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
            
            IconButton(onClick = { navController.navigate(Route.UserMenu.path) }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MoviPetWhite
                )
            }
        }
        
        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MoviPetWhite)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Conductor en camino",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Mapa simulado
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MoviPetLightGray)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Icono del auto (conductor)
                        Icon(
                            Icons.Default.DirectionsCar,
                            contentDescription = "Driver Car",
                            modifier = Modifier.size(40.dp),
                            tint = MoviPetOrange
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Icono de ubicación del usuario
                        Icon(
                            Icons.Default.Pets,
                            contentDescription = "User Location",
                            modifier = Modifier.size(30.dp),
                            tint = MoviPetTeal
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Mapa de ruta",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Botón de enviar mensaje -> abre Chat
            Button(
                onClick = { navController.navigate(Route.Chat.path) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoviPetTeal)
            ) {
                Icon(
                    Icons.Default.Message,
                    contentDescription = "Message",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Enviar mensaje al conductor", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Acciones rápidas: Ver perfil y Chatear
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigate(Route.DriverProfile.path) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Perfil")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ver perfil")
                }

                OutlinedButton(
                    onClick = { navController.navigate(Route.Chat.path) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Message, contentDescription = "Chatear")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Chatear")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botones adicionales de prueba: Resumen del viaje y Calificar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { navController.navigate(Route.TripSummary.path) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MoviPetOrange)
                ) {
                    Icon(Icons.Default.ReceiptLong, contentDescription = "Resumen")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Resumen del viaje")
                }

                Button(
                    onClick = { navController.navigate(Route.Rating.path) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MoviPetOrange)
                ) {
                    Icon(Icons.Default.Star, contentDescription = "Calificar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Calificar")
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Botón de cancelar viaje
            Button(
                onClick = { navController.navigate(Route.LocationSelection.path) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("CANCELAR VIAJE", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
