package com.example.uinavegacion.ui.screen

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.data.MoviPetDatabase
import com.example.uinavegacion.data.entity.PetEntity
import com.example.uinavegacion.data.repository.PetRepository
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite
import kotlinx.coroutines.launch

class PetsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = MoviPetDatabase.getDatabase(application)
    private val repository = PetRepository(database.petDao())
    val allPets = repository.allPets

    fun addPet(name: String, type: String = "Perro") {
        viewModelScope.launch {
            repository.insertPet(PetEntity(name = name, type = type))
        }
    }

    fun deletePet(pet: PetEntity) {
        viewModelScope.launch {
            repository.deletePet(pet)
        }
    }
}

@Composable
fun PetsScreen(navController: NavController) {
    val viewModel: PetsViewModel = viewModel()
    val pets by viewModel.allPets.collectAsStateWithLifecycle(initialValue = emptyList())
    var newPet by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().background(MoviPetLightGray)) {
        Row(Modifier.fillMaxWidth().background(MoviPetOrange).padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MoviPetWhite) }
            Text("Mis mascotas", style = MaterialTheme.typography.titleLarge, color = MoviPetWhite)
            Spacer(Modifier.size(48.dp))
        }

        Column(Modifier.padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f, false)) {
                items(pets, key = { it.id }) { pet ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MoviPetWhite),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(pet.name, fontSize = 16.sp, color = Color.Black)
                                Text(pet.type, fontSize = 12.sp, color = Color.Gray)
                            }
                            IconButton(onClick = { viewModel.deletePet(pet) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(value = newPet, onValueChange = { newPet = it }, label = { Text("Nombre de la mascota") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            Button(onClick = { 
                if (newPet.isNotBlank()) { 
                    viewModel.addPet(newPet)
                    newPet = "" 
                } 
            }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Agregar mascota")
            }
        }

        Box(Modifier.fillMaxWidth().height(8.dp).background(MoviPetOrange))
    }
}
