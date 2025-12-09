package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.viewmodel.TripSelectionViewModel

@Composable
fun ConfirmationScreen(navController: NavController, tripViewModel: TripSelectionViewModel) {
    var petOnly by remember { mutableStateOf(false) }
    var withOwner by remember { mutableStateOf(false) }
    
    val selectedVehicleType by tripViewModel.selectedVehicleType.collectAsState()
    val selectedPetType by tripViewModel.selectedPetType.collectAsState()
    val selectedOrigin by tripViewModel.selectedOrigin.collectAsState()
    val selectedDestination by tripViewModel.selectedDestination.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviPetLightGray)
    ) {
        // Header con logo MoviPet
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MoviPetTeal)
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
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(MoviPetOrange, androidx.compose.foundation.shape.CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🚗", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "MoviPet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MoviPetWhite
                )
            }
            
            IconButton(onClick = { navController.navigate(Route.UserMenu.path) }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MoviPetWhite
                )
            }
        }
        
        // Contenido principal scrolleable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Título
            Text(
                "Resumen de tu viaje",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Card principal con toda la información
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MoviPetWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Ruta: Origen → Destino
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MoviPetOrange,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Tu ubicación",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                "Actual",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        }
                        
                        // Flecha en el medio
                        Column(
                            modifier = Modifier.weight(0.5f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = MoviPetOrange,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MoviPetTeal,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Destino",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                "Veterinaria",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        }
                    }
                    
                    Divider(modifier = Modifier.padding(vertical = 16.dp))
                    
                    // Mascota seleccionada
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "🐕 Mascota",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                selectedPetType,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Surface(
                            modifier = Modifier
                                .size(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFFE0B2)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🐕", fontSize = 28.sp)
                            }
                        }
                    }
                    
                    Spacer(Modifier.height(8.dp))
                    
                    // Vehículo seleccionado
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "🚐 Vehículo",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                selectedVehicleType,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Surface(
                            modifier = Modifier
                                .size(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🚐", fontSize = 28.sp)
                            }
                        }
                    }
                    
                    Spacer(Modifier.height(8.dp))
                    
                    // Tiempo estimado
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "⏱️ Tiempo estimado",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                "12-15 minutos",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MoviPetOrange
                            )
                        }
                        Surface(
                            modifier = Modifier.padding(8.dp),
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFFF3E0)
                        ) {
                            Text(
                                "Tarifa: \$120",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MoviPetOrange,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
            
            // Requisitos especiales
            Text(
                "Requisitos especiales para la mascota",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MoviPetWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = petOnly,
                            onCheckedChange = { petOnly = it },
                            colors = CheckboxDefaults.colors(checkedColor = MoviPetOrange)
                        )
                        Text(
                            text = "Solo mascota (sin acompañante)",
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = withOwner,
                            onCheckedChange = { withOwner = it },
                            colors = CheckboxDefaults.colors(checkedColor = MoviPetOrange)
                        )
                        Text(
                            text = "Con dueño o acompañante",
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botón de buscar conductor
            Button(
                onClick = { navController.navigate(Route.DriverSearch.path) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoviPetOrange),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = MoviPetWhite,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "BUSCAR CONDUCTOR",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Footer naranja
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(MoviPetOrange)
        )
    }
}
