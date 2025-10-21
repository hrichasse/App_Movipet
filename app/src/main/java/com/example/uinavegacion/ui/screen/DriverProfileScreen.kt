package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DriverProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto y nombre
        Card(modifier = Modifier.size(120.dp), shape = CircleShape) {
            // placeholder image (usa un recurso si lo tienes)
            Box(contentAlignment = Alignment.Center) {
                // Puedes reemplazar painterResource por una imagen real
                Text(text = "Foto", modifier = Modifier.padding(8.dp))
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("Nombre del conductor", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("⭐⭐⭐⭐☆ 4.2")
        Spacer(Modifier.height(8.dp))
        Text("Vehículo: Toyota Prius - Patente: ABC123")

        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { /* Llamar - integrar intent */ }) { Text("Llamar") }
            Button(onClick = { navController.navigate("chat") }) { Text("Chatear") }
        }

        Spacer(Modifier.height(12.dp))
        Button(onClick = { /* Mostrar ubicación en tiempo real (abrir mapa) */ }) {
            Text("Ver ubicación en tiempo real")
        }
    }
}
