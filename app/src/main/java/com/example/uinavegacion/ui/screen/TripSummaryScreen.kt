package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TripSummaryScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // Placeholder para mapa
        Box(modifier = Modifier.height(200.dp).fillMaxWidth().padding(8.dp)) {
            Text("[Mapa del recorrido aquí]", modifier = Modifier.align(Alignment.Center))
        }

        Spacer(Modifier.height(12.dp))
        Text("Duración: 25 min")
        Text("Costo: $12.50")
        Text("Destino / Veterinario: VetCare - Dr. Pérez")

        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { /* generar PDF */ }) { Text("Descargar comprobante PDF") }
            Button(onClick = { /* compartir */ }) { Text("Compartir viaje") }
        }
    }
}
