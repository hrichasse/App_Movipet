package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreen(
    navController: NavController,
    remoteVm: RemotePetViewModel = viewModel()
) {
    // Estados que vienen del ViewModel remoto
    val pets by remoteVm.pets.collectAsState()
    val isLoading by remoteVm.isLoading.collectAsState()
    val error by remoteVm.error.collectAsState()

    var newPetName by remember { mutableStateOf("") }
    var newPetType by remember { mutableStateOf("Perro") }
    var newPetBreed by remember { mutableStateOf("") }
    var newPetAge by remember { mutableStateOf("") }
    var newPetWeight by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    // Cuando se abre la pantalla, carga las mascotas del backend
    LaunchedEffect(Unit) {
        remoteVm.loadPets()
    }

    // Dialog para agregar mascota
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Agregar Mascota") },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = newPetName,
                        onValueChange = { newPetName = it },
                        label = { Text("Nombre *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Dropdown para tipo (usando botones simples)
                    Text("Tipo de mascota *", fontSize = 14.sp, color = Color.Gray)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("Perro", "Gato", "Ave", "Otro").forEach { tipo ->
                            FilterChip(
                                selected = newPetType == tipo,
                                onClick = { newPetType = tipo },
                                label = { Text(tipo, fontSize = 12.sp) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = newPetBreed,
                        onValueChange = { newPetBreed = it },
                        label = { Text("Raza (opcional)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newPetAge,
                        onValueChange = { newPetAge = it },
                        label = { Text("Edad (años, opcional)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = newPetWeight,
                        onValueChange = { newPetWeight = it },
                        label = { Text("Peso (kg, opcional)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )

                    Text("* Campos obligatorios", fontSize = 11.sp, color = Color.Gray)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPetName.isNotBlank()) {
                            val newPetObj = Pet(
                                id = null,
                                name = newPetName.trim(),
                                type = newPetType,
                                breed = newPetBreed.trim().ifBlank { "" },
                                age = newPetAge.toIntOrNull() ?: 0,
                                weight = newPetWeight.toDoubleOrNull() ?: 0.0
                            )
                            remoteVm.createPet(newPetObj)
                            // Limpiar campos
                            newPetName = ""
                            newPetType = "Perro"
                            newPetBreed = ""
                            newPetAge = ""
                            newPetWeight = ""
                            showAddDialog = false
                        }
                    },
                    enabled = newPetName.isNotBlank()
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
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

            // Botón para agregar mascota
            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoviPetOrange)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Agregar Mascota")
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
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    pet.name,
                    fontSize = 17.sp,
                    color = Color.Black
                )
                Text(
                    "${pet.type}${if (pet.breed.isNotBlank()) " • ${pet.breed}" else ""}",
                    fontSize = 13.sp,
                    color = Color(0xFF6B6B6B)
                )
                if (pet.age > 0 || pet.weight > 0.0) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        buildString {
                            if (pet.age > 0) append("${pet.age} años")
                            if (pet.age > 0 && pet.weight > 0.0) append(" • ")
                            if (pet.weight > 0.0) append("${pet.weight} kg")
                        },
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color(0xFFD64545)
                )
            }
        }
    }
}
