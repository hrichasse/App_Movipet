package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.data.model.Pet
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.viewmodel.RemotePetViewModel

@Composable
fun PetsScreen(
    navController: NavController,
    remoteVm: RemotePetViewModel = viewModel()
) {
    // Estados que vienen del ViewModel remoto
    val pets by remoteVm.pets.collectAsState()
    val isLoading by remoteVm.isLoading.collectAsState()
    val error by remoteVm.error.collectAsState()

    var newPet by remember { mutableStateOf("") }

    // Cuando se abre la pantalla, carga las mascotas del backend
    LaunchedEffect(Unit) {
        remoteVm.loadPets()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MoviPetLightGray)
    ) {
        // HEADER
        Row(
            Modifier
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

            Text(
                "Mis mascotas",
                style = MaterialTheme.typography.titleLarge,
                color = MoviPetWhite
            )

            Spacer(Modifier.size(48.dp)) // para equilibrar el header
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // BOTÓN PARA ABRIR CÁMARA
            OutlinedButton(
                onClick = {
                    navController.navigate(Route.Camera.path)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Tomar foto de mascota")
            }

            Spacer(Modifier.height(12.dp))

            // ESTADO DE CARGA
            if (isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Cargando mascotas desde el servidor...")
                }
            }

            // ERROR DEL BACKEND
            if (error != null) {
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // LISTA DE MASCOTAS REMOTAS
            LazyColumn(
                modifier = Modifier
                    .weight(1f, fill = true)
            ) {
                items(pets) { pet ->
                    PetItem(
                        pet = pet,
                        onDelete = {
                            // Cuando tengas DELETE en el backend:
                            // remoteVm.deletePet(pet)
                        }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // INPUT PARA AGREGAR MASCOTA
            OutlinedTextField(
                value = newPet,
                onValueChange = { newPet = it },
                label = { Text("Nombre de la mascota") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    if (newPet.isNotBlank()) {
                        // Crea mascota en el backend.
                        // OJO: aquí rellenamos también breed, age y weight
                        val newPetObj = Pet(
                            id = null,
                            name = newPet,
                            type = "Perro",      // fijo por ahora
                            breed = "",          // o "Sin raza"
                            age = 0,             // por defecto
                            weight = 0.0         // por defecto
                            // si tu Pet tiene createdAt con default, no hace falta
                            // createdAt = System.currentTimeMillis()
                        )
                        remoteVm.createPet(newPetObj)
                        newPet = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Agregar mascota")
            }
        }

        // FOOTER NARANJO
        Box(
            Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(MoviPetOrange)
        )
    }
}

@Composable
private fun PetItem(
    pet: Pet,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MoviPetWhite),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    pet.name,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    pet.type,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        }
    }
}
