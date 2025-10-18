package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.ui.theme.MoviPetLightGray

data class Driver(
    val name: String,
    val rating: String,
    val arrivalTime: String,
    val vehicleType: String,
    val licensePlate: String
)

@Composable
fun DriverSelectionScreen(navController: NavController) {
    val drivers = listOf(
        Driver("Maria R.", "4,9", "5 min", "Kia Sdu", "KX-ZT23"),
        Driver("Pedro L.", "4,7", "7 min", "Van mediana", "LP-ZT56"),
        Driver("Ana G.", "4,8", "6 min", "Transporte Especial", "MJ-KP32")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviPetLightGray)
    ) {
        // Header con logo MoviPet
        Row(
            modifier = Modifier
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
            
            // Logo MoviPet
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(MoviPetTeal, androidx.compose.foundation.shape.CircleShape),
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
        
        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Lista de conductores
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(drivers) { driver ->
                    DriverCard(
                        driver = driver,
                        onClick = { navController.navigate(Route.DriverEnRoute.path) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botón de elegir
            Button(
                onClick = { navController.navigate(Route.DriverEnRoute.path) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoviPetOrange)
            ) {
                Text("ELEGIR", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
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

@Composable
fun DriverCard(
    driver: Driver,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MoviPetWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto de perfil
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(MoviPetLightGray, androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Driver",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Información del conductor
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = driver.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        modifier = Modifier.size(16.dp),
                        tint = MoviPetOrange
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = driver.rating,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                
                Text(
                    text = driver.arrivalTime,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Text(
                    text = driver.vehicleType,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Text(
                    text = driver.licensePlate,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            
            // Botón elegir
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = MoviPetOrange),
                modifier = Modifier.height(36.dp)
            ) {
                Text("Elegir", fontSize = 12.sp)
            }
        }
    }
}
