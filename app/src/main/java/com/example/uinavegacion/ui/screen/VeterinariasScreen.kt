package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.viewmodel.ClinicViewModel

@Composable
fun VeterinariasScreen(
    navController: NavController,
    clinicViewModel: ClinicViewModel = viewModel()
) {
    val clinics by clinicViewModel.clinics.collectAsStateWithLifecycle()
    val isLoading by clinicViewModel.isLoading.collectAsStateWithLifecycle()
    val error by clinicViewModel.error.collectAsStateWithLifecycle()

    // Cargar clínicas al abrir pantalla
    LaunchedEffect(Unit) {
        clinicViewModel.loadClinics()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Route.AddClinic.path) },
                containerColor = MoviPetOrange
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar veterinaria", tint = MoviPetWhite)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MoviPetLightGray)
                .padding(paddingValues)
        ) {
            // Header
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
                Text(
                    text = "Veterinarias asociadas",
                    style = MaterialTheme.typography.titleLarge,
                    color = MoviPetWhite
                )
                Spacer(Modifier.size(48.dp))
            }

            // Estados: Loading, Error, Empty, Lista
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = MoviPetOrange)
                            Spacer(Modifier.height(16.dp))
                            Text("Cargando veterinarias...", fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                }
                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                error!!,
                                color = Color(0xFFD64545),
                                fontSize = 14.sp
                            )
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = { clinicViewModel.loadClinics() },
                                colors = ButtonDefaults.buttonColors(containerColor = MoviPetOrange)
                            ) {
                                Text("Reintentar")
                            }
                        }
                    }
                }
                clinics.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("No hay veterinarias registradas", fontSize = 16.sp, color = Color.Gray)
                            Spacer(Modifier.height(8.dp))
                            Text("Presiona + para agregar una", fontSize = 13.sp, color = Color.Gray)
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Text(
                                "Selecciona una veterinaria para continuar",
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.height(8.dp))
                        }

                        items(clinics) { clinic ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { navController.navigate(Route.LocationSelection.path) },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MoviPetWhite),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                clinic.clinicName,
                                                fontSize = 18.sp,
                                                color = Color.Black
                                            )
                                            Spacer(Modifier.height(4.dp))
                                            Text(
                                                clinic.clinicAddress,
                                                fontSize = 13.sp,
                                                color = Color(0xFF6B6B6B)
                                            )
                                            Spacer(Modifier.height(8.dp))
                                            Text(
                                                "📞 ${clinic.clinicPhone}",
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                            Text(
                                                "✉️ ${clinic.clinicEmail}",
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                        }
                                        Surface(
                                            modifier = Modifier.size(24.dp),
                                            color = MoviPetTeal,
                                            shape = RoundedCornerShape(4.dp)
                                        ) {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
