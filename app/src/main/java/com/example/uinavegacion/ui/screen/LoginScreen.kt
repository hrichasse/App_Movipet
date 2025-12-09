package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite
import com.example.uinavegacion.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    // Estados de los campos
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Estados del ViewModel
    val isLoading by authViewModel.isLoading.collectAsStateWithLifecycle()
    val error by authViewModel.error.collectAsStateWithLifecycle()
    val currentUser by authViewModel.currentUser.collectAsStateWithLifecycle()

    // Validación
    val isEmailValid = email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= 6
    val isFormValid = isEmailValid && isPasswordValid

    // Si ya se logueó correctamente → ir a
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate(Route.LocationSelection.path) {
                popUpTo(Route.Login.path) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviPetLightGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // HEADER
        Spacer(Modifier.height(32.dp))
        Text(
            text = "MoviPet",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MoviPetOrange
        )
        Spacer(Modifier.height(8.dp))
        Text("Inicia sesión para continuar", fontSize = 14.sp, color = Color.DarkGray)

        Spacer(Modifier.height(32.dp))

        // CARD DE LOGIN
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MoviPetWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = email.isNotBlank() && !isEmailValid,
                    supportingText = {
                        if (email.isNotBlank() && !isEmailValid) {
                            Text("Email inválido", color = Color.Red)
                        }
                    },
                    enabled = !isLoading
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = password.isNotBlank() && !isPasswordValid,
                    supportingText = {
                        if (password.isNotBlank() && !isPasswordValid) {
                            Text("Mínimo 6 caracteres", color = Color.Red)
                        }
                    },
                    enabled = !isLoading
                )

                Spacer(Modifier.height(16.dp))

                // Botón LOGIN
                Button(
                    onClick = {
                        authViewModel.login(email.trim(), password)
                    },
                    enabled = !isLoading && isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MoviPetOrange,
                        contentColor = MoviPetWhite
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MoviPetWhite
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Ingresando...")
                    } else {
                        Text("Iniciar sesión")
                    }
                }

                // Error si falló
                if (error != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = error ?: "",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Ir a Registro
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿No tienes cuenta?", fontSize = 14.sp)
            TextButton(onClick = { navController.navigate(Route.Register.path) }) {
                Text("Regístrate", color = MoviPetOrange)
            }
        }

        Spacer(Modifier.weight(1f))

        // Franja inferior naranja
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(MoviPetOrange)
        )
    }
}
