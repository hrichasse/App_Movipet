package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class TripItem(val date: String, val dest: String, val driver: String, val status: String)

@Composable
fun MyTripsScreen(navController: NavController) {
    var filter by remember { mutableStateOf("Completados") }
    val all = listOf(
        TripItem("2025-10-18", "VetCare", "Juan", "Completado"),
        TripItem("2025-10-05", "City Clinic", "María", "Cancelado"),
        TripItem("2025-09-30", "VetCare", "Pedro", "Pendiente")
    )

    val filtered = remember(filter) { all.filter { f -> when(filter) { "Completados" -> f.status=="Completado"; "Pendientes" -> f.status=="Pendiente"; "Cancelados" -> f.status=="Cancelado"; else -> true } } }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Completados", "Pendientes", "Cancelados").forEach { f ->
                OutlinedButton(onClick = { filter = f }) { Text(f) }
            }
        }

        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(filtered) { trip ->
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { /* detalle */ }, horizontalArrangement = Arrangement.SpaceBetween) {
                    Column { Text(trip.date); Text(trip.dest) }
                    Column { Text(trip.driver); Text(trip.status) }
                    Button(onClick = { /* repetir viaje */ }) { Text("Repetir viaje") }
                }
            }
        }
    }
}
