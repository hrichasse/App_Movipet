package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.data.model.Clinic
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.viewmodel.ClinicViewModel

@Composable
fun AddClinicScreen(
    navController: NavController,
    clinicViewModel: ClinicViewModel = viewModel()
) {
    var clinicName by remember { mutableStateOf("") }
    var clinicAddress by remember { mutableStateOf("") }
    var clinicPhone by remember { mutableStateOf("") }
    var clinicEmail by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val isLoading by clinicViewModel.isLoading.collectAsStateWithLifecycle()
    val error by clinicViewModel.error.collectAsStateWithLifecycle()
    val createSuccess by clinicViewModel.createSuccess.collectAsStateWithLifecycle()

    // Navegar de vuelta si se creó exitosamente
    LaunchedEffect(createSuccess) {
        if (createSuccess) {
            clinicViewModel.resetCreateSuccess()
            navController.popBackStack()
        }
    }

    // Validación de campos
    val isFormValid = clinicName.isNotBlank() &&
            clinicAddress.isNotBlank() &&
            clinicPhone.isNotBlank() &&
            clinicEmail.isNotBlank() &&
            latitude.isNotBlank() &&
            longitude.isNotBlank() &&
            address.isNotBlank() &&
            latitude.toDoubleOrNull() != null &&
            longitude.toDoubleOrNull() != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviPetLightGray)
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
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = MoviPetWhite)
            }
            Text(
                "Agregar Veterinaria",
                style = MaterialTheme.typography.titleLarge,
                color = MoviPetWhite
            )
            Spacer(Modifier.size(48.dp))
        }

        // Formulario scrolleable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Complete todos los campos para registrar la veterinaria",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(8.dp))

            // Nombre de la clínica
            OutlinedTextField(
                value = clinicName,
                onValueChange = { clinicName = it },
                label = { Text("Nombre de la veterinaria *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            // Dirección de la clínica
            OutlinedTextField(
                value = clinicAddress,
                onValueChange = { clinicAddress = it },
                label = { Text("Dirección *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            // Teléfono
            OutlinedTextField(
                value = clinicPhone,
                onValueChange = { clinicPhone = it },
                label = { Text("Teléfono *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                enabled = !isLoading
            )

            // Email
            OutlinedTextField(
                value = clinicEmail,
                onValueChange = { clinicEmail = it },
                label = { Text("Email *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = !isLoading
            )

            // Latitud
            OutlinedTextField(
                value = latitude,
                onValueChange = { latitude = it },
                label = { Text("Latitud *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                enabled = !isLoading,
                supportingText = {
                    if (latitude.isNotBlank() && latitude.toDoubleOrNull() == null) {
                        Text("Debe ser un número válido (ej: -33.4489)", color = Color.Red)
                    }
                }
            )

            // Longitud
            OutlinedTextField(
                value = longitude,
                onValueChange = { longitude = it },
                label = { Text("Longitud *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                enabled = !isLoading,
                supportingText = {
                    if (longitude.isNotBlank() && longitude.toDoubleOrNull() == null) {
                        Text("Debe ser un número válido (ej: -70.6693)", color = Color.Red)
                    }
                }
            )

            // Address adicional
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Dirección completa *") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                enabled = !isLoading
            )

            // Mensaje de error
            if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            // Botón guardar
            Button(
                onClick = {
                    val clinic = Clinic(
                        id = null,
                        clinicName = clinicName.trim(),
                        clinicAddress = clinicAddress.trim(),
                        clinicPhone = clinicPhone.trim(),
                        clinicEmail = clinicEmail.trim(),
                        latitude = latitude.toDouble(),
                        longitude = longitude.toDouble(),
                        address = address.trim()
                    )
                    clinicViewModel.createClinic(clinic)
                },
                enabled = isFormValid && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MoviPetOrange,
                    contentColor = MoviPetWhite
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MoviPetWhite
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Guardando...")
                } else {
                    Text("Guardar Veterinaria")
                }
            }

            Text(
                "* Campos obligatorios",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
