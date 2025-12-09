package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.viewmodel.TripViewModel
import java.text.SimpleDateFormat
import java.util.*

// Función helper para formatear timestamps
private fun formatTimestamp(timestamp: Long?): String {
    if (timestamp == null || timestamp == 0L) return "Sin fecha"
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        sdf.format(Date(timestamp))
    } catch (e: Exception) {
        "Fecha inválida"
    }
}

@Composable
fun TravelHistoryScreen(
    navController: NavController,
    tripViewModel: TripViewModel = viewModel()
) {
    val trips by tripViewModel.trips.collectAsStateWithLifecycle()
    val isLoading by tripViewModel.isLoading.collectAsStateWithLifecycle()
    val error by tripViewModel.error.collectAsStateWithLifecycle()

    // Cargar viajes al abrir
    LaunchedEffect(Unit) {
        tripViewModel.loadTrips()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviPetLightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MoviPetOrange)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MoviPetWhite)
            }
            Text("Historial de viajes", style = MaterialTheme.typography.titleLarge, color = MoviPetWhite)
            Spacer(Modifier.size(48.dp))
        }

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(8.dp))
                    Text("Cargando viajes...", fontSize = 14.sp)
                }
            }
        } else if (error != null) {
            Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                Text("Error: $error", color = Color.Red, fontSize = 14.sp)
            }
        } else if (trips.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No hay viajes registrados", color = Color.Gray, fontSize = 16.sp)
                    Text("Tus viajes aparecerán aquí", color = Color.Gray, fontSize = 12.sp)
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(trips, key = { it.id ?: UUID.randomUUID().toString() }) { trip ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MoviPetWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                "${trip.fromAddress} → ${trip.toAddress}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                            Spacer(Modifier.height(6.dp))
                            
                            Text(
                                "Conductor: ${trip.driverName} (${trip.driverCar})",
                                fontSize = 13.sp,
                                color = Color(0xFF6B6B6B)
                            )
                            
                            Text(
                                "📅 ${formatTimestamp(trip.timestamp)}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "💰 $${"%.0f".format(trip.cost ?: 0.0)}",
                                    fontSize = 13.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    "${trip.distance?.let { "%.1f km".format(it) } ?: ""} • ${trip.duration ?: 0} min",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            if (trip.rating != null && trip.rating > 0) {
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "${"★".repeat(trip.rating)}${"☆".repeat(5 - trip.rating)}",
                                    fontSize = 13.sp,
                                    color = MoviPetOrange
                                )
                            }
                            
                            if (!trip.comment.isNullOrBlank()) {
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "\"${trip.comment}\"",
                                    fontSize = 12.sp,
                                    color = Color.DarkGray,
                                    fontWeight = FontWeight.Light
                                )
                            }
                        }
                    }
                }
            }
        }

        Box(Modifier.fillMaxWidth().height(8.dp).background(MoviPetOrange))
    }
}
