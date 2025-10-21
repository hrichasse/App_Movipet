package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RatingScreen(navController: NavController) {
    var stars by remember { mutableStateOf(5) }
    var comment by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Califica al conductor")
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (i in 1..5) {
                Button(onClick = { stars = i }) { Text(if (i <= stars) "★" else "☆") }
            }
        }

        Spacer(Modifier.height(12.dp))
        Text("¿Cómo estuvo el trato con tu mascota?")
        OutlinedTextField(value = comment, onValueChange = { comment = it }, modifier = Modifier.fillMaxWidth().height(120.dp))

        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { /* enviar */ }) { Text("Enviar") }
            Button(onClick = { /* guardar favorito */ }) { Text("Guardar como favorito") }
        }
    }
}
