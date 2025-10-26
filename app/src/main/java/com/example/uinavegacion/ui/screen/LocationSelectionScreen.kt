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
import com.example.uinavegacion.ui.components.CurrentLocationMap
import com.example.uinavegacion.ui.components.RequestLocationPermissions
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.ui.theme.MoviPetLightGray

@Composable
fun LocationSelectionScreen(navController: NavController) {
    val viewModel: LocationViewModel = viewModel()
    var hasPermission by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }
    var pickupLocation by remember { mutableStateOf("PATRONATO") }
    var dropoffLocation by remember { mutableStateOf("") }

    val location by viewModel.location.collectAsStateWithLifecycle()
    val address by viewModel.address.collectAsStateWithLifecycle()

    // Solicitar permisos solo una vez
    if (!permissionRequested) {
        RequestLocationPermissions { granted ->
            hasPermission = granted
            permissionRequested = true
            if (granted) {
                viewModel.start()
            }
        }
    }

    // Actualizar ubicación actual cuando cambie (ahora con dirección legible)
    LaunchedEffect(address) {
        address?.let {
            pickupLocation = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviPetWhite)
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
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "¿Donde estas?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Campo de ubicación actual
            OutlinedTextField(
                value = pickupLocation,
                onValueChange = { pickupLocation = it },
                label = { Text("Ubicación actual") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MoviPetOrange
                    )
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Mapa con ubicación real
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MoviPetLightGray)
            ) {
                if (hasPermission) {
                    CurrentLocationMap(
                        location = location,
                        modifier = Modifier.fillMaxSize(),
                        zoom = 15f
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
                                text = "Activa el permiso de ubicación",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "para ver el mapa",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "¿Donde vas?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Campo de destino
            OutlinedTextField(
                value = dropoffLocation,
                onValueChange = { dropoffLocation = it },
                label = { Text("Destino") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Destination",
                        tint = MoviPetOrange
                    )
                }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Botón de confirmar
            Button(
                onClick = { navController.navigate(Route.PetTypeSelection.path) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoviPetOrange)
            ) {
                Text("CONFIRMAR", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
