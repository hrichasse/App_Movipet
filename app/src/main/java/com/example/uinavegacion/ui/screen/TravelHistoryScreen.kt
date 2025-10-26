package com.example.uinavegacion.ui.screen

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.data.MoviPetDatabase
import com.example.uinavegacion.data.entity.TripEntity
import com.example.uinavegacion.data.repository.TripRepository
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite
import java.text.SimpleDateFormat
import java.util.*

class TravelHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = MoviPetDatabase.getDatabase(application)
    private val repository = TripRepository(database.tripDao())
    val allTrips = repository.allTrips
}

@Composable
fun TravelHistoryScreen(navController: NavController) {
    val viewModel: TravelHistoryViewModel = viewModel()
    val trips by viewModel.allTrips.collectAsStateWithLifecycle(initialValue = emptyList())
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

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

        if (trips.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No hay viajes registrados", color = Color.Gray, fontSize = 16.sp)
                    Text("Tus viajes aparecerán aquí", color = Color.Gray, fontSize = 12.sp)
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(trips, key = { it.id }) { trip ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MoviPetWhite)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("${trip.fromAddress} → ${trip.toAddress}", fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(4.dp))
                            Text("Conductor: ${trip.driverName}", fontSize = 12.sp, color = Color.Gray)
                            Text("${dateFormat.format(Date(trip.timestamp))}", fontSize = 12.sp, color = Color.Gray)
                            Text("Total: $${"%.0f".format(trip.cost)}", fontSize = 12.sp, color = Color.Gray)
                            if (trip.rating != null) {
                                Text("Valoración: ${"★".repeat(trip.rating)}", fontSize = 12.sp, color = MoviPetOrange)
                            }
                        }
                    }
                }
            }
        }

        Box(Modifier.fillMaxWidth().height(8.dp).background(MoviPetOrange))
    }
}

data class Trip(val from: String, val to: String, val time: String, val total: String)
