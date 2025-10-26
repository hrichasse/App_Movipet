package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uinavegacion.domain.validation.validateConfirm
import com.example.uinavegacion.domain.validation.validateEmail
import com.example.uinavegacion.domain.validation.validateNameLettersOnly
import com.example.uinavegacion.domain.validation.validateStrongPass
import com.example.uinavegacion.navigation.Route

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passError by remember { mutableStateOf<String?>(null) }
    var confirmError by remember { mutableStateOf<String?>(null) }

    val allValid = nameError == null && emailError == null && passError == null && confirmError == null &&
            name.isNotBlank() && email.isNotBlank() && pass.isNotBlank() && confirm.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear cuenta",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = validateNameLettersOnly(it)
            },
            label = { Text("Nombre") },
            isError = nameError != null,
            supportingText = { if (nameError != null) Text(nameError!!, color = MaterialTheme.colorScheme.error) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = validateEmail(it)
            },
            label = { Text("Correo") },
            isError = emailError != null,
            supportingText = { if (emailError != null) Text(emailError!!, color = MaterialTheme.colorScheme.error) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = pass,
            onValueChange = {
                pass = it
                passError = validateStrongPass(it)
                confirmError = validateConfirm(it, confirm)
            },
            label = { Text("Contraseña") },
            isError = passError != null,
            supportingText = { if (passError != null) Text(passError!!, color = MaterialTheme.colorScheme.error) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = confirm,
            onValueChange = {
                confirm = it
                confirmError = validateConfirm(pass, it)
            },
            label = { Text("Confirmar contraseña") },
            isError = confirmError != null,
            supportingText = { if (confirmError != null) Text(confirmError!!, color = MaterialTheme.colorScheme.error) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate(Route.Login.path) },
            enabled = allValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Registrarse", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}