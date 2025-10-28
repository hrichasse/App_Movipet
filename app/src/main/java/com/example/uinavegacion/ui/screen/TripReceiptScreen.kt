package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.uinavegacion.ui.components.MoviPetHeader
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite

@Composable
fun TripReceiptScreen(navController: NavController) {
    Column(Modifier.fillMaxSize().background(MoviPetLightGray)) {
        MoviPetHeader(
            title = "Comprobante de viaje",
            onBackClick = { navController.popBackStack() },
            onHomeClick = { 
                navController.navigate(Route.UserMenu.path) {
                    popUpTo(Route.UserMenu.path) { inclusive = false }
                }
            }
        )

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
