package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uinavegacion.ui.components.MoviPetHeader
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite

@Composable
fun ChatScreen(navController: NavController) {
    var input by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(
        listOf(
            ChatMsg("conductor", "¡Hola! Ya voy en camino."),
            ChatMsg("yo", "Gracias, te espero en la entrada."),
        )
    ) }

    Column(Modifier.fillMaxSize().background(MoviPetLightGray)) {
        MoviPetHeader(
            title = "Chat con el conductor",
            onBackClick = { navController.popBackStack() }
        )

        LazyColumn(modifier = Modifier.weight(1f).padding(12.dp)) {
            items(messages) { msg ->
                val isMine = msg.sender == "yo"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
                ) {
                    Surface(
                        color = if (isMine) MoviPetTeal else MoviPetWhite,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            msg.text,
                            modifier = Modifier.padding(12.dp).widthIn(max = 260.dp),
                            color = if (isMine) MoviPetWhite else Color.Black
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().background(MoviPetWhite).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje...") }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (input.isNotBlank()) {
                    messages = messages + ChatMsg("yo", input)
                    input = ""
                }
            }) { Text("Enviar") }
        }

        Box(Modifier.fillMaxWidth().height(8.dp).background(MoviPetOrange))
    }
}

data class ChatMsg(val sender: String, val text: String)
