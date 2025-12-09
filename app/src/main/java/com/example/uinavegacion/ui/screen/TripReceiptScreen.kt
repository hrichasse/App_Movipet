package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.viewmodel.TripViewModel

@Composable
fun TripReceiptScreen(
    navController: NavController,
    tripViewModel: TripViewModel = viewModel()
) {
    val lastTrip by tripViewModel.lastCreatedTrip.collectAsStateWithLifecycle()
    val isLoading by tripViewModel.isLoading.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize().background(MoviPetLightGray)) {
        Row(
            Modifier.fillMaxWidth().background(MoviPetOrange).padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MoviPetWhite)
            }
            Text("Comprobante de viaje", style = MaterialTheme.typography.titleLarge, color = MoviPetWhite)
            Spacer(Modifier.size(48.dp))
        }

        if (isLoading) {
            Column(
                Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
                Text("Cargando recibo...", fontSize = 14.sp)
            }
        } else if (lastTrip == null) {
            Column(
                Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No hay datos del viaje", fontSize = 16.sp, color = Color.Gray)
            }
        } else {
            val trip = lastTrip!!
            Column(Modifier.padding(16.dp)) {
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MoviPetWhite)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Resumen", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        Text("Origen: ${trip.fromAddress}")
                        Text("Destino: ${trip.toAddress}")
                        Text("Distancia: ${trip.distance} km")
                        Text("Tiempo: ${trip.duration} min")
                        Text("Total: $${trip.cost ?: 0.0}")
                        if (trip.rating != null) {
                            Text("Calificación: ${"★".repeat(trip.rating)} (${trip.rating}/5)")
                        }
                        if (!trip.comment.isNullOrBlank()) {
                            Text("Comentario: ${trip.comment}", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MoviPetWhite)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Conductor", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(modifier = Modifier.size(36.dp), color = MoviPetTeal, shape = RoundedCornerShape(18.dp)) {}
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(trip.driverName, fontWeight = FontWeight.Medium)
                                Text("Auto: ${trip.driverCar}", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Button(onClick = { navController.navigate(Route.TravelHistory.path) }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
                    Text("Ir al historial")
                }
            }
        }

        Box(Modifier.fillMaxWidth().height(8.dp).background(MoviPetOrange))
    }
}
