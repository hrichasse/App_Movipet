package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite

@Composable
fun TripReceiptScreen(navController: NavController) {
    Column(Modifier.fillMaxSize().background(MoviPetLightGray)) {
        Row(Modifier.fillMaxWidth().background(MoviPetOrange).padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MoviPetWhite) }
            Text("Comprobante de viaje", style = MaterialTheme.typography.titleLarge, color = MoviPetWhite)
            Spacer(Modifier.size(48.dp))
        }

        Column(Modifier.padding(16.dp)) {
            Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MoviPetWhite)) {
                Column(Modifier.padding(16.dp)) {
                    Text("Resumen", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Text("Origen: Domicilio")
                    Text("Destino: VetCare Center")
                    Text("Distancia: 5.2 km")
                    Text("Tiempo: 14 min")
                    Text("Total: $7.900")
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
                        Column { Text("Juan Pérez", fontWeight = FontWeight.Medium); Text("Auto: ABCD-12", fontSize = 12.sp, color = Color.Gray) }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(onClick = { navController.navigate(Route.TravelHistory.path) }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
                Text("Ir al historial")
            }
        }

        Box(Modifier.fillMaxWidth().height(8.dp).background(MoviPetOrange))
    }
}
