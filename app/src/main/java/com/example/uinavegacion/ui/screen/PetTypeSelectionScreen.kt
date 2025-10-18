package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.ui.theme.MoviPetLightGray

data class PetType(
    val name: String,
    val icon: ImageVector
)

@Composable
fun PetTypeSelectionScreen(navController: NavController) {
    var selectedPetType by remember { mutableStateOf("Perro grande") }

    val petTypes = listOf(
        PetType("Perro pequeño", Icons.Default.Pets),
        PetType("Perro grande", Icons.Default.Pets),
        PetType("Gato", Icons.Default.Pets),
        PetType("Conejo", Icons.Default.Pets),
        PetType("Ave", Icons.Default.Pets),
        PetType("Reptil", Icons.Default.Pets),
        PetType("Roedor", Icons.Default.Pets),
        PetType("Pez", Icons.Default.Pets),
        PetType("Exótico", Icons.Default.Pets)
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
            
            // Grid de tipos de mascotas
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(petTypes) { petType ->
                    PetTypeCard(
                        petType = petType,
                        isSelected = selectedPetType == petType.name,
                        onClick = { selectedPetType = petType.name }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botón de elegir
            Button(
                onClick = { navController.navigate(Route.VehicleTypeSelection.path) },
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
fun PetTypeCard(
    petType: PetType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MoviPetOrange else MoviPetWhite
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                petType.icon,
                contentDescription = petType.name,
                modifier = Modifier.size(32.dp),
                tint = if (isSelected) MoviPetWhite else Color.Gray
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = petType.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) MoviPetWhite else Color.Black,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
