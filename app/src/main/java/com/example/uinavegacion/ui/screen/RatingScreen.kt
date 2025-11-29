package com.example.uinavegacion.ui.screen

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.data.MoviPetDatabase
import com.example.uinavegacion.data.entity.TripEntity
import com.example.uinavegacion.data.repository.TripRepository
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite
import kotlinx.coroutines.launch

class RatingViewModel(application: Application) : AndroidViewModel(application) {
    private val database = MoviPetDatabase.getDatabase(application)
    private val repository = TripRepository(database.tripDao())

    fun saveTripWithRating(rating: Int, comment: String, onSaved: () -> Unit) {
        viewModelScope.launch {
            // Crear viaje de demostración con la valoración
            val trip = TripEntity(
                fromAddress = "Tu ubicación",
                toAddress = "Veterinaria",
                fromLat = -33.4489,
                fromLon = -70.6693,
                toLat = -33.4520,
                toLon = -70.6620,
                driverName = "Juan Pérez",
                driverCar = "ABCD-12",
                distance = 5.2,
                duration = 14,
                cost = 7900.0,
                rating = rating,
                comment = comment
            )
            repository.insertTrip(trip)
            onSaved()
        }
    }
}

@Composable
fun RatingScreen(navController: NavController) {
    val viewModel: RatingViewModel = viewModel()
    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().background(MoviPetLightGray)) {
        Row(Modifier.fillMaxWidth().background(MoviPetOrange).padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MoviPetWhite) }
            Text("Valora al conductor", style = MaterialTheme.typography.titleLarge, color = MoviPetWhite)
            Spacer(Modifier.size(48.dp))
        }

        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("¿Cómo fue tu experiencia?", color = Color.Black, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..5).forEach { i ->
                    FilledIconToggleButton(checked = rating >= i, onCheckedChange = {
                        rating = if (rating == i) i - 1 else i
                    }) { Text("★", color = if (rating >= i) Color.Yellow else Color.Gray, fontSize = 20.sp) }
                }
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(value = comment, onValueChange = { comment = it }, label = { Text("Comentarios") }, modifier = Modifier.fillMaxWidth().heightIn(min = 80.dp))

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (rating > 0) {
                        isSaving = true
                        viewModel.saveTripWithRating(rating, comment) {
                            isSaving = false
                            navController.navigate(Route.TripReceipt.path) {
                                popUpTo(Route.Rating.path) { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = rating > 0 && !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = MoviPetWhite, modifier = Modifier.size(24.dp))
                } else {
                    Text("Enviar valoración")
                }
            }
        }

        Box(Modifier.fillMaxWidth().height(8.dp).background(MoviPetOrange))
    }
}
