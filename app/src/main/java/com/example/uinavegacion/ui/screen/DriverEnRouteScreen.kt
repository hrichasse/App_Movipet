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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.location.LocationViewModel
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.notification.NotificationHelper
import com.example.uinavegacion.ui.components.CurrentLocationMap
import com.example.uinavegacion.ui.components.RequestLocationPermissions
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import kotlinx.coroutines.delay

@Composable
fun DriverEnRouteScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: LocationViewModel = viewModel()
    var hasPermission by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }

    val location by viewModel.location.collectAsStateWithLifecycle()

    // Simular notificaciones de progreso del viaje
    LaunchedEffect(Unit) {
        delay(5000) // 5 segundos: conductor cerca
        NotificationHelper.notifyDriverNearby(context)
        
        delay(10000) // 15 segundos total: viaje iniciado
        NotificationHelper.notifyTripStarted(context)
    }

    // Solicitar permisos y comenzar rastreo
    if (!permissionRequested) {
        RequestLocationPermissions { granted ->
            hasPermission = granted
            permissionRequested = true
            if (granted) {
                viewModel.start()
            }
        }
    }

    // Detener rastreo al salir de la pantalla
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stop()
        }
    }
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
            
            // Mapa de seguimiento en tiempo real
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MoviPetLightGray)
            ) {
                if (hasPermission) {
                    CurrentLocationMap(
                        location = location,
                        modifier = Modifier.fillMaxSize(),
                        zoom = 16f,
                        showMyLocationButton = true
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.LocationOff,
                                contentDescription = "Location disabled",
                                modifier = Modifier.size(40.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Seguimiento en tiempo real",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "Activa la ubicación para ver tu posición",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Botón de enviar mensaje (abre chat)
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
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Botones inferiores: finalizar o cancelar viaje
            Button(
                onClick = { navController.navigate(Route.PaymentMethod.path) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoviPetOrange)
            ) {
                Text("FINALIZAR VIAJE", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { navController.navigate(Route.LocationSelection.path) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("CANCELAR VIAJE", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Red)
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
