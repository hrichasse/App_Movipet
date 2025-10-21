package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ChatScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    val quick = listOf("Estoy listo", "Estoy afuera", "Llego en 5 min")
    val messages = remember { mutableStateListOf<String>("Conductor: Estoy en camino") }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { msg ->
                Text(msg, modifier = Modifier.padding(6.dp))
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = text, onValueChange = { text = it }, modifier = Modifier.weight(1f))
            Button(onClick = { if (text.isNotBlank()) { messages.add("Tú: $text"); text = "" } }) { Text("Enviar") }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
            quick.forEach { q ->
                Button(onClick = { messages.add("Tú: $q") }) { Text(q) }
            }
            Button(onClick = { messages.add("Tú: 🐶 Mascota: peso 5kg, tranquila") }) { Text("🐶") }
        }
    }
}
